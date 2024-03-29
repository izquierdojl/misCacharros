package com.jlib.miscacharros.ui.contacto;

import static androidx.core.content.ContextCompat.startActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
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

import org.w3c.dom.Text;

import javax.xml.namespace.QName;

public class AdaptadorContactos extends
        RecyclerView.Adapter<AdaptadorContactos.ViewHolder>
{
    private View.OnClickListener onClickListener;
    public ControladorContacto controller;

    //Creamos nuestro ViewHolder, con los elementos a modificar
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView id;
        private TextView web;
        private ImageView foto;
        protected ImageButton botonBorrar;
        protected ImageButton botonEditar;
        protected ImageButton botonCall;
        protected ImageButton botonLocation;
        public ViewHolder(VistaContactoElementoListaBinding itemView) {
            super(itemView.getRoot());
            name = itemView.name;
            id = itemView.IdContacto;
            web = itemView.web;
            botonBorrar = itemView.botonBorrar;
            botonEditar = itemView.botonEditar;
            botonCall = itemView.botonCall;
            botonLocation = itemView.botonLocation;
        }
        // Personalizamos un ViewHolder a partir de un tipo
        public void personaliza(Contacto contacto) {
            name.setText(contacto.getName());
            web.setText(contacto.getWeb());
            id.setText(String.valueOf(contacto.getId()));
            if( contacto.getTelephone().isEmpty() ) {
                botonCall.setVisibility(View.GONE);
            }else{
                botonCall.setVisibility(View.VISIBLE);
                botonCall.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        doCall(itemView,contacto);
                    }
                });
            }
            if( contacto.getGeopunto().isEmpty() && contacto.getAddress().isEmpty() )
                botonLocation.setVisibility(View.GONE);
            else{
                botonLocation.setVisibility(View.VISIBLE);
                botonLocation.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        doNavigate(itemView,contacto);
                    }
                });
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
        VistaContactoElementoListaBinding v = VistaContactoElementoListaBinding.inflate(LayoutInflater
                .from(parent.getContext()), parent, false);
        v.getRoot().setOnClickListener(onClickListener);
        return new ViewHolder(v);
    }

    // Usando como base el ViewHolder y lo personalizamos
    @Override
    public void onBindViewHolder(ViewHolder holder, int i) {
        if( controller.getContactos().tamano() > 0 ) {
            Contacto contacto = controller.getContactos().elemento(i);
            holder.personaliza(contacto);
            holder.itemView.setTag(i);
        }
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

    public static void doCall(View view, Contacto contacto)
    {
        if( contacto != null && !contacto.getTelephone().isEmpty() ) {
            String phoneNumber = contacto.getTelephone();
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber));
            Context context = view.getContext();
            context.startActivity(intent);
        }
    }

    public static void doNavigate( View view, Contacto contacto )
    {
        if( contacto != null ) {
            Uri uri;
            if( contacto.getGeopunto().isEmpty() )
            {
                if( !contacto.getAddress().isEmpty())
                    uri = Uri.parse("geo:0,0?q=" + contacto.getAddress() );
                else
                    uri = Uri.parse("geo:0,0?q=" + contacto.getName() );
            }else{
                double lat = contacto.getGeopunto().getLatitud();
                double lon = contacto.getGeopunto().getLongitud();
                uri = Uri.parse("geo:" + lat + "," + lon +"?q=" + lat + "," + lon+"(prueba)");
            }
            Intent intent = new Intent("android.intent.action.VIEW", uri);
            Context context = view.getContext();
            context.startActivity(intent);
        }
    }

}

