package com.jlib.miscacharros.datos.tipo;

import android.content.Context;

import com.jlib.miscacharros.modelo.Tipo;
import com.jlib.miscacharros.ui.tipo.AdaptadorTiposBD;

public class TiposBDAdapter extends TiposBD{

    private AdaptadorTiposBD adaptador;

    public TiposBDAdapter(Context context) {
        super(context);
    }

    public AdaptadorTiposBD getAdaptador() {
        return adaptador;
    }

    public void setAdaptador(AdaptadorTiposBD adaptador) {
        this.adaptador = adaptador;
    }

    public Tipo elementoPos( int pos ){
        return adaptador.tipoPosicion(pos);
    }

}
