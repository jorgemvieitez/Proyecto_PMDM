package me.jorgemoreno.whattodo.categoria_edit;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import me.jorgemoreno.whattodo.Global;
import me.jorgemoreno.whattodo.R;
import me.jorgemoreno.whattodo.main.MainActivity;

public class CategoriaEditActivity extends AppCompatActivity {
    CategoriaEditFragment catEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_categoria_edit);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Toolbar toolbar = findViewById(R.id.toolbarCatEdit);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FragmentManager fmgr = getSupportFragmentManager();
        FragmentTransaction trans = fmgr.beginTransaction();

        catEdit = new CategoriaEditFragment();
        catEdit.setArguments(getIntent().getExtras());
        trans.replace(R.id.fragCatEdit, catEdit);

        trans.commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        if (Global.meta_modificada != null) {
            catEdit.adapter.notifyItemChanged(Global.meta_modificada);
            Global.cat_modificada = catEdit.position;
            Global.meta_modificada = null;
        }
    }
}