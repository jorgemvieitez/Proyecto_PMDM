package me.jorgemoreno.whattodo.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.stream.Stream;

public class Meta implements Serializable {
    private String nombre;
    private String descripcion;
    private final ArrayList<Tarea> tareas;

    public Meta(String nombre) {
        this.nombre = nombre;
        tareas = new ArrayList<>();
    }

    public Meta(String nombre, String descripcion) {
        this(nombre);
        this.descripcion = descripcion;
    }

    public Meta(String nombre, String descripcion, ArrayList<Tarea> tareas) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tareas = tareas;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Stream<Tarea> getTareas() {
        return tareas.stream();
    }

    public void addTarea(Tarea tarea) {
        tareas.add(tarea);
    }

    public void removeTarea(Tarea tarea) {
        tareas.remove(tarea);
    }

    public int getTareaCount() {
        return tareas.size();
    }

    public Tarea getTareaAt(int pos) {
        return tareas.get(pos);
    }
}
