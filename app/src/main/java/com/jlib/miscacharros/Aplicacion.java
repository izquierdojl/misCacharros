package com.jlib.miscacharros;

import android.app.Application;

import com.jlib.miscacharros.datos.tipo.RepositorioTipos;
import com.jlib.miscacharros.datos.tipo.TiposLista;
import com.jlib.miscacharros.ui.tipo.AdaptadorTipos;

// esta clase inicializa otras para ser utilizada durante la vida de la aplicación

public class Aplicacion extends Application {

    public RepositorioTipos tipos = new TiposLista();
    public AdaptadorTipos adaptador = new AdaptadorTipos(tipos);

    public void onCreate(){
        super.onCreate();
    }

}
