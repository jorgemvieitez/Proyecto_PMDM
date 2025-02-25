package me.jorgemoreno.whattodo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import me.jorgemoreno.whattodo.data.Categoria;
import me.jorgemoreno.whattodo.data.Meta;
import me.jorgemoreno.whattodo.data.Tarea;

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

    public static synchronized BaseDatos getInstance() {
        if (sInstance == null)
            throw new RuntimeException("instance does not exist");
        return sInstance;
    }

    public static BaseDatos getInstance(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        if (sInstance == null) {
            sInstance = new BaseDatos(context, name, factory, version);
        }
        return getInstance();
    }

    public static boolean setUpTestValues(SQLiteDatabase db, List<Categoria> datos) {
        for (Categoria c: datos) {
            if (!crearCategoria(db, c))
                return false;
        }

        return true;
    }

    public static ArrayList<Categoria> getDatos(SQLiteDatabase db) {
        ArrayList<Categoria> out = new ArrayList<>();

        Cursor fila = db.rawQuery("SELECT _id, nombre, descripcion FROM categorias", null);
        while (fila.moveToNext()) {
            long id = fila.getLong(0);

            Categoria c = new Categoria(fila.getString(1), fila.getString(2));
            c.setId(id);

            Cursor meta = db.rawQuery(
                    "SELECT _id, nombre, descripcion, categoria FROM metas WHERE categoria = ?",
                    new String[]{Long.toString(id)}
            );

            while (meta.moveToNext()) {
                long id_meta = meta.getLong(0);

                Meta m = new Meta(meta.getString(1), meta.getString(2));
                m.setId(id_meta);

                Cursor tarea = db.rawQuery(
                        "SELECT _id, nombre, completada, meta FROM tareas WHERE meta = ?",
                        new String[]{Long.toString(id_meta)}
                );

                while (tarea.moveToNext()) {
                    long id_tarea = tarea.getLong(0);

                    Tarea t = new Tarea(tarea.getString(1));
                    t.setId(id_tarea);
                    t.setCompletada(tarea.getInt(2) != 0);

                    m.addTarea(t);
                }

                c.addMeta(m);
            }

            out.add(c);
        }

        return out;
    }

    public static boolean crearCategoria(SQLiteDatabase db, Categoria c) {
        AtomicBoolean wasSuccessful = new AtomicBoolean(true);

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

            if (!crearMeta(db, m, id_cat))
                wasSuccessful.set(false);
        });

        return wasSuccessful.get();
    }

    public static boolean updateCategoria(SQLiteDatabase db, Categoria c) {
        ContentValues registro = new ContentValues();
        registro.put("nombre", c.getNombre());
        registro.put("descripcion", c.getDescripcion());
        int resultado = db.update("categorias", registro, "_id = ?", new String[]{c.getId().toString()});
        return resultado == 1;
    }

    public static boolean deleteCategoria(SQLiteDatabase db, Categoria c) {
        c.getMetas().forEach(m -> deleteMeta(db, m));

        int resultado = db.delete("categorias", "_id = ?", new String[]{c.getId().toString()});
        return resultado == 1;
    }

    public static boolean crearMeta(SQLiteDatabase db, Meta m, long id_cat) {
        AtomicBoolean wasSuccessful = new AtomicBoolean(true);

        ContentValues registro_meta = new ContentValues();
        registro_meta.put("_id", m.getId());
        registro_meta.put("nombre", m.getNombre());
        registro_meta.put("descripcion", m.getDescripcion());
        registro_meta.put("categoria", id_cat);
        long id_meta = db.insert("metas", null, registro_meta);
        if (id_meta == -1) return false;
        m.setId(id_meta);

        m.getTareas().forEach(t -> {
            // skip if has already errored
            if (!wasSuccessful.get()) return;

            if (!crearTarea(db, t, id_meta))
                wasSuccessful.set(false);
        });

        return wasSuccessful.get();
    }

    public static boolean updateMeta(SQLiteDatabase db, Meta m) {
        ContentValues registro = new ContentValues();
        registro.put("nombre", m.getNombre());
        registro.put("descripcion", m.getDescripcion());
        int resultado = db.update("metas", registro, "_id = ?", new String[]{m.getId().toString()});
        return resultado == 1;
    }

    public static boolean deleteMeta(SQLiteDatabase db, Meta m) {
        m.getTareas().forEach(t -> deleteTarea(db, t));

        int resultado = db.delete("metas", "_id = ?", new String[]{m.getId().toString()});
        return resultado == 1;
    }

    public static boolean crearTarea(SQLiteDatabase db, Tarea t, long id_meta) {
        ContentValues registro_tarea = new ContentValues();
        registro_tarea.put("_id", t.getId());
        registro_tarea.put("nombre", t.getNombre());
        registro_tarea.put("completada", t.isCompletada());
        registro_tarea.put("meta", id_meta);
        long id_tarea = db.insert("tareas", null, registro_tarea);
        if (id_tarea == -1) return false;

        t.setId(id_tarea);

        return true;
    }

    public static boolean updateTarea(SQLiteDatabase db, Tarea t) {
        ContentValues registro = new ContentValues();
        registro.put("nombre", t.getNombre());
        registro.put("completada", t.isCompletada());
        int resultado = db.update("tareas", registro, "_id = ?", new String[]{t.getId().toString()});
        return resultado == 1;
    }


    public static boolean deleteTarea(SQLiteDatabase db, Tarea t) {
        int resultado = db.delete("tareas", "_id = ?", new String[]{t.getId().toString()});
        return resultado == 1;
    }
}
