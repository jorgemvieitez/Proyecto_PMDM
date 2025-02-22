package me.jorgemoreno.whattodo;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class BaseDatos extends SQLiteOpenHelper {
    public static final int DB_VERSION = 1;
    public BaseDatos(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public BaseDatos(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version, @Nullable DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.setVersion(DB_VERSION);
        db.execSQL(
            "CREATE TABLE categorias (" +
                "id INT PRIMARY KEY AUTOINCREMENT," +
                "nombre TEXT NOT NULL," +
                "descripcion TEXT" +
            ")"
        );
        db.execSQL(
            "CREATE TABLE metas (" +
                "id INT PRIMARY KEY AUTOINCREMENT," +
                "nombre TEXT NOT NULL," +
                "descripcion TEXT," +
                "categoria INT," +
                "FOREIGN KEY (categoria) REFERENCES categorias(id)" +
            ")"
        );
        db.execSQL(
            "CREATE TABLE tareas (" +
                "id INT PRIMARY KEY AUTOINCREMENT," +
                "nombre TEXT NOT NULL," +
                "completada BOOLEAN NOT NULL DEFAULT false," +
                "meta INT," +
                "FOREIGN KEY (meta) REFERENCES metas(id)" +
            ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //TODO: añadir funcionalidad si es necesario
        db.execSQL("DROP TABLE IF EXISTS tareas");
        db.execSQL("DROP TABLE IF EXISTS metas");
        db.execSQL("DROP TABLE IF EXISTS categorias");
        onCreate(db);
    }
}
