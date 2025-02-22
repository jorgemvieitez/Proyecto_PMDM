package me.jorgemoreno.whattodo.dialogos;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class YesNo extends DialogFragment {
    private String title;
    private String message;
    private DialogInterface.OnClickListener si;
    private DialogInterface.OnClickListener no;

    public YesNo(String title, String message, DialogInterface.OnClickListener si, DialogInterface.OnClickListener no) {
        this.title = title;
        this.message = message;
        this.si = si;
        this.no = no;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title).setMessage(message)
                .setPositiveButton(android.R.string.yes, si)
                .setNegativeButton(android.R.string.no, no);
        return builder.create();
    }
}
