package com.jlib.miscacharros.ui.contacto;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.jlib.miscacharros.modelo.Contacto;
import java.util.List;

public class AdaptadorSpinnerContactos extends ArrayAdapter<Contacto> {

    public AdaptadorSpinnerContactos(@NonNull Context context, @NonNull List<Contacto> contactos) {
        super(context, android.R.layout.simple_spinner_item, contactos);
        setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getId();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return super.getView(position, convertView, parent);
    }
}
