package me.jorgemoreno.whattodo.meta_edit;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import me.jorgemoreno.whattodo.Global;
import me.jorgemoreno.whattodo.R;
import me.jorgemoreno.whattodo.categoria_edit.CatEditListAdapter;
import me.jorgemoreno.whattodo.data.Categoria;
import me.jorgemoreno.whattodo.data.Meta;
import me.jorgemoreno.whattodo.data.Tarea;
import me.jorgemoreno.whattodo.dialogos.YesNo;

public class MetaEditListAdapter extends RecyclerView.Adapter<MetaEditListAdapter.ViewHolder> {
    Meta datos;
    Context context;
    Fragment parent;

    public MetaEditListAdapter(Meta datos, Fragment parent) {
        this.datos = datos;
        this.parent = parent;
    }

    @NonNull
    @Override
    public MetaEditListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_meta_edit, parent, false);

        return new MetaEditListAdapter.ViewHolder(view, this);
    }

    @Override
    public void onBindViewHolder(@NonNull MetaEditListAdapter.ViewHolder holder, int position) {
        holder.valor = datos.getTareaAt(position);
        context = holder.itemView.getContext();

        holder.nombre.setText(holder.valor.getNombre());
    }

    @Override
    public int getItemCount() {
        return datos.getTareaCount();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        Tarea valor;
        CheckBox checkBox;
        EditText nombre;
        ImageButton borrar;

        public ViewHolder(@NonNull View view, MetaEditListAdapter parent) {
            super(view);

            nombre = view.findViewById(R.id.editTareaNombre);
            checkBox = view.findViewById(R.id.editTareaCheckbox);
            borrar = view.findViewById(R.id.deleteTarea);

            borrar.setOnClickListener((__) -> {
                YesNo dialogo = new YesNo(
                        "¿Borrar esta tarea?",
                        "¿Seguro que quieres borrar esta tarea? No podrás deshacer esto.",
                        (dialog, which) -> {
                            parent.datos.removeTarea(valor);
                            Toast.makeText(parent.context, "Tarea borrada", Toast.LENGTH_SHORT).show();
                            parent.notifyItemRemoved(getAdapterPosition());
                        },
                        (dialog, which) -> {});
                dialogo.show(parent.parent.getActivity().getSupportFragmentManager(), "borrar");
            });
        }
    }
}
