package com.jlib.miscacharros.controlador.contacto;

import android.app.Activity;
import android.content.Intent;

import com.jlib.miscacharros.datos.contacto.ContactosBDAdapter;
import com.jlib.miscacharros.datos.tipo.TiposBDAdapter;
import com.jlib.miscacharros.modelo.Contacto;

public class ControladorContacto
{
    private Activity actividad;
    private ContactosBDAdapter contactos;

    public ControladorContacto(Activity actividad, ContactosBDAdapter contactos) {
        this.actividad = actividad;
        this.contactos = contactos;
    }

    public Activity getActividad() {
        return actividad;
    }

    public void setActividad(Activity actividad) {
        this.actividad = actividad;
    }

    public ContactosBDAdapter contactos() {
        return contactos;
    }

    BDAdapter getContactos() {
        return contactos;
    }

    public void setContactos(TiposBDAdapter contactos) {
        this.contactos = contactos;
    }

    public void listar()
    {
        Intent i = new Intent( actividad, VistaListaContactoActivity.class );
        actividad.startActivity(i);
    }

    public void mostrar(int pos)
    {
        Intent i = new Intent( actividad, VistaDetalleContactoActivity.class );
        i.putExtra("pos", pos);
        actividad.startActivity(i);
    }

    public void borrar(int pos)
    {
        contactos.borrar(pos);
    }

    public void nuevo(Contacto contacto)
    {
        contactos.anade(contacto);
    }

    public void ejemplos()
    {
        contactos.anadeEjemplos();
    }

}
