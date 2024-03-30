package com.jlib.miscacharros.controlador.cacharro;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.jlib.miscacharros.datos.cacharro.CacharrosBDAdapter;
import com.jlib.miscacharros.modelo.Cacharro;
import com.jlib.miscacharros.ui.cacharro.VistaDetalleCacharroActivity;
import com.jlib.miscacharros.ui.cacharro.VistaListaCacharroFragment;

public class ControladorCacharro
{
    private Activity actividad;


    private CacharrosBDAdapter cacharros;

    public ControladorCacharro(Activity actividad, CacharrosBDAdapter cacharros) {
        this.actividad = actividad;
        this.cacharros = cacharros;
    }

    public Activity getActividad() {
        return actividad;
    }

    public void setActividad(Activity actividad) {
        this.actividad = actividad;
    }

    public CacharrosBDAdapter contactos() {
        return cacharros;
    }

    public CacharrosBDAdapter getContactos() {
        return cacharros;
    }

    public void setCacharros(CacharrosBDAdapter cacharros) {
        this.cacharros = cacharros;
    }

    public CacharrosBDAdapter getCacharros() {
        return cacharros;
    }

    public void listar()
    {
        Intent i = new Intent( actividad, VistaListaCacharroFragment.class );
        actividad.startActivity(i);
    }

    public void nuevo()
    {
        Intent i = new Intent( actividad, VistaDetalleCacharroActivity.class );
        i.putExtra("modo", 1);
        actividad.startActivity(i);
    }

    public void mostrar(Activity actividad, int id,int posicion)
    {
        Intent i = new Intent( actividad, VistaDetalleCacharroActivity.class );
        i.putExtra("modo", 2);
        i.putExtra("id", id);
        i.putExtra("pos", posicion);
        actividad.startActivity(i);
    }

    public void borrar(int pos)
    {
        cacharros.borrar(pos);
    }

    public void anade(Cacharro cacharro)
    {
        cacharros.anade(cacharro);
    }

    public void actualiza( int id, Cacharro cacharro )
    {
        cacharros.actualiza( id, cacharro );
    }

    public void ejemplos()
    {
        cacharros.anadeEjemplos();
    }

}
