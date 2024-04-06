package com.jlib.miscacharros.ui.cacharro;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jlib.miscacharros.Aplicacion;
import com.jlib.miscacharros.controlador.cacharro.ControladorCacharro;
import com.jlib.miscacharros.controlador.tipo.ControladorTipo;
import com.jlib.miscacharros.databinding.VistaCacharroElementoListaBinding;
import com.jlib.miscacharros.modelo.Cacharro;
import com.jlib.miscacharros.modelo.Tipo;

public class AdaptadorCacharros extends
        RecyclerView.Adapter<AdaptadorCacharros.ViewHolder>
{
    private View.OnClickListener onClickListener;
    public ControladorCacharro controller;
    public ControladorTipo controllerTipo;

    //Creamos nuestro ViewHolder, con los elementos a modificar
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView fabricante;
        private TextView id;
        private ImageView imagen;
        private ImageView archivo;
        private ImageView alerta;
        protected ImageButton botonBorrar;
        protected ImageButton botonEditar;
        public ViewHolder(VistaCacharroElementoListaBinding itemView) {
            super(itemView.getRoot());
            name = itemView.name;
            id = itemView.IdCacharro;
            imagen = itemView.imagen;
            archivo = itemView.imgAttach;
            alerta = itemView.imgAlarm;
            fabricante = itemView.fabricante;
            botonBorrar = itemView.botonBorrar;
            botonEditar = itemView.botonEditar;
        }
        // Personalizamos un ViewHolder a partir de un tipo
        public void personaliza(ViewHolder holder,Cacharro cacharro,Tipo tipo) {
            id.setText(String.valueOf(cacharro.getId()));
            name.setText(cacharro.getName());
            fabricante.setText(cacharro.getFabricante());
            if( cacharro.getImagen()!=null && !cacharro.getImagen().isEmpty() ) {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = false;
                Bitmap photoBmp = BitmapFactory.decodeFile(holder.itemView.getContext().getFilesDir().getAbsolutePath() + "/" + cacharro.getUid() + "/img/" + cacharro.getImagen(), options);
                imagen.setImageBitmap(photoBmp);
                imagen.setVisibility(View.VISIBLE);
            }
            if( cacharro.getArchivo()!=null && !cacharro.getArchivo().isEmpty() ){
                archivo.setVisibility(View.VISIBLE);
            }else{
                archivo.setVisibility(View.GONE);
            }
            if( cacharro.isAviso() )
            {
                alerta.setVisibility(View.VISIBLE);
            }else{
                alerta.setVisibility(View.GONE);
            }
            holder.itemView.setBackgroundColor(tipo.getColor());

        }

    }

    public void setOnItemClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    // Creamos el ViewHolder con la vista de un elemento sin personalizar
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflamos la vista desde el xml
        @NonNull VistaCacharroElementoListaBinding v = VistaCacharroElementoListaBinding.inflate(LayoutInflater
                .from(parent.getContext()), parent, false);
        v.getRoot().setOnClickListener(onClickListener);

        return new ViewHolder(v);
    }

    // Usando como base el ViewHolder y lo personalizamos
    @Override
    public void onBindViewHolder(ViewHolder holder, int i) {
        if( controller.getCacharros().tamano() > 0 ) {
            Cacharro cacharro = controller.getCacharros().elemento(i);
            Tipo tipo = controllerTipo.getTipos().elemento(cacharro.getIdTipo());
            holder.personaliza(holder,cacharro,tipo);
            holder.itemView.setTag(i);
        }
    }
    // Indicamos el número de elementos de la lista

    @Override public int getItemCount() {
        return controller.getCacharros().tamano();
    }

    public void deleteItem(ViewHolder holder, Cacharro cacharro, int posicion)  {
        AlertDialog.Builder builder = new AlertDialog.Builder(holder.itemView.getContext());
        builder.setTitle("Confirmación")
                .setMessage("¿Estás seguro de que quieres borrar el elemento " + holder.name.getText() + " ?")
                .setPositiveButton("Borrar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        controller.borrar(cacharro.getId());
                        controller.getCacharros().getAdaptador().setCursor(controller.getCacharros().extraeCursor());
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

    public void editItem(Activity actividad, ViewHolder holder, Cacharro cacharro, int posicion)  {
        controller.mostrar(actividad,cacharro.getId(),posicion);
    }

}

