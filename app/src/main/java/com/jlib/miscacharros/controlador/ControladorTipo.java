package com.jlib.miscacharros.controlador;

import android.app.Activity;

public class ControladorTipo
{
    private Activity actividad;
    private RepositorioTipos tipos;

    public void mostrar()
    {
        Intent i = new Intent( actividad, VistaTipo actividad );
        actividad.startActivity(i);
    }

}
