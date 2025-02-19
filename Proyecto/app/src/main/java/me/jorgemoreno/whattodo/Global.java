package me.jorgemoreno.whattodo;

import java.util.ArrayList;
import java.util.Arrays;

import me.jorgemoreno.whattodo.data.Categoria;
import me.jorgemoreno.whattodo.data.Meta;

public class Global {
    public static ArrayList<Categoria> datos = new ArrayList<>(Arrays.asList(
            new Categoria("test1", "Hola", new ArrayList<>(Arrays.asList(
                    new Meta("test1.a"),
                    new Meta("test1.b")
            ))),
            new Categoria("test2", "Adiós",new ArrayList<>(Arrays.asList(
                    new Meta("test2.a"),
                    new Meta("test2.b")
            ))),
            new Categoria("test3"),
            new Categoria("test4"),
            new Categoria("test5"),
            new Categoria("test6"),
            new Categoria("test7"),
            new Categoria("test8"),
            new Categoria("test9"),
            new Categoria("test10"),
            new Categoria("test11"),
            new Categoria("test12"),
            new Categoria("test13"),
            new Categoria("test14")
    ));
}
