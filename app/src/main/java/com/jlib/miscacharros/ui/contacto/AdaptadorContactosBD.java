package com.jlib.miscacharros.ui.contacto;

import android.database.Cursor;
import android.util.Log;
import android.view.View;

import com.jlib.miscacharros.datos.contacto.ContactosBD;
import com.jlib.miscacharros.datos.contacto.ContactosBDAdapter;
import com.jlib.miscacharros.modelo.Contacto;
import com.jlib.miscacharros.modelo.Tipo;

public class AdaptadorContactosBD extends AdaptadorContactos {

    protected Cursor cursor;

    public AdaptadorContactosBD(ContactosBDAdapter contactos, Cursor cursor) {
        this.cursor = cursor;
    }

    public Cursor getCursor() {
        return cursor;
    }

    public void setCursor(Cursor cursor) {
        this.cursor = cursor;
    }

    public Contacto contactoPosicion(int posicion ){
        cursor.moveToPosition(posicion);
        return ContactosBD.extraeContacto(cursor);
    }

    public int idPosicion(int posicion){
        cursor.moveToPosition(posicion);
        if( cursor.getCount()>0)
            return cursor.getInt(0);
        else
            return -1;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int posicion) {
        Contacto contacto = contactoPosicion(posicion);
        holder.personaliza(contacto);
        holder.botonBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //int posicion = (int) (v.getTag());
                int posicion = holder.getLayoutPosition();
                deleteItem(holder,contacto,posicion);
            }
        });

        holder.botonEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                editItem(holder,contacto,posicion);
            }
        });

    }

    public int getItemCount(){
        return cursor.getCount();
    }

}
