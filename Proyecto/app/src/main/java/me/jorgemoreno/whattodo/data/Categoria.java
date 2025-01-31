package me.jorgemoreno.whattodo.data;

import java.io.Serializable;

public class Categoria implements Serializable {
    private String nombre;
    private boolean isCollapsed = false;

    public Categoria(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isCollapsed() {
        return isCollapsed;
    }

    public void setCollapsed(boolean collapsed) {
        isCollapsed = collapsed;
    }
}
