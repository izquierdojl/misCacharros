package com.jlib.miscacharros.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.jlib.miscacharros.R;
import com.jlib.miscacharros.datos.RepositorioTipos;
import com.jlib.miscacharros.modelo.Tipo;
import com.jlib.miscacharros.databinding.VistaTipoElementoListaBinding;

public class AdaptadorTipos extends
        RecyclerView.Adapter<AdaptadorTipos.ViewHolder> {
    protected RepositorioTipos tipos;         // Lista de lugares a mostrar
    private View.OnClickListener onClickListener;

    public AdaptadorTipos(RepositorioTipos tipos) {
        this.tipos = tipos;
    }

    //Creamos nuestro ViewHolder, con los tipos de elementos a modificar
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nombre;
        public ImageView foto;
        public ViewHolder(VistaTipoElementoListaBinding itemView) {
            super(itemView.getRoot());
            nombre = itemView.nombre;
            foto = itemView.foto;
        }
        // Personalizamos un ViewHolder a partir de un tipo
        public void personaliza(Tipo tipo) {
            nombre.setText(tipo.getNombre());
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
    }
    // Indicamos el número de elementos de la lista
    @Override public int getItemCount() {
        return tipos.tamano();
    }
}

