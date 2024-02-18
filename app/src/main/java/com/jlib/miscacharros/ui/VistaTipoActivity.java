package com.jlib.miscacharros.ui;

import android.app.Application;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.jlib.miscacharros.Aplicacion;
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
        actualizaVistas();
    }

    public void actualizaVistas()
    {
        tipo = tipos.tipo(pos);
        binding.nombre.setText(tipo.getNombre());
    }

}
