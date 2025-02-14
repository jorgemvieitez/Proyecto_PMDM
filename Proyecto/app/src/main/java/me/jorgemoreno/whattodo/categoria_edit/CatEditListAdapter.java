package me.jorgemoreno.whattodo.categoria_edit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import me.jorgemoreno.whattodo.R;
import me.jorgemoreno.whattodo.data.Categoria;
import me.jorgemoreno.whattodo.data.Meta;

public class CatEditListAdapter extends RecyclerView.Adapter<CatEditListAdapter.ViewHolder> {
    Categoria datos;
    Context context;

    public CatEditListAdapter(Categoria datos) {
        this.datos = datos;
    }

    @NonNull
    @Override
    public CatEditListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_categoria_edit, parent, false);

        return new ViewHolder(view, this);
    }

    @Override
    public void onBindViewHolder(@NonNull CatEditListAdapter.ViewHolder holder, int position) {
        holder.valor = datos.getMetas().skip(position).findFirst().orElseThrow();
        context = holder.itemView.getContext();
    }

    @Override
    public int getItemCount() {
        return datos.getMetaCount();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        Meta valor;

        public ViewHolder(@NonNull View view, CatEditListAdapter parent) {
            super(view);
        }
    }
}
