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

public class MainFragment extends Fragment {
    MainListAdapter adapter;

    public MainFragment() {
        adapter = new MainListAdapter(Global.datos);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        RecyclerView listaMain = view.findViewById(R.id.listaMain);
        listaMain.setLayoutManager(new LinearLayoutManager(this.getContext()));
        listaMain.setAdapter(adapter);
        listaMain.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.HORIZONTAL));

        FloatingActionButton fab = view.findViewById(R.id.floatingActionButton);
        fab.setOnClickListener((v) -> {
            Toast.makeText(view.getContext(), "TODO", Toast.LENGTH_SHORT);
        });

        return view;
    }
}