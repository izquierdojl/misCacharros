package com.jlib.miscacharros;

import android.app.Application;

import com.jlib.miscacharros.controlador.contacto.ControladorContacto;
import com.jlib.miscacharros.controlador.tipo.ControladorTipo;
import com.jlib.miscacharros.datos.contacto.ContactosBDAdapter;
import com.jlib.miscacharros.datos.tipo.TiposBD;
import com.jlib.miscacharros.datos.tipo.TiposBDAdapter;
import com.jlib.miscacharros.ui.contacto.AdaptadorContactosBD;
import com.jlib.miscacharros.ui.tipo.AdaptadorTiposBD;

// esta clase inicializa otras para ser utilizada durante la vida de la aplicación

public class Aplicacion extends Application {


    //private RepositorioTipos tipos;
    private TiposBDAdapter tipos;
    private ContactosBDAdapter contactos;
    private ControladorTipo controllerTipo = null;
    private ControladorContacto controllerContacto = null;
    private AdaptadorTiposBD adaptador;
    private AdaptadorContactosBD adaptadorContactos;

    public ControladorTipo getControllerTipo() {
        return controllerTipo;
    }
    public ControladorContacto getControllerContacto() {
        return controllerContacto;
    }

    public void setControllerTipo(ControladorTipo controllerTipo) {
        this.controllerTipo = controllerTipo;
    }

    public void setControllerContacto(ControladorContacto controllerContacto) {
        this.controllerContacto = controllerContacto;
    }

    public void onCreate(){
        super.onCreate();

        tipos = new TiposBDAdapter(this);
        adaptador = new AdaptadorTiposBD(tipos,tipos.extraeCursor());
        tipos.setAdaptador(adaptador);
        controllerTipo = new ControladorTipo(null,tipos);
        adaptador.controller = controllerTipo;

        contactos = new ContactosBDAdapter(this);
        adaptadorContactos = new AdaptadorContactosBD(contactos,contactos.extraeCursor());
        contactos.setAdaptador(adaptadorContactos);
        controllerContacto = new ControladorContacto(null,contactos);
        adaptadorContactos.controller = controllerContacto;

    }

}
