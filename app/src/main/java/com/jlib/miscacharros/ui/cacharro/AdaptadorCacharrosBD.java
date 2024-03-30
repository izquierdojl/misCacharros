package com.jlib.miscacharros.ui.cacharro;

import android.app.Activity;
import android.database.Cursor;
import android.view.View;

import com.jlib.miscacharros.datos.cacharro.CacharrosBD;
import com.jlib.miscacharros.datos.cacharro.CacharrosBDAdapter;
import com.jlib.miscacharros.modelo.Cacharro;

public class AdaptadorCacharrosBD extends AdaptadorCacharros {

    protected Cursor cursor;
    protected Activity activity;

    public AdaptadorCacharrosBD(CacharrosBDAdapter cacharros, Cursor cursor) {
        this.cursor = cursor;
    }

    public Cursor getCursor() {
        return cursor;
    }

    public void setCursor(Cursor cursor) {
        this.cursor = cursor;
    }

    public Cacharro cacharroPosicion(int posicion ){
        cursor.moveToPosition(posicion);
        return CacharrosBD.extraeCacharro(cursor);
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
        Cacharro cacharro = cacharroPosicion(posicion);
        if( cacharro != null ) {
            holder.personaliza(cacharro);
            holder.botonBorrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //int posicion = (int) (v.getTag());
                    int posicion = holder.getLayoutPosition();
                    deleteItem(holder, cacharro, posicion);
                }
            });

            holder.botonEditar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editItem(activity, holder, cacharro, posicion);
                }
            });
        }

    }

    public int getItemCount(){
        return cursor.getCount();
    }

}
