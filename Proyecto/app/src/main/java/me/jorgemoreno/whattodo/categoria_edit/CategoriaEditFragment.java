package me.jorgemoreno.whattodo.categoria_edit;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import me.jorgemoreno.whattodo.BaseDatos;
import me.jorgemoreno.whattodo.Global;
import me.jorgemoreno.whattodo.R;
import me.jorgemoreno.whattodo.data.Categoria;
import me.jorgemoreno.whattodo.data.Meta;
import me.jorgemoreno.whattodo.dialogos.CreateWithName;

public class CategoriaEditFragment extends Fragment {
    Categoria cat;
    int position;
    CatEditListAdapter adapter;

    public CategoriaEditFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        position = getArguments().getInt("categoria");
        cat = Global.getDatos().get(position);
        adapter = new CatEditListAdapter(cat, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categoria_edit, container, false);

        RecyclerView rv = view.findViewById(R.id.listaEditCat);
        rv.setLayoutManager(new LinearLayoutManager(this.getContext()));
        rv.setAdapter(adapter);

        EditText nombre = view.findViewById(R.id.editCatNombre);
        nombre.setText(cat.getNombre());

        EditText descripcion = view.findViewById(R.id.editCatDescripcion);
        descripcion.setText(cat.getDescripcion());

        FloatingActionButton fab = view.findViewById(R.id.fabCatEdit);
        fab.setOnClickListener(v -> {
            cat.setNombre(nombre.getText().toString());
            cat.setDescripcion(descripcion.getText().toString());
            Toast.makeText(getContext(), "Categoría actualizada", Toast.LENGTH_SHORT).show();
            BaseDatos.updateCategoria(BaseDatos.getInstance().getWritableDatabase(), cat);

            // no me apetece mirarme las APIs de result así que static globals here we go
            Global.cat_modificada = position;

            // TODO: may not be preferred if in horizontal?
            getActivity().finish();
        });

        ImageButton nuevaMeta = view.findViewById(R.id.nuevaMeta);
        nuevaMeta.setOnClickListener((btn) -> {
            CreateWithName dialogo = new CreateWithName(
                    "Crear meta",
                    "Elige el nombre de la meta",
                    (name) -> {
                        Meta meta = new Meta(name);
                        cat.addMeta(meta);
                        adapter.notifyItemInserted(cat.getMetaCount() - 1);
                        BaseDatos.crearMeta(BaseDatos.getInstance().getWritableDatabase(), meta, cat.getId());
                        Global.cat_modificada = position;
                    },
                    (dialog, which) -> {}
            );
            dialogo.show(getActivity().getSupportFragmentManager(), "meta_crear");
        });

        // Inflate the layout for this fragment
        return view;
    }
}