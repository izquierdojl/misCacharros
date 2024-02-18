package com.jlib.miscacharros;

import android.app.Application;
import com.jlib.miscacharros.datos.RepositorioTipos;
import com.jlib.miscacharros.datos.TiposLista;

// esta clase inicializa otras para ser utilizada durante la vida de la aplicación

public class Aplicacion extends Application {

    public RepositorioTipos tipos = new TiposLista();

    public void onCreate(){
        super.onCreate();
    }

}
