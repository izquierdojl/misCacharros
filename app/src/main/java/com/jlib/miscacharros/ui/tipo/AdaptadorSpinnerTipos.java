package com.jlib.miscacharros.ui.tipo;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.jlib.miscacharros.modelo.Tipo;

import java.util.List;

public class AdaptadorSpinnerTipos extends ArrayAdapter<Tipo> {

    public AdaptadorSpinnerTipos(@NonNull Context context, @NonNull List<Tipo> tipos) {
        super(context, android.R.layout.simple_spinner_item, tipos);
        setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getId();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TextView textView = (TextView) super.getView(position, convertView, parent);
        Tipo tipo = getItem(position);
        textView.setText(tipo.getNombre());
        return textView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView textView = (TextView) super.getDropDownView(position, convertView, parent);
        Tipo tipo = getItem(position);
        textView.setText(tipo.getNombre());
        return textView;
    }
}
