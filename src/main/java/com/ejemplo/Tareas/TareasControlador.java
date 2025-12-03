package com.ejemplo.Tareas;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@FxmlView("/tareasvista.fxml")
public class TareasControlador {
    @Autowired
    TareasServicio tareasServicio;
    @FXML
    public ListView<Tarea> lstVwListaDeTareas;
    @FXML
    public TextField txtFldTitulo;
    @FXML
    public TextArea txtArDescripcion;
    @FXML
    public CheckBox chkBxRealizada;
    @FXML
    public Button btnNuevaTarea;
    @FXML
    public Button btnEliminar;
    @FXML
    public Button btnModificar;
    @FXML
    public Button btnGuardar;
    @FXML
    public Button btnSalir;
    ObservableList<Tarea> observableList;
    @FXML
    public void onActionBtnSalir(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        WindowEvent closeEvent = new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST);
        stage.fireEvent(closeEvent);
    }
    @FXML
    public void onActionBtnGuardar(ActionEvent actionEvent) {
        Tarea tareaSeleccionada = lstVwListaDeTareas.getSelectionModel().getSelectedItem();
        tareaSeleccionada.setTitulo(txtFldTitulo.getText());
        tareaSeleccionada.setDescription(txtArDescripcion.getText());
        tareaSeleccionada.setRealizada(chkBxRealizada.isSelected());

        int index = observableList.indexOf(tareaSeleccionada);

        if (index != -1)
            observableList.set(index, tareaSeleccionada);

        btnGuardar.setDisable(true);
        reseleccionar();
    }
    @FXML
    public void onActionBtnEliminar(ActionEvent actionEvent) {
    Tarea tareaseleccionada = lstVwListaDeTareas.getSelectionModel().getSelectedItem();
    if (confirmarAccion("Eliminar Tarea "+ tareaseleccionada.getTitulo()))
        observableList.remove(tareaseleccionada);
    }
    @FXML
    public void onActionBtnModificar(ActionEvent actionEvent) {
    txtFldTitulo.setDisable(false);
    txtArDescripcion.setDisable(false);
    chkBxRealizada.setDisable(false);
    txtFldTitulo.requestFocus();
    btnNuevaTarea.setDisable(false);
    btnModificar.setDisable(false);
    btnEliminar.setDisable(false);
    btnGuardar.setDisable(false);
    }
    @FXML
    public void onActionBtnNuevaTarea(ActionEvent actionEvent) {
    Tarea nuevaTarea = new Tarea("Nueva Tarea","Descripcion de la nueva tarea",false);
    observableList.add(nuevaTarea);
    lstVwListaDeTareas.getSelectionModel().select(nuevaTarea);
    lstVwListaDeTareas.scrollTo(nuevaTarea);
    txtFldTitulo.setDisable(false);
    txtArDescripcion.setDisable(false);
    chkBxRealizada.setDisable(false);
    txtFldTitulo.requestFocus();
    btnNuevaTarea.setDisable(false);
    btnModificar.setDisable(false);
    btnEliminar.setDisable(false);
    btnGuardar.setDisable(false);
    }
    public void initialize() {
        //Inicializa el observableist
        observableList = FXCollections.observableArrayList();
        //Vincula la observablelist con el listview
        lstVwListaDeTareas.setItems(observableList);
        //Cargar los datos iniciales (opcional, pero recomendado)
        List<Tarea> tareasIniciales = tareasServicio.findAll();
        observableList.addAll(tareasIniciales);
        //Añadir un escuchador listChangeListenner al observablelist para detectar cambios en la lista
        observableList.addListener((ListChangeListener<Tarea>) change -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    //Iterar sobre todos los elementos agragados en este cambio
                    for (Tarea tareaAgregada : change.getAddedSubList()) {
                        //llenar el servicio para guardar en la 50
                        tareasServicio.save(tareaAgregada);
                        System.out.println("Tarea guardada automaticamente en la BB" + tareaAgregada.getTitulo());
                    }
                } else if (change.wasRemoved()) {
                    for (Tarea tareaRemovida : change.getRemoved()) {
                        tareasServicio.delete(tareaRemovida.getIdTarea());
                        lstVwListaDeTareas.getSelectionModel().clearSelection();
                    }
                } else if (change.wasUpdated()) {
                    System.out.println("---Deteccion de actualizacion de tarea---");
                    for (int i = change.getFrom(); 1 < change.getTo(); i++) {
                        Tarea tareaModificada = observableList.get(1);
                        tareasServicio.update(tareaModificada.getIdTarea(), tareaModificada);
                        System.out.println("Tarea con indice" + 1 +
                                "actualizada en la 80: " + tareaModificada.getTitulo());
                        lstVwListaDeTareas.refresh();
                    }
                }
            }
        });
        lstVwListaDeTareas.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {


                    if (newValue != null) {
                        txtFldTitulo.setText(newValue.getTitulo());// Mostrar el título de la tarea seleccionada en el TextField
                        txtArDescripcion.setText(newValue.getDescription()); // Mostrar el título de la tarea seleccionada en el I
                        chkBxRealizada.setAllowIndeterminate(false);
                        chkBxRealizada.setIndeterminate(false);
                        chkBxRealizada.setSelected(newValue.getRealizada()); //Mostrar el estado de realizada de la tarea
                        txtFldTitulo.setDisable(true); //Deshabilitar el control del título
                        txtArDescripcion.setDisable(true); //Deshabilitar el control de la descripción
                        chkBxRealizada.setDisable(true); //Deshabilitar el control de realizada
                        btnNuevaTarea.setDisable(false); //Habilitar el boton de nueva tarea
                        btnModificar.setDisable(false); //Habilitar el botpn de modificar
                        btnEliminar.setDisable(false);//Habilitar el boton de eliminar
                        btnGuardar.setDisable(true); //Deshabilitar el boton de guardar
                    } else {
                        //Si no hay selección
                        txtFldTitulo.setText("");
                        txtArDescripcion.setText("");
                        chkBxRealizada.setAllowIndeterminate(true);
                        chkBxRealizada.setIndeterminate(true);
                        btnNuevaTarea.setDisable(false);
                        btnModificar.setDisable(true);
                        btnEliminar.setDisable(false);
                        btnGuardar.setDisable(true);
                    }
                });
    }
    private boolean confirmarAccion(String pregunta){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Por favor confirme la accion");
        alert.setHeaderText("¿"+ pregunta+"?");
        alert.setContentText("Elige tu respuesta.0");
        Optional<ButtonType> resp = alert.showAndWait();
        if (resp.isPresent()&& resp.get()==ButtonType.OK)
            return true;
        return false;
    }
    //---Forzar Notificacion a selectedItemProperty---
    public void reseleccionar(){
        int selectedIndex = lstVwListaDeTareas.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0){
            //1. Forzar la deseleccion ( Dispara el evento con newValue
            lstVwListaDeTareas.getSelectionModel().clearSelection();
            //2. Forzar la re-seleccion(Dispara el evento con newValue=Tarea Actualizada)
            lstVwListaDeTareas.getSelectionModel().select(selectedIndex);
            //3.Redibujar el ListView(Para mostrar los nuevos textos)
            lstVwListaDeTareas.refresh();

        }
        else
            lstVwListaDeTareas.getSelectionModel().clearSelection();
    }
}


