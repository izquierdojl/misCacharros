package com.jlib.miscacharros;

import android.app.Application;

import com.jlib.miscacharros.datos.RepositorioTipos;
import com.jlib.miscacharros.datos.TiposLista;

public class Aplicacion extends Application {

    public RepositorioTipos tipos = new TiposLista();

    public void onCreate(){
        super.onCreate();
    }

}
