package me.jorgemoreno.whattodo.data;

import java.io.Serializable;

public class Meta implements Serializable {
    private String nombre;

    public Meta(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
