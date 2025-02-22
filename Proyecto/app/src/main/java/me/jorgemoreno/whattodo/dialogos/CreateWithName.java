package me.jorgemoreno.whattodo.dialogos;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.textfield.TextInputLayout;

import me.jorgemoreno.whattodo.R;

public class CreateWithName extends DialogFragment {
    public String title;
    public String message;
    public OnNameGivenListener aceptar;
    public DialogInterface.OnClickListener cancelar;

    public interface OnNameGivenListener {
        void onNameGiven(String name);
    }

    public CreateWithName(String title, String message, OnNameGivenListener aceptar, DialogInterface.OnClickListener cancelar) {
        this.title = title;
        this.message = message;
        this.aceptar = aceptar;
        this.cancelar = cancelar;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        View viewInflated = getLayoutInflater().inflate(R.layout.dialog_createwithname, (ViewGroup)getView(), false);

        EditText input = viewInflated.findViewById(R.id.value);
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_SHORT_MESSAGE);

        builder.setView(viewInflated);

        builder.setTitle(title).setMessage(message)
            .setPositiveButton(android.R.string.ok, (dialog, which) -> aceptar.onNameGiven(input.getText().toString()))
            .setNegativeButton(android.R.string.cancel, cancelar);

        return builder.create();
    }
}
