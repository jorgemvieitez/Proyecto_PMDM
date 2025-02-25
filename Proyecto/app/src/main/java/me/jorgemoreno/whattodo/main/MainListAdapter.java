package me.jorgemoreno.whattodo.main;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.stream.Collectors;

import me.jorgemoreno.whattodo.R;
import me.jorgemoreno.whattodo.categoria_edit.CategoriaEditActivity;
import me.jorgemoreno.whattodo.data.Categoria;
import me.jorgemoreno.whattodo.data.Meta;
import me.jorgemoreno.whattodo.dialogos.YesNo;

public class MainListAdapter extends RecyclerView.Adapter<MainListAdapter.ViewHolder> {
    ArrayList<Categoria> datos;
    Fragment parent;
    Context context;

    public MainListAdapter(Fragment parent, ArrayList<Categoria> datos) {
        this.parent = parent;
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
        context = parent.getContext();
        holder.valor = datos.get(position);

        holder.getTitulo().setText(holder.valor.getNombre());

        // TODO: recyclerview
        String texto = "<ul>"  +datos.get(position).getMetas()
            .map(
                meta -> "<li> " + (meta.isCompletada() ? "✓ " : "") + meta.getNombre() + "</li>"
            ).collect(Collectors.joining("\n"))
                + "</ul>";
        if (texto == "")
            holder.getLista().setText("(No hay metas)");
        else
            holder.getLista().setText(Html.fromHtml(texto, Html.FROM_HTML_MODE_COMPACT));

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
        private final TextView lista; //TODO: convertir a RecyclerView

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
                    } else if (item.getItemId() == R.id.menu_list_borrar) {
                        YesNo dialogo = new YesNo(
                                "¿Borrar esta categoría?",
                                "¿Seguro que quieres borrar esta categoría? No podrás deshacer esto",
                                (dialog, which) -> {
                                    parent.datos.remove(valor);
                                    Toast.makeText(parent.context, "Categoría borrada", Toast.LENGTH_SHORT).show();
                                    parent.notifyItemRemoved(getAdapterPosition());
                                    },
                                (dialog, which) -> {});
                        dialogo.show(parent.parent.getActivity().getSupportFragmentManager(), "borrar");
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
        public TextView getLista() {
            return lista;
        }
    }
}
