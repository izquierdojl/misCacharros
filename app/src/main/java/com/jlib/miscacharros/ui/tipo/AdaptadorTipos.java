package com.jlib.miscacharros.ui.tipo;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.jlib.miscacharros.R;
import com.jlib.miscacharros.controlador.tipo.ControladorTipo;
import com.jlib.miscacharros.modelo.Tipo;
import com.jlib.miscacharros.databinding.VistaTipoElementoListaBinding;

import yuku.ambilwarna.AmbilWarnaDialog;

public class AdaptadorTipos extends
        RecyclerView.Adapter<AdaptadorTipos.ViewHolder> {
    //protected TiposBDAdapter tipos;
    private View.OnClickListener onClickListener;
    public ControladorTipo controller;

    //Creamos nuestro ViewHolder, con los tipos de elementos a modificar
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nombre;
        public ImageView foto;
        public ImageButton botonBorrar;
        public ImageButton botonColor;
        public RatingBar prioridad;
        public ViewHolder(VistaTipoElementoListaBinding itemView) {
            super(itemView.getRoot());
            nombre = itemView.nombre;
            foto = itemView.foto;
            prioridad = itemView.prioridad;
            botonBorrar = itemView.botonBorrar;
            botonColor = itemView.botonColor;
        }
        // Personalizamos un ViewHolder a partir de un tipo
        public void personaliza(Tipo tipo,ViewHolder holder) {
            nombre.setText(tipo.getNombre());
            prioridad.setRating(tipo.getPrioridad());
            int id = R.drawable.miscaharros;
            foto.setScaleType(ImageView.ScaleType.FIT_END);
            if( tipo.getColor()!= 0 )
                holder.itemView.setBackgroundColor(tipo.getColor());

            switch (tipo.getId())
            {
                case 1:
                    foto.setImageResource(R.drawable.tipo_cocina);
                    break;
                case 2:
                    foto.setImageResource(R.drawable.tipo_entretenimiento);
                    break;
                case 3:
                    foto.setImageResource(R.drawable.tipo_moviles);
                    break;
                case 4:
                    foto.setImageResource(R.drawable.tipo_hogar);
                    break;
            }


        }
    }

    public void setOnItemClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    // Creamos el ViewHolder con la vista de un elemento sin personalizar
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflamos la vista desde el xml
        VistaTipoElementoListaBinding v = VistaTipoElementoListaBinding.inflate(LayoutInflater
                .from(parent.getContext()), parent, false);
        v.getRoot().setOnClickListener(onClickListener);
        return new AdaptadorTipos.ViewHolder(v);
    }

    // Usando como base el ViewHolder y lo personalizamos
    @Override
    public void onBindViewHolder(ViewHolder holder, int i) {
        Tipo tipo = controller.getTipos().elemento(i);
        holder.personaliza(tipo,holder);
        holder.itemView.setTag(i);
    }
    // Indicamos el número de elementos de la lista

    @Override public int getItemCount() {
        return this.getItemCount();
    }

    public void deleteItem(ViewHolder holder, Tipo tipo, int posicion)  {
        AlertDialog.Builder builder = new AlertDialog.Builder(holder.itemView.getContext());
        builder.setTitle("Confirmación")
                .setMessage("¿Estás seguro de que quieres borrar el elemento " + holder.nombre.getText() + " ?")
                .setPositiveButton("Borrar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        controller.borrar(tipo.getId());
                        controller.getTipos().getAdaptador().setCursor(controller.getTipos().extraeCursor());
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

    public void selectorColor(ViewHolder holder, Tipo tipo, int posicion){
        // the AmbilWarnaDialog callback needs 3 parameters
        // one is the context, second is default color,
        final AmbilWarnaDialog colorPickerDialogue = new AmbilWarnaDialog(holder.itemView.getContext(), 0,
                new AmbilWarnaDialog.OnAmbilWarnaListener() {
                    @Override
                    public void onCancel(AmbilWarnaDialog dialog) {
                        // leave this function body as
                        // blank, as the dialog
                        // automatically closes when
                        // clicked on cancel button
                    }

                    @Override
                    public void onOk(AmbilWarnaDialog dialog, int color) {
                        // change the mDefaultColor to
                        // change the GFG text color as
                        // it is returned when the OK
                        // button is clicked from the
                        // color picker dialog
                        //mDefaultColor = color;
                        // now change the picked color
                        // preview box to mDefaultColor
                        //mColorPreview.setBackgroundColor(mDefaultColor);
                        tipo.setColor(color);
                        controller.actualiza(tipo.getId(), tipo);
                        controller.getTipos().getAdaptador().setCursor(controller.getTipos().extraeCursor());
                        notifyItemChanged(posicion);
                    }
                });
        colorPickerDialogue.show();
    }

}

