package me.jorgemoreno.whattodo.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;

import me.jorgemoreno.whattodo.Global;
import me.jorgemoreno.whattodo.R;
import me.jorgemoreno.whattodo.data.Categoria;
import me.jorgemoreno.whattodo.dialogos.CreateWithName;

public class MainFragment extends Fragment {
    MainListAdapter adapter;

    public MainFragment() {
        adapter = new MainListAdapter(this, Global.datos);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        RecyclerView listaMain = view.findViewById(R.id.listaMain);
        listaMain.setLayoutManager(new LinearLayoutManager(getContext()));
        listaMain.setAdapter(adapter);
        listaMain.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        FloatingActionButton fab = view.findViewById(R.id.floatingActionButton);
        fab.setOnClickListener((v) -> {
            CreateWithName dialogo = new CreateWithName(
                    "Crear categoría",
                    "Elige el nombre de la categoría",
                    (name) -> {
                        Global.datos.add(new Categoria(name));
                        adapter.notifyItemInserted(Global.datos.size() - 1);
                    },
                    (dialog, which) -> {}
            );
            dialogo.show(getActivity().getSupportFragmentManager(), "cat_crear");
        });

        return view;
    }
}