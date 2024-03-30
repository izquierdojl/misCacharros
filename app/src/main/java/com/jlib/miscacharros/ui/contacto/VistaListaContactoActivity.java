package com.jlib.miscacharros.ui.contacto;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jlib.miscacharros.Aplicacion;
import com.jlib.miscacharros.R;
import com.jlib.miscacharros.controlador.contacto.ControladorContacto;
import com.jlib.miscacharros.databinding.VistaContactoDetalleBinding;
import com.jlib.miscacharros.databinding.VistaContactoListaBinding;
import com.jlib.miscacharros.modelo.Contacto;

import java.util.Locale;

public class VistaListaContactoActivity extends AppCompatActivity{

    public AdaptadorContactosBD adaptador;
    private VistaContactoListaBinding binding;
    private Toolbar barra;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        adaptador = ((Aplicacion) getApplication()).getControllerContacto().getContactos().getAdaptador();
        binding = VistaContactoListaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot()); // obtiene el layout de la actividad

        barra = binding.toolbar;
        setSupportActionBar(barra);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RecyclerView recyclerView = binding.recyclerView;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adaptador);

        EditText editTextSearch = binding.editTextSearch;
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String searchTerm = s.toString().toLowerCase(Locale.getDefault());
                Cursor newCursor = adaptador.controller.getContactos().extraeCursorFiltrado(searchTerm);
                adaptador.setCursor(newCursor);
                recyclerView.getAdapter().notifyDataSetChanged();
            }
            @Override
            public void afterTextChanged(Editable s) {}

        });

        adaptador.setOnItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos= binding.recyclerView.getChildAdapterPosition(v); // es la posición física
                TextView textView_contactId = v.findViewById(R.id.Id_Contacto);
                int id = Integer.parseInt(textView_contactId.getText().toString());
                adaptador.controller.mostrar(id,pos);
            }
        });

        if( adaptador.getItemCount() == 0 )
            binding.editTextSearch.setVisibility(View.GONE);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar_contacto, menu);
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
        adaptador.notifyDataSetChanged();
        if( adaptador.getItemCount() == 0 )
            binding.editTextSearch.setVisibility(View.GONE);
        else
            binding.editTextSearch.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if( id == R.id.action_nuevo_contacto ) {
            adaptador.controller.nuevo();
            //showEditDialog(binding.recyclerView, adaptador, 1, 0);
            return true;
        }
        else if( id == android.R.id.home ) {
            finish();
            return true;
        }
        else
        {
            return super.onOptionsItemSelected(item);
        }
    }

    private void showEditDialog(RecyclerView recyclerView, AdaptadorContactosBD adaptador, int modo, int pos) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.vista_contacto_detalle, null);
        VistaContactoDetalleBinding binding = VistaContactoDetalleBinding.bind(dialogView);
        Contacto contacto = (modo == 1) ? new Contacto() : adaptador.contactoPosicion(pos);
        builder.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                contacto.setName(binding.editTextName.getText().toString());
                contacto.setAddress(binding.editTextAddress.getText().toString());
                contacto.setEmail(binding.editTextEmail.getText().toString());
                contacto.setTelephone(binding.editTextTel.getText().toString());
                contacto.setWeb(binding.editTextWeb.getText().toString());
                if (modo == 1) {
                    adaptador.controller.anade(contacto);
                    adaptador.setCursor(adaptador.controller.contactos().extraeCursor());
                    recyclerView.getAdapter().notifyDataSetChanged();
                    //recyclerView.getAdapter().notifyItemInserted(adaptador.getItemCount() - 1);
                }
            }
        });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.setView(dialogView);
        builder.create().show();

    }


}
