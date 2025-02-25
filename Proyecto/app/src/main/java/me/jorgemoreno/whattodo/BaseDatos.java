package me.jorgemoreno.whattodo;

import android.content.ContentValues;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.concurrent.atomic.AtomicBoolean;

import me.jorgemoreno.whattodo.data.Categoria;
import me.jorgemoreno.whattodo.data.Meta;

public class BaseDatos extends SQLiteOpenHelper {
    public static BaseDatos sInstance;

    public static final int DB_VERSION = 1;
    private BaseDatos(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.setVersion(DB_VERSION);
        db.execSQL(
            "CREATE TABLE categorias (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombre TEXT NOT NULL," +
                "descripcion TEXT" +
            ")"
        );
        db.execSQL(
            "CREATE TABLE metas (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombre TEXT NOT NULL," +
                "descripcion TEXT," +
                "categoria INTEGER," +
                "FOREIGN KEY (categoria) REFERENCES categorias(id)" +
            ")"
        );
        db.execSQL(
            "CREATE TABLE tareas (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombre TEXT NOT NULL," +
                "completada BOOLEAN NOT NULL DEFAULT false," +
                "meta INTEGER," +
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

    public static synchronized BaseDatos getInstance(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        if (sInstance == null) {
            sInstance = new BaseDatos(context, name, factory, version);
        }
        return sInstance;
    }

    public static boolean setUpTestValues(SQLiteDatabase db) {
        AtomicBoolean wasSuccessful = new AtomicBoolean(true);

        for (Categoria c: Global.datos) {
            ContentValues registro_cat = new ContentValues();
            registro_cat.put("_id", c.getId());
            registro_cat.put("nombre", c.getNombre());
            registro_cat.put("descripcion", c.getDescripcion());
            long id_cat = db.insert("categorias", null, registro_cat);
            if (id_cat == -1) return false;
            c.setId(id_cat);

            c.getMetas().forEach(m -> {
                // skip if has already errored
                if (!wasSuccessful.get()) return;

                ContentValues registro_meta = new ContentValues();
                registro_meta.put("_id", m.getId());
                registro_meta.put("nombre", m.getNombre());
                registro_meta.put("descripcion", m.getDescripcion());
                registro_meta.put("categoria", id_cat);
                long id_meta = db.insert("metas", null, registro_meta);
                if (id_meta == -1) {
                    wasSuccessful.set(false);
                    return;
                }
                m.setId(id_meta);

                m.getTareas().forEach(t -> {
                    // skip if has already errored
                    if (!wasSuccessful.get()) return;

                    ContentValues registro_tarea = new ContentValues();
                    registro_tarea.put("_id", t.getId());
                    registro_tarea.put("nombre", t.getNombre());
                    registro_tarea.put("completada", t.isCompletada());
                    registro_tarea.put("meta", id_meta);
                    long id_tarea = db.insert("tareas", null, registro_tarea);
                    if (id_tarea == -1) {
                        wasSuccessful.set(false);
                        return;
                    }
                    m.setId(id_meta);
                });
            });

            // skip to end if has already errored
            if (!wasSuccessful.get()) break;
        }

        return wasSuccessful.get();
    }
}
