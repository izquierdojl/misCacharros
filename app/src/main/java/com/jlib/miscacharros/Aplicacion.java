package com.jlib.miscacharros;

import android.app.Application;

import com.jlib.miscacharros.controlador.ControladorTipo;
import com.jlib.miscacharros.datos.RepositorioTipos;
import com.jlib.miscacharros.datos.TiposLista;
import com.jlib.miscacharros.ui.AdaptadorTipos;

// esta clase inicializa otras para ser utilizada durante la vida de la aplicación

public class Aplicacion extends Application {

    public RepositorioTipos tipos = new TiposLista();
    public AdaptadorTipos adaptador = new AdaptadorTipos(tipos);

    public void onCreate(){
        super.onCreate();
    }

}
