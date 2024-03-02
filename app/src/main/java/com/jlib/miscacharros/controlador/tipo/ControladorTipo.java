package com.jlib.miscacharros.controlador.tipo;

import android.app.Activity;
import android.content.Intent;

import com.jlib.miscacharros.datos.tipo.RepositorioTipos;
import com.jlib.miscacharros.modelo.Tipo;
import com.jlib.miscacharros.ui.tipo.VistaListaTipoActivity;
import com.jlib.miscacharros.ui.tipo.VistaDetalleTipoActivity;

public class ControladorTipo
{
    private Activity actividad;
    private RepositorioTipos tipos;

    public ControladorTipo(Activity actividad, RepositorioTipos tipos) {
        this.actividad = actividad;
        this.tipos = tipos;
    }

    public void listar()
    {
        Intent i = new Intent( actividad, VistaListaTipoActivity.class );
        actividad.startActivity(i);
    }

    public void mostrar(int pos)
    {
        Intent i = new Intent( actividad, VistaDetalleTipoActivity.class );
        i.putExtra("pos", pos);
        actividad.startActivity(i);
    }

    public void borrar(int pos)
    {
        tipos.borrar(pos);
        actividad.finish();
    }

    public void nuevo(String nombre, int orden, int prioridad )
    {
        if( !nombre.isEmpty() ) {
            if (orden == 0)
                orden = 1;
            Tipo nuevo = new Tipo( nombre , orden , prioridad );
            tipos.anade(nuevo);
        }
    }



}
