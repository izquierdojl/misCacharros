package com.jlib.miscacharros.ui;

import android.app.Application;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.jlib.miscacharros.Aplicacion;
import com.jlib.miscacharros.R;
import com.jlib.miscacharros.controlador.ControladorTipo;
import com.jlib.miscacharros.datos.RepositorioTipos;
import com.jlib.miscacharros.modelo.Tipo;
import com.jlib.miscacharros.databinding.VistaTipoBinding;

public class VistaTipoActivity extends AppCompatActivity {
    private RepositorioTipos tipos;
    private ControladorTipo contTipo;
    private int pos;
    private Tipo tipo;
    private VistaTipoBinding binding;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        binding = VistaTipoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot()); // obtiene el layout de la actividad
        Bundle extras = getIntent().getExtras();
        pos = extras.getInt("pos",0);
        tipos = ((Aplicacion) getApplication()).tipos;
        contTipo = new ControladorTipo(this, tipos); // creamos el objeto controlador
        tipo = tipos.tipo(pos);

        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);

        actualizaVistas();
    }

    public void actualizaVistas()
    {
        tipo = tipos.tipo(pos);
        binding.nombre.setText(tipo.getNombre()+" ("+tipo.getOrden()+")");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.vista_tipo, menu);
        Log.d("VistaTipoActivity", "Menú inflado correctamente"); // Agregar esta línea
        return true;
    }
/*
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.accion_editar:
                // Acción para editar
                return true;
            case R.id.accion_borrar:
                // Acción para borrar
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
 */


}
