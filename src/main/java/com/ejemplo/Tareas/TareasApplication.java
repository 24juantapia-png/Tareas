package com.ejemplo.Tareas;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Optional;

@SpringBootApplication (scanBasePackages = "com.ejemplo")
public class TareasApplication extends Application {
    private ConfigurableApplicationContext applicationContext;

	public static void main(String[] args)
    {
		launch(args);
	}

    @Override
    public void start(Stage stage) throws Exception {
        FxWeaver fxWeaver = applicationContext.getBean(FxWeaver.class);
        Parent root = fxWeaver.loadView(TareasControlador.class);
        stage.setTitle("Tareas");
        stage.setScene(new Scene(root,640, 480));
        stage.setOnCloseRequest(event -> {
            //Cancelar el evento de cierre
            event.consume();
            //Mostrar la confirmacion de alerta de cierre
            mostrarConfirmacionDeCierreq(stage);
        });
        stage.show();
    }
    @Override
    public void init() throws Exception{
        String[]args = getParameters().getRaw().toArray(new String[0]);
        applicationContext = SpringApplication.run(TareasApplication.class,args);
    }
    private void mostrarConfirmacionDeCierreq(Stage stage){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar cierre");
        alert.setHeaderText("¿Cerrar la aplicacion de tareas?");
        alert.setContentText("Elige tu Respuesta.0");
        Optional<ButtonType> resp = alert.showAndWait();
        if (resp.isPresent() && resp.get()== ButtonType.OK){
            //Si el usuario responde "ok", cerrar la ventana
            stage.close();
        }

    }
}
