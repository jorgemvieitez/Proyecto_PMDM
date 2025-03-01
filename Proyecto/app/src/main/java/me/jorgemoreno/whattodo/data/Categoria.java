package me.jorgemoreno.whattodo.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.stream.Stream;

public class Categoria implements Serializable {
    private Long id = null;
    private String nombre;
    private String descripcion = "";
    private boolean isCollapsed = false;
    private final ArrayList<Meta> metas;

    public Categoria(String nombre) {
        this.nombre = nombre;
        this.metas = new ArrayList<>();
    }

    public Categoria(String nombre, String descripcion) {
        this(nombre);
        this.descripcion = descripcion;
    }

    public Categoria(String nombre, String descripcion, ArrayList<Meta> metas) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.metas = metas;
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public boolean isCollapsed() {
        return isCollapsed;
    }

    public void setCollapsed(boolean collapsed) {
        isCollapsed = collapsed;
    }

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

    public Meta getMetaAt(int pos) {
        return metas.get(pos);
    }
}
