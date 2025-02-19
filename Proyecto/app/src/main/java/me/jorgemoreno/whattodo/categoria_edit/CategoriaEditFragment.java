package me.jorgemoreno.whattodo.categoria_edit;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import me.jorgemoreno.whattodo.Global;
import me.jorgemoreno.whattodo.R;
import me.jorgemoreno.whattodo.data.Categoria;

public class CategoriaEditFragment extends Fragment {
    Categoria cat;
    CatEditListAdapter adapter;

    public CategoriaEditFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int pos = getArguments().getInt("categoria");
        cat = Global.datos.get(pos);
        adapter = new CatEditListAdapter(cat);
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
            Toast.makeText(getContext(), "Categoría actualizada", Toast.LENGTH_SHORT);

            // TODO: may not be preferred if in horizontal?
            super.getActivity().finish();
        });

        // Inflate the layout for this fragment
        return view;
    }
}