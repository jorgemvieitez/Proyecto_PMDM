package me.jorgemoreno.whattodo.main;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;

import me.jorgemoreno.whattodo.BaseDatos;
import me.jorgemoreno.whattodo.Global;
import me.jorgemoreno.whattodo.R;
import me.jorgemoreno.whattodo.ajustes.SettingsActivity;
import me.jorgemoreno.whattodo.data.Categoria;
import me.jorgemoreno.whattodo.meta_edit.MetaEditActivity;

public class MainActivity extends AppCompatActivity {
    MainFragment main;
    public static BaseDatos dbMgr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        dbMgr = BaseDatos.getInstance(this, "datos.db", null, 1);
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Toolbar toolbar = findViewById(R.id.toolbarMain);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        FragmentManager fmgr = getSupportFragmentManager();
        FragmentTransaction trans = fmgr.beginTransaction();

        main = new MainFragment();
        trans.replace(R.id.fragMain, main);

        trans.commit();

    }

    @Override
    protected void onRestart() {
        super.onRestart();

        if (Global.cat_modificada != null) {
            main.adapter.notifyItemChanged(Global.cat_modificada);
            Global.cat_modificada = null;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menuAjustes) {
            Intent i = new Intent(this, SettingsActivity.class);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}