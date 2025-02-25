package me.jorgemoreno.whattodo.categoria_edit;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import me.jorgemoreno.whattodo.BaseDatos;
import me.jorgemoreno.whattodo.Global;
import me.jorgemoreno.whattodo.R;
import me.jorgemoreno.whattodo.data.Categoria;
import me.jorgemoreno.whattodo.data.Meta;
import me.jorgemoreno.whattodo.dialogos.YesNo;
import me.jorgemoreno.whattodo.meta_edit.MetaEditActivity;

public class CatEditListAdapter extends RecyclerView.Adapter<CatEditListAdapter.ViewHolder> {
    Categoria datos;
    Context context;
    Fragment parent;

    public CatEditListAdapter(Categoria datos, Fragment parent) {
        this.datos = datos;
        this.parent = parent;
    }

    @NonNull
    @Override
    public CatEditListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // generating this at the time of constructing causes it to be null apparently
        if (this.context == null) this.context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_categoria_edit, parent, false);

        return new ViewHolder(view, this);
    }

    @Override
    public void onBindViewHolder(@NonNull CatEditListAdapter.ViewHolder holder, int position) {
        holder.valor = datos.getMetaAt(position);

        if (holder.valor.isCompletada()) {
            holder.itemView.setBackgroundTintList(
                context.getResources().getColorStateList(
                    R.color.accent_background_light_greyed,
                    context.getTheme()
                )
            );

            holder.nombre.setText("✓ " + holder.valor.getNombre());
        } else {
            holder.nombre.setText(holder.valor.getNombre());
        }

    }

    @Override
    public int getItemCount() {
        return datos.getMetaCount();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        Meta valor;
        TextView nombre;
        ImageButton editar;
        ImageButton borrar;

        public ViewHolder(@NonNull View view, CatEditListAdapter parent) {
            super(view);

            nombre = view.findViewById(R.id.nombre);
            editar = view.findViewById(R.id.editMeta);
            borrar = view.findViewById(R.id.deleteMeta);

            editar.setOnClickListener((__) -> {
                Intent i = new Intent(parent.context, MetaEditActivity.class);
                i.putExtra("categoria", Global.getDatos().indexOf(parent.datos));
                i.putExtra("meta", getAdapterPosition());
                parent.context.startActivity(i);
            });

            borrar.setOnClickListener((__) -> {
                YesNo dialogo = new YesNo(
                        "¿Borrar esta meta?",
                        "¿Seguro que quieres borrar esta meta? No podrás deshacer esto.",
                        (dialog, which) -> {
                            parent.datos.removeMeta(valor);
                            parent.notifyItemRemoved(getAdapterPosition());
                            BaseDatos.deleteMeta(BaseDatos.getInstance().getWritableDatabase(), valor);
                            Toast.makeText(parent.context, "Meta borrada", Toast.LENGTH_SHORT).show();
                            Global.cat_modificada = Global.getDatos().indexOf(parent.datos);
                        },
                        (dialog, which) -> {});
                dialogo.show(parent.parent.getActivity().getSupportFragmentManager(), "borrar");
            });
        }
    }
}
