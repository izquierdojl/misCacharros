package com.jlib.miscacharros.datos.cacharro;

import android.content.Context;
import com.jlib.miscacharros.modelo.Cacharro;
import com.jlib.miscacharros.ui.cacharro.AdaptadorCacharrosBD;

public class CacharrosBDAdapter extends CacharrosBD {

    private AdaptadorCacharrosBD adaptador;

    public CacharrosBDAdapter(Context context) {
        super(context);
    }

    public AdaptadorCacharrosBD getAdaptador() {
        return adaptador;
    }

    public void setAdaptador(AdaptadorCacharrosBD adaptador) {
        this.adaptador = adaptador;
    }

    public Cacharro elementoPos(int pos )
    {
        return adaptador.cacharroPosicion(pos);
    }

}
