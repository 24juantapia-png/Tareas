package com.ejemplo.Tareas;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Tarea {

    public Tarea(){

    }

    public Tarea(String titulo, String description, Boolean realizada){
        this.titulo = titulo;
        this.description = description;
        this.realizada = realizada;

    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTarea;
    private String titulo;
    private String description;
    private Boolean realizada;

    @Override
    public String toString() {
        return "Tarea{" +
                "ifTarea=" + idTarea +
                ", titulo='" + titulo + '\'' +
                '}';
    }

    public boolean isRealizada(){
        return realizada;
}

}