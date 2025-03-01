package me.jorgemoreno.whattodo.meta_edit;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import me.jorgemoreno.whattodo.BaseDatos;
import me.jorgemoreno.whattodo.Global;
import me.jorgemoreno.whattodo.R;
import me.jorgemoreno.whattodo.categoria_edit.CatEditListAdapter;
import me.jorgemoreno.whattodo.data.Categoria;
import me.jorgemoreno.whattodo.data.Meta;
import me.jorgemoreno.whattodo.data.Tarea;
import me.jorgemoreno.whattodo.dialogos.CreateWithName;

public class MetaEditFragment extends Fragment {
    Meta meta;
    int position[] = new int[2];
    MetaEditListAdapter adapter;

    public MetaEditFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        position[0] = getArguments().getInt("categoria");
        position[1] = getArguments().getInt("meta");
        meta = Global.getDatos().get(position[0]).getMetaAt(position[1]);
        adapter = new MetaEditListAdapter(meta, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meta_edit, container, false);

        RecyclerView rv = view.findViewById(R.id.listaEditMeta);
        rv.setLayoutManager(new LinearLayoutManager(this.getContext()));
        rv.setAdapter(adapter);

        EditText nombre = view.findViewById(R.id.editMetaNombre);
        nombre.setText(meta.getNombre());

        EditText descripcion = view.findViewById(R.id.editMetaDescripcion);
        descripcion.setText(meta.getDescripcion());

        ImageButton crearTarea = view.findViewById(R.id.nuevaTarea);
        crearTarea.setOnClickListener(v -> {
            CreateWithName dialogo = new CreateWithName(
                    "Crear tarea",
                    "Elige el nombre de la tarea",
                    (name) -> {
                        //TODO: hacer esto temporal o añadir un mensaje que indique que se crea en el momento
                        Tarea tarea = new Tarea(name);
                        meta.addTarea(tarea);

                        adapter.notifyItemInserted(meta.getTareaCount() - 1);
                        BaseDatos.crearTarea(BaseDatos.getInstance().getWritableDatabase(), tarea, meta.getId());
                        Global.meta_modificada = position[1];
                    },
                    (dialog, which) -> {}
            );
            dialogo.show(getActivity().getSupportFragmentManager(), "tarea_crear");
        });

        FloatingActionButton fab = view.findViewById(R.id.fabMetaEdit);
        fab.setOnClickListener(v -> {
            meta.setNombre(nombre.getText().toString());
            meta.setDescripcion(descripcion.getText().toString());
            BaseDatos.updateMeta(BaseDatos.getInstance().getWritableDatabase(), meta);

            //actualizar datos de las tareas
            int num_tareas = rv.getLayoutManager().getItemCount();
            for (int i = 0; i < num_tareas; i++) {
                Tarea t = meta.getTareaAt(i);
                var vhOpt = adapter.getHolder(i);
                if (vhOpt.isEmpty()) continue;
                MetaEditListAdapter.ViewHolder vh = vhOpt.get();

                t.setNombre(vh.nombre.getText().toString());
                t.setCompletada(vh.checkBox.isChecked());

                BaseDatos.updateTarea(BaseDatos.getInstance().getWritableDatabase(), t);
            }

            Toast.makeText(getContext(), "Meta actualizada", Toast.LENGTH_SHORT).show();
            Global.meta_modificada = position[1];
            Global.cat_modificada = position[0];

            // TODO: may not be preferred if in horizontal?
            getActivity().finish();
        });

        // Inflate the layout for this fragment
        return view;
    }
}