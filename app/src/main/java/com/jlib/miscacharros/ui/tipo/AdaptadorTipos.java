package com.jlib.miscacharros.ui.tipo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.jlib.miscacharros.R;
import com.jlib.miscacharros.datos.tipo.RepositorioTipos;
import com.jlib.miscacharros.modelo.Tipo;
import com.jlib.miscacharros.databinding.VistaTipoElementoListaBinding;

public class AdaptadorTipos extends
        RecyclerView.Adapter<AdaptadorTipos.ViewHolder> {
    protected RepositorioTipos tipos;         // Lista de lugares a mostrar
    private View.OnClickListener onClickListener;

    public AdaptadorTipos(RepositorioTipos tipos)  {
        this.tipos = tipos;
    }

    //Creamos nuestro ViewHolder, con los tipos de elementos a modificar
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nombre;
        public ImageView foto;
        public ImageButton botonBorrar;
        public RatingBar prioridad;
        public ViewHolder(VistaTipoElementoListaBinding itemView) {
            super(itemView.getRoot());
            nombre = itemView.nombre;
            foto = itemView.foto;
            prioridad = itemView.prioridad;
            botonBorrar = itemView.botonBorrar;
        }
        // Personalizamos un ViewHolder a partir de un tipo
        public void personaliza(Tipo tipo) {
            nombre.setText(tipo.getNombre());
            prioridad.setRating(tipo.getPrioridad());
            int id = R.drawable.miscaharros;
            foto.setScaleType(ImageView.ScaleType.FIT_END);
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
    public void onBindViewHolder(ViewHolder holder, int posicion) {
        Tipo tipo = tipos.tipo(posicion);
        holder.personaliza(tipo);

        holder.prioridad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                asignaPrioridad(holder,posicion);
            }
        });

        holder.botonBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteItem(holder,posicion);
            }
        });
    }
    // Indicamos el número de elementos de la lista
    @Override public int getItemCount() {
        return tipos.tamano();
    }

    public void deleteItem(ViewHolder holder, int posicion)  {
        AlertDialog.Builder builder = new AlertDialog.Builder(holder.itemView.getContext());
        builder.setTitle("Confirmación")
                .setMessage("¿Estás seguro de que quieres borrar el elemento " + holder.nombre.getText() + " ?")
                .setPositiveButton("Borrar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tipos.borrar(posicion);
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

    public void asignaPrioridad(ViewHolder holder, int posicion){
        float rating = holder.prioridad.getRating();
        Tipo tipo = tipos.tipo(posicion);
        tipo.setPrioridad((int) rating);
        tipos.sortPrioridad();
        notifyDataSetChanged();
    }
}

