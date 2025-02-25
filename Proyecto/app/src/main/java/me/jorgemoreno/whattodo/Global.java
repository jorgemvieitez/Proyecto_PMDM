package me.jorgemoreno.whattodo;

import java.util.ArrayList;
import java.util.Arrays;

import me.jorgemoreno.whattodo.data.Categoria;
import me.jorgemoreno.whattodo.data.Meta;
import me.jorgemoreno.whattodo.data.Tarea;

public class Global {
    public static Integer cat_modificada = null;
    public static Integer meta_modificada = null;

    private static ArrayList<Categoria> datos;

    public static ArrayList<Categoria> datos_testing = new ArrayList<>(Arrays.asList(
            new Categoria("test1", "Hola", new ArrayList<>(Arrays.asList(
                    new Meta("test1.a", "Buenas", new ArrayList<>(Arrays.asList(
                            new Tarea("hola")
                    ))),
                    new Meta("test1.b", "Q tal")
            ))),
            new Categoria("test2", "Adiós",new ArrayList<>(Arrays.asList(
                    new Meta("test2.a"),
                    new Meta("test2.b")
            )))
    ));

    public static ArrayList<Categoria> getDatos() {
        if (datos == null) {
            datos = BaseDatos.getDatos(BaseDatos.getInstance().getWritableDatabase());
        }
        return datos;
    }
}
