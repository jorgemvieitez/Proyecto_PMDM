package me.jorgemoreno.whattodo.main;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import me.jorgemoreno.whattodo.R;
import me.jorgemoreno.whattodo.categoria_edit.CategoriaEditActivity;
import me.jorgemoreno.whattodo.data.Categoria;

public class MainListAdapter extends RecyclerView.Adapter<MainListAdapter.ViewHolder> {
    ArrayList<Categoria> datos;
    Context context;

    public MainListAdapter(ArrayList<Categoria> datos) {
        this.datos = datos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_main, parent, false);

        return new ViewHolder(view, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.valor = datos.get(position);
        holder.getTitulo().setText(holder.valor.getNombre());

        context = holder.itemView.getContext();

        if (holder.valor.isCollapsed()) {
            holder.getCollapse().setBackground(AppCompatResources.getDrawable(context, R.drawable.chevron_right));
        }
    }

    @Override
    public int getItemCount() {
        return datos.size();
    }

    public void onClickCollapse(@NonNull ViewHolder holder, int position) {
        boolean collapsed = !holder.valor.isCollapsed();

        holder.valor.setCollapsed(collapsed);
        if (collapsed) {
            holder.getCollapse().setBackground(AppCompatResources.getDrawable(context, R.drawable.chevron_right));
            holder.getLista().setVisibility(View.GONE);
        } else {
            holder.getCollapse().setBackground(AppCompatResources.getDrawable(context, R.drawable.chevron_down));
            holder.getLista().setVisibility(View.VISIBLE);
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        Categoria valor;
        private final TextView titulo;
        private final ImageButton collapse;
        private final ImageButton menu;
        private final View lista; //TODO: convertir a RecyclerView

        public ViewHolder(@NonNull View view, MainListAdapter parent) {
            super(view);

            collapse = view.findViewById(R.id.collapse);
            titulo = view.findViewById(R.id.listTitle);
            menu = view.findViewById(R.id.menuMain);
            lista = view.findViewById(R.id.listaCat);

            collapse.setOnClickListener(v -> {
                parent.onClickCollapse(this, getAdapterPosition());
            });

            menu.setOnClickListener(v -> {
                PopupMenu popupMenu = new PopupMenu(parent.context, menu);
                popupMenu.getMenuInflater().inflate(R.menu.menu_list_main, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener((item) -> {
                    if (item.getItemId() == R.id.menu_list_editar) {
                        Intent i = new Intent(parent.context, CategoriaEditActivity.class);
                        i.putExtra("categoria", getAdapterPosition());
                        parent.context.startActivity(i);
                    }
                    return true;
                });
                popupMenu.show();
            });
        }

        public ImageButton getCollapse() {
            return collapse;
        }

        public TextView getTitulo() {
            return titulo;
        }

        public ImageButton getMenu() {
            return menu;
        }

        //TODO: convertir a RecyclerView
        public View getLista() {
            return lista;
        }
    }
}
