package me.jorgemoreno.whattodo.meta_edit;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.TreeMap;

import me.jorgemoreno.whattodo.BaseDatos;
import me.jorgemoreno.whattodo.R;
import me.jorgemoreno.whattodo.data.Meta;
import me.jorgemoreno.whattodo.data.Tarea;
import me.jorgemoreno.whattodo.dialogos.YesNo;

public class MetaEditListAdapter extends RecyclerView.Adapter<MetaEditListAdapter.ViewHolder> {
    Meta datos;
    Context context;
    Fragment parent;

    TreeMap<Integer, ViewHolder> holders = new TreeMap<>();

    public MetaEditListAdapter(Meta datos, Fragment parent) {
        this.datos = datos;
        this.parent = parent;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // generating this at the time of constructing causes it to be null apparently
        if (this.context == null) this.context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_meta_edit, parent, false);

        return new ViewHolder(view, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.valor = datos.getTareaAt(position);

        holder.nombre.setText(holder.valor.getNombre());
        holder.checkBox.setChecked(holder.valor.isCompletada());

        holders.put(position, holder);
    }

    public Optional<ViewHolder> getHolder(int position) {
        ViewHolder vh = holders.get(position);
        return Optional.ofNullable(vh);
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
                            BaseDatos.deleteTarea(BaseDatos.getInstance().getWritableDatabase(), valor);
                        },
                        (dialog, which) -> {});
                dialogo.show(parent.parent.getActivity().getSupportFragmentManager(), "borrar");
            });

            checkBox.setOnClickListener((button) -> {
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(parent.context);
                if (((CheckBox)button).isChecked() && prefs.getBoolean("aplauso", false)) {
                    MediaPlayer audio = MediaPlayer.create(parent.context, R.raw.clap);
                    audio.start();
                }
            });
        }
    }
}
