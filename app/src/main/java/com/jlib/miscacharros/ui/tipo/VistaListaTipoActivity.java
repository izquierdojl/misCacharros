package com.jlib.miscacharros.ui.tipo;

import static android.graphics.Color.rgb;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jlib.miscacharros.Aplicacion;
import com.jlib.miscacharros.R;
import com.jlib.miscacharros.databinding.VistaTipoListaBinding;
import com.jlib.miscacharros.modelo.Tipo;

public class VistaListaTipoActivity extends AppCompatActivity{

    public AdaptadorTiposBD adaptador;
    private VistaTipoListaBinding binding;
    private Toolbar barra;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        adaptador = ((Aplicacion) getApplication()).getControllerTipo().getTipos().getAdaptador();
        //contTipo = new ControladorTipo(this,adaptador );

        binding = VistaTipoListaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot()); // obtiene el layout de la actividad

        barra = binding.toolbar;
        setSupportActionBar(barra);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RecyclerView recyclerView = binding.recyclerView;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // así se vería como una tabla
        //recyclerView.setLayoutManager(new GridLayoutManager(this, 2)); // 2 indica el número de columnas en la cuadrícula
        recyclerView.setAdapter(adaptador);

        /*
        adaptador.setOnItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos= binding.recyclerView.getChildAdapterPosition(v);
                contTipo.mostrar(pos);
            }
        });
         */

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar_tipo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if( id == R.id.action_nuevo_tipo ) {
            nuevoTipo();
            return true;
        }
        else if( id == android.R.id.home ) {
            finish();
            return true;
        }
        else
        {
            return super.onOptionsItemSelected(item);
        }
    }

    public void nuevoTipo()
    {
        EditText entrada = new EditText(this);
        entrada.setText("");
        new AlertDialog.Builder(this)
                .setTitle("Nuevo tipo")
                .setMessage("Indica su nombre:")
                .setView(entrada)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Tipo tipo = new Tipo();
                        tipo.setNombre(entrada.getText().toString());
                        if( !tipo.getNombre().isEmpty() ) {
                            tipo.setPrioridad(1);
                            tipo.setColor(rgb(208, 211, 212));
                            adaptador.controller.nuevo(tipo);
                            Cursor cursorAsigna = adaptador.controller.getTipos().extraeCursor();
                            adaptador.setCursor(cursorAsigna);
                            adaptador.notifyItemInserted(adaptador.getItemCount());
                        }
                    }})
                .setNegativeButton("Cancelar", null)
                .show();
    }
}
