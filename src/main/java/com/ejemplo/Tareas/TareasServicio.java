package com.ejemplo.Tareas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TareasServicio {
    @Autowired
    private TareasRepositorio tareasRepositorio;

    /***********************************************************
     * Metodo: find All
     * Objetivo: Regresa todas las tareas de la tabla Tareas de
     * la base de datos
     * @return Una lista de tareas que se obtiene de la consulta
     * la base de datos
     **********************************************************/
    public List<Tarea> findAll(){
        return tareasRepositorio.findAll();
    }

    /*********************************************************
     * Metodo: save
     * Objetivo: Guardar un objeto Tareas de la tabla de
     * datos
     * @return tarea - El objeto tarea que se quiere guardar
     * @return No regresa valores
     ********************************************************/
    public void save(Tarea tarea){
        tareasRepositorio.save(tarea);
    }

    /********************************************************
     * Metodo: delete
     * Objetivo: Eliminar de la base de datos la tarea que tenga el
     * identificador especificado
     * @param idTarea
     ********************************************************/
    public void delete(Long idTarea){
        tareasRepositorio.deleteById(idTarea);

    }

    /********************************************************
     * Metodo: findById
     * Busca una tarea que tenga el identificador especificado en la
     * tabla Tareas de la base de datos
     * @param idTarea
     * @return La tarea si se encontró o null si no existe la tarea
     ********************************************************/
    public Tarea findById(Long idTarea) {
        return tareasRepositorio.findById(idTarea).orElseThrow(
                ()-> new RuntimeException("Tarea" + idTarea + " no encontrada"));

    }

    /*******************************************************************
     * Metodo: update
     * Objetivo: Actualizar o modificar una tarea ya existente en
     * la base de datos
     * @param idTarea Identificador unico de la tarea a actualizar
     *                o modificar
     * @param tareaActualizada El objeto Tarea con los datos ya
     *                         modificados
     */
    public void update(Long idTarea, Tarea tareaActualizada){
        //Buscar la tarea en la base de datos
        Tarea tareaExistente = findById(idTarea);
        tareaExistente.setTitulo(tareaActualizada.getTitulo());
        tareaExistente.setDescription(tareaActualizada.getDescription());
        tareaExistente.setRealizada(tareaActualizada.getRealizada());
        tareasRepositorio.save(tareaExistente);
}

}