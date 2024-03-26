package com.jlib.miscacharros.ui.contacto;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.jlib.miscacharros.R;
import com.jlib.miscacharros.controlador.contacto.ControladorContacto;
import com.jlib.miscacharros.databinding.VistaContactoElementoListaBinding;
import com.jlib.miscacharros.modelo.Contacto;

import javax.xml.namespace.QName;

public class AdaptadorContactos extends
        RecyclerView.Adapter<AdaptadorContactos.ViewHolder>
{
    private View.OnClickListener onClickListener;
    public ControladorContacto controller;

    //Creamos nuestro ViewHolder, con los elementos a modificar
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView id;
        public ImageView foto;
        public ImageButton botonBorrar;
        public ImageButton botonEditar;
        public ViewHolder(VistaContactoElementoListaBinding itemView) {
            super(itemView.getRoot());
            name = itemView.name;
            id = itemView.IdContacto;
            botonBorrar = itemView.botonBorrar;
            botonEditar = itemView.botonEditar;
        }
        // Personalizamos un ViewHolder a partir de un tipo
        public void personaliza(Contacto contacto) {
            name.setText(contacto.getName());
            id.setText(String.valueOf(contacto.getId()));
            //int id = R.drawable.miscaharros;
            //foto.setScaleType(ImageView.ScaleType.FIT_END);
            //foto.setImageResource();
        }
    }

    public void setOnItemClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    // Creamos el ViewHolder con la vista de un elemento sin personalizar
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflamos la vista desde el xml
        VistaContactoElementoListaBinding v = VistaContactoElementoListaBinding.inflate(LayoutInflater
                .from(parent.getContext()), parent, false);
        v.getRoot().setOnClickListener(onClickListener);
        return new ViewHolder(v);
    }

    // Usando como base el ViewHolder y lo personalizamos
    @Override
    public void onBindViewHolder(ViewHolder holder, int i) {
        Contacto contacto = controller.getContactos().elemento(i);
        holder.personaliza(contacto);
        holder.itemView.setTag(i);
    }
    // Indicamos el número de elementos de la lista

    @Override public int getItemCount() {
        return controller.getContactos().tamano();
    }

    public void deleteItem(ViewHolder holder, Contacto contacto, int posicion)  {
        AlertDialog.Builder builder = new AlertDialog.Builder(holder.itemView.getContext());
        builder.setTitle("Confirmación")
                .setMessage("¿Estás seguro de que quieres borrar el elemento " + holder.name.getText() + " ?")
                .setPositiveButton("Borrar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        controller.borrar(contacto.getId());
                        controller.getContactos().getAdaptador().setCursor(controller.getContactos().extraeCursor());
                        notifyItemRemoved(posicion);
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss(); // Cierra el cuadro de diálogo
                    }
                })
                .show();
    }

    public void editItem(ViewHolder holder, Contacto contacto, int posicion)  {
        controller.mostrar(contacto.getId(),posicion);

    }
}

