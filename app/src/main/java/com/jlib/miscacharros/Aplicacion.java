package com.jlib.miscacharros;

import android.app.Application;

import com.jlib.miscacharros.controlador.tipo.ControladorTipo;
import com.jlib.miscacharros.datos.tipo.TiposBD;
import com.jlib.miscacharros.datos.tipo.TiposBDAdapter;
import com.jlib.miscacharros.ui.tipo.AdaptadorTiposBD;

// esta clase inicializa otras para ser utilizada durante la vida de la aplicación

public class Aplicacion extends Application {


    //private RepositorioTipos tipos;
    private TiposBDAdapter tipos;
    private ControladorTipo controllerTipo = null;
    private AdaptadorTiposBD adaptador;

    public ControladorTipo getControllerTipo() {
        return controllerTipo;
    }

    public void setControllerTipo(ControladorTipo controllerTipo) {
        this.controllerTipo = controllerTipo;
    }

    public void onCreate(){
        super.onCreate();
        tipos = new TiposBDAdapter(this);
        adaptador = new AdaptadorTiposBD(tipos,tipos.extraeCursor());
        tipos.setAdaptador(adaptador);
        controllerTipo = new ControladorTipo(null,tipos);
        adaptador.controller = controllerTipo;
        //adaptador = new AdaptadorTiposBD(tipos,tipos.extraeCursor());
        //tipos.setAdaptador(adaptador);
    }

}
