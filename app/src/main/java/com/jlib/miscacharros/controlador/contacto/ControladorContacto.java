package com.jlib.miscacharros.controlador.contacto;

import android.app.Activity;
import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.jlib.miscacharros.datos.contacto.ContactosBDAdapter;
import com.jlib.miscacharros.datos.tipo.TiposBDAdapter;
import com.jlib.miscacharros.modelo.Contacto;
import com.jlib.miscacharros.ui.contacto.VistaDetalleContactoActivity;
import com.jlib.miscacharros.ui.contacto.VistaListaContactoActivity;

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

    public ContactosBDAdapter getContactos() {
        return contactos;
    }

    public void setContactos(ContactosBDAdapter contactos) {
        this.contactos = contactos;
    }

    public void listar()
    {
        Intent i = new Intent( actividad, VistaListaContactoActivity.class );
        actividad.startActivity(i);
    }

    public void nuevo()
    {
        Intent i = new Intent( actividad, VistaDetalleContactoActivity.class );
        i.putExtra("modo", 1);
        actividad.startActivity(i);
    }

    public void mostrar(int id,int posicion)
    {
        Intent i = new Intent( actividad, VistaDetalleContactoActivity.class );
        i.putExtra("modo", 2);
        i.putExtra("id", id);
        i.putExtra("pos", posicion);
        actividad.startActivity(i);
        //actividad.startActivityForResult(i,1);
    }

    public void borrar(int pos)
    {
        contactos.borrar(pos);
    }

    public void anade(Contacto contacto)
    {
        contactos.anade(contacto);
    }

    public void actualiza( int id, Contacto contacto )
    {
        contactos.actualiza( id, contacto );
    }

    public void ejemplos()
    {
        contactos.anadeEjemplos();
    }


}
