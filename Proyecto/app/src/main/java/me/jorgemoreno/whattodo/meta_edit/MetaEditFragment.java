package me.jorgemoreno.whattodo.meta_edit;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import me.jorgemoreno.whattodo.Global;
import me.jorgemoreno.whattodo.R;
import me.jorgemoreno.whattodo.categoria_edit.CatEditListAdapter;
import me.jorgemoreno.whattodo.data.Categoria;

public class MetaEditFragment extends Fragment {
    Categoria cat;
    int position;
    CatEditListAdapter adapter;

    public MetaEditFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        position = getArguments().getInt("categoria");
        cat = Global.datos.get(position);
        //TODO
        //adapter = new CatEditListAdapter(cat);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meta_edit, container, false);

        RecyclerView rv = view.findViewById(R.id.listaEditMeta);
        rv.setLayoutManager(new LinearLayoutManager(this.getContext()));
        rv.setAdapter(adapter);

        EditText nombre = view.findViewById(R.id.editMetaNombre);
        nombre.setText(cat.getNombre());

        EditText descripcion = view.findViewById(R.id.editMetaDescripcion);
        descripcion.setText(cat.getDescripcion());

        FloatingActionButton fab = view.findViewById(R.id.fabMetaEdit);
        fab.setOnClickListener(v -> {
            // TODO

            // TODO: may not be preferred if in horizontal?
            getActivity().finish();
        });

        // Inflate the layout for this fragment
        return view;
    }
}