package com.jlib.miscacharros.controlador;

import android.app.Activity;
import android.content.Intent;

import com.jlib.miscacharros.datos.RepositorioTipos;
import com.jlib.miscacharros.ui.VistaTipoActivity;

public class ControladorTipo
{
    private Activity actividad;
    private RepositorioTipos tipos;

    public ControladorTipo(Activity actividad, RepositorioTipos tipos) {
        this.actividad = actividad;
        this.tipos = tipos;
    }

    public void mostrar(int pos)
    {
        Intent i = new Intent( actividad, VistaTipoActivity.class );
        i.putExtra("pos", pos);
        actividad.startActivity(i);
    }

}
