package com.jlib.miscacharros.datos.contacto;

import android.content.Context;

import com.jlib.miscacharros.modelo.Contacto;
import com.jlib.miscacharros.ui.contacto.AdaptadorContactosBD;

public class ContactosBDAdapter extends ContactosBD {

    private AdaptadorContactosBD adaptador;

    public ContactosBDAdapter(Context context) {
        super(context);
    }

    public AdaptadorContactosBD getAdaptador() {
        return adaptador;
    }

    public void setAdaptador(AdaptadorContactosBD adaptador) {
        this.adaptador = adaptador;
    }

    public Contacto elementoPos(int pos )
    {
        return adaptador.contactoPosicion(pos);
    }

}
