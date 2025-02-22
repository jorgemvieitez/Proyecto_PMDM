package me.jorgemoreno.whattodo;

import java.util.ArrayList;
import java.util.Arrays;

import me.jorgemoreno.whattodo.data.Categoria;
import me.jorgemoreno.whattodo.data.Meta;

public class Global {
    public static Integer cat_modificada = null;

    public static ArrayList<Categoria> datos = new ArrayList<>(Arrays.asList(
            new Categoria("test1", "Hola", new ArrayList<>(Arrays.asList(
                    new Meta("test1.a"),
                    new Meta("test1.b")
            ))),
            new Categoria("test2", "Adiós",new ArrayList<>(Arrays.asList(
                    new Meta("test2.a"),
                    new Meta("test2.b")
            )))
    ));
}
