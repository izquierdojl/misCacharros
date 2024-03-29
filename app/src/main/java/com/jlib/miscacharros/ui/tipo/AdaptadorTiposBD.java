package com.jlib.miscacharros.ui.tipo;

import android.database.Cursor;
import android.view.View;
import android.widget.RatingBar;

import com.jlib.miscacharros.datos.tipo.TiposBD;
import com.jlib.miscacharros.datos.tipo.TiposBDAdapter;
import com.jlib.miscacharros.modelo.Tipo;

public class AdaptadorTiposBD extends AdaptadorTipos {

    protected Cursor cursor;

    public AdaptadorTiposBD(TiposBDAdapter tipos, Cursor cursor) {
        this.cursor = cursor;
    }

    public Cursor getCursor() {
        return cursor;
    }

    public void setCursor(Cursor cursor) {
        this.cursor = cursor;
    }

    public Tipo tipoPosicion(int posicion ){
        cursor.moveToPosition(posicion);
        return TiposBD.extraeTipo(cursor);
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
        Tipo tipo = tipoPosicion(posicion);
        holder.personaliza(tipo,holder);
        //holder.itemView.setTag(posicion);
        //holder.itemView.setTag(new Integer(posicion));
        //holder.itemView.setTag(new Integer(tipo.getId()));

        holder.prioridad.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                tipo.setPrioridad((int) rating);
                controller.getTipos().actualiza(tipo.getId(),tipo);
                controller.getTipos().getAdaptador().setCursor(controller.getTipos().extraeCursor());
                //notifyItemChanged(posicion);
                //tipos.sortPrioridad();
            }
        });

        holder.botonBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //int posicion = (int) (v.getTag());
                int posicion = holder.getLayoutPosition();
                deleteItem(holder,tipo,posicion);
            }
        });

        holder.botonColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int posicion = holder.getLayoutPosition();
                selectorColor(holder,tipo,posicion);
            }
        });

    }

    public int getItemCount(){
        return cursor.getCount();
    }

}
