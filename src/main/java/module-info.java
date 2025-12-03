module Tareas {
requires jakarta.persistence;
requires javafx.controls;
requires javafx.graphics;
requires javafx.fxml;
requires static lombok;
requires net.rgielen.fxweaver.core;
requires spring.beans;
requires spring.boot;
requires spring.boot.autoconfigure;
requires spring.context;
requires spring.data.jpa;
// Permitir que el módulo javafx.graphics acceda
// cree una instancia de la clase Application.
// También que permitir acceso a los paquetes donde residen
// los controladores para que JavaFX pueda inyectar los elementos @FXML
// Además permitir a FXWeaver abrir los paquetes de los controladores
// a "spring.core" para que Spring pueda escanear e inyectar dependencias.
// opens com.ejemplo.Tareas to javafx.graphics, javafx.fxml, spring.core, net. rgielen.fxweaver.core;
opens com.ejemplo.Tareas;
// IMPORTANTE: agregar a VM_OPTIONS --add-opens Tareas/com.ejemplo. Tareas=ALL-UNNAMED --add-reads Tai
// Abrir el paquete de entidades a "unnamed module" (classpath), donde reside Hibernate.
exports com.ejemplo.Tareas;

}