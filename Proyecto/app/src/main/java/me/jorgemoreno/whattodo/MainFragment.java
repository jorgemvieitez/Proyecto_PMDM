package me.jorgemoreno.whattodo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;

import me.jorgemoreno.whattodo.data.Categoria;

public class MainFragment extends Fragment {
    MainListAdapter adapter;

    public MainFragment() {
        adapter = new MainListAdapter(new ArrayList<>(Arrays.asList(
                new Categoria("test1"),
                new Categoria("test2"),
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
        )));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        RecyclerView listaMain = view.findViewById(R.id.listaMain);
        listaMain.setLayoutManager(new LinearLayoutManager(this.getContext()));
        listaMain.setAdapter(adapter);
        listaMain.addItemDecoration(new DividerItemDecoration(getContext(), getResources().getConfiguration().orientation));

        return view;
    }
}