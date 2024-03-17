package com.jlib.miscacharros.controlador.tipo;

import android.app.Activity;
import android.content.Intent;
import com.jlib.miscacharros.datos.tipo.TiposBDAdapter;
import com.jlib.miscacharros.modelo.Tipo;
import com.jlib.miscacharros.ui.tipo.VistaListaTipoActivity;
import com.jlib.miscacharros.ui.tipo.VistaDetalleTipoActivity;

public class ControladorTipo
{
    private Activity actividad;
    //private RepositorioTipos tipos;
    private TiposBDAdapter tipos;

    public ControladorTipo(Activity actividad, TiposBDAdapter tipos) {
        this.actividad = actividad;
        this.tipos = tipos;
    }

    public Activity getActividad() {
        return actividad;
    }

    public void setActividad(Activity actividad) {
        this.actividad = actividad;
    }

    public TiposBDAdapter getTipos() {
        return tipos;
    }

    public void setTipos(TiposBDAdapter tipos) {
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
    }

    public void nuevo(Tipo tipo)
    {
        tipos.anade(tipo);
    }

    public void ejemplos()
    {
        tipos.anadeEjemplos();
    }

}
