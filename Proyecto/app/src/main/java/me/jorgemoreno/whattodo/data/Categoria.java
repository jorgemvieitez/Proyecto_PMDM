package me.jorgemoreno.whattodo.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.stream.Stream;

public class Categoria implements Serializable {
    private String nombre;
    private boolean isCollapsed = false;
    private ArrayList<Meta> metas;

    public Categoria(String nombre) {
        this.nombre = nombre;
        this.metas = new ArrayList<>();
    }

    public Categoria(String nombre, ArrayList<Meta> metas) {
        this.nombre = nombre;
        this.metas = metas;
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

    // APIs para las metas están hechas para después poder aplicar ORM

    public Stream<Meta> getMetas() {
        return metas.stream();
    }

    public void addMeta(Meta meta) {
        metas.add(meta);
    }

    public void removeMeta(Meta meta) {
        metas.remove(meta);
    }

    public int getMetaCount() {
        return metas.size();
    }
}
