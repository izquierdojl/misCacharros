package com.jlib.miscacharros.ui.contacto;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.jlib.miscacharros.Aplicacion;
import com.jlib.miscacharros.R;
import com.jlib.miscacharros.controlador.contacto.ControladorContacto;
import com.jlib.miscacharros.databinding.VistaContactoDetalleBinding;
import com.jlib.miscacharros.datos.contacto.ContactosBDAdapter;
import com.jlib.miscacharros.datos.util.Util;
import com.jlib.miscacharros.modelo.Contacto;
import com.jlib.miscacharros.modelo.GeoPunto;

public class VistaDetalleContactoActivity extends AppCompatActivity {
    private AdaptadorContactosBD adaptador;
    private ControladorContacto controller;
    private int pos;
    private int modo; //1 - Añadir  2 - Editar
    private Contacto contacto;
    private @NonNull VistaContactoDetalleBinding binding;


    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        binding = VistaContactoDetalleBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot()); // obtiene el layout de la actividad
        Bundle extras = getIntent().getExtras();
        int id = extras.getInt("id",0);
        pos = extras.getInt("pos",0);
        modo = extras.getInt("modo",0);
        controller = ((Aplicacion) getApplication()).getControllerContacto();
        adaptador = controller.getContactos().getAdaptador();
        if( modo == 2 )
            contacto = controller.contactos().elemento(id);
        else
            contacto = new Contacto();
        Toolbar toolbar = binding.toolbarDetalle;
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        actualizaVistas(contacto,modo);

        // validacion email
        binding.editTextEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) { // Verifica si el EditText ha perdido el foco
                    String email = binding.editTextEmail.getText().toString().trim(); // Obtén el texto del EditText y elimina espacios en blanco
                    if (!email.isEmpty()) {
                        if (!(Util.validEmail(email))) {
                            binding.editTextEmail.setError("Dirección de correo electrónico no válida");
                        }
                    }
                }
            }
        });

        binding.editTextWeb.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) { // Verifica si el EditText ha perdido el foco
                    String web = binding.editTextWeb.getText().toString().trim(); // Obtén el texto del EditText y elimina espacios en blanco
                    if (!web.isEmpty()) {
                        if (!(Util.validEmail(web))) {
                            binding.editTextEmail.setError("Dirección de correo electrónico no válida");
                        }
                    }
                }
            }
        });

        binding.actionSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contacto.setName(binding.editTextName.getText().toString());
                contacto.setAddress(binding.editTextAddress.getText().toString());
                contacto.setEmail(binding.editTextEmail.getText().toString());
                contacto.setTelephone(binding.editTextTel.getText().toString());
                contacto.setWeb(binding.editTextWeb.getText().toString());
                String[] ubicacion = binding.editTextUbicacion.getText().toString().split(",");
                if( ubicacion.length==2 )
                {
                    double lat = Double.parseDouble(ubicacion[0].trim()); // trim() elimina espacios en blanco alrededor
                    double lon = Double.parseDouble(ubicacion[1].trim());
                    contacto.setGeopunto(new GeoPunto(lat, lon));
                }
                if( modo == 1 ) {
                    controller.anade(contacto);
                    adaptador.setCursor(controller.contactos().extraeCursor());
                    adaptador.notifyDataSetChanged();
                } else if (modo==2) {
                    controller.actualiza(contacto.getId(),contacto);
                    adaptador.setCursor(controller.contactos().extraeCursor());
                    adaptador.notifyItemChanged(pos);
                   // setResult(RESULT_OK);
                }
                finish();
            }
        });

    }

    /**
     * Actualiza la pantalla del formulario del contacto
     * @param contacto Objeto contacto
     * @param modo 1 - Añadir    2 - Editar
     */
    public void actualizaVistas(Contacto contacto, int modo)
    {
        if( modo == 2 ) {
            binding.editTextName.setText(contacto.getName());
            binding.editTextAddress.setText(contacto.getAddress());
            binding.editTextTel.setText(contacto.getTelephone());
            binding.editTextEmail.setText(contacto.getEmail());
            binding.editTextWeb.setText(contacto.getWeb());
            binding.editTextUbicacion.setText(contacto.getGeopunto().toText());
        }

        binding.editTextUbicacion.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                // Al detectar un long click, mostramos el menú de contexto
                v.showContextMenu();
                return true; // Indicamos que hemos gestionado el evento
            }
        });

        binding.editTextUbicacion.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                // Creamos un menú de contexto con una opción para pegar
                menu.add(Menu.NONE, R.id.menu_ubicacion_pegar, Menu.NONE, "Pegar");
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar_detalle_contacto, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        //switch (item.getItemId()) {
        if( item.getItemId()==R.id.menu_ubicacion_pegar) {
            // Pegar el texto del portapapeles en el EditText
            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            if (clipboard != null && clipboard.hasPrimaryClip()) {
                ClipData clip = clipboard.getPrimaryClip();
                if (clip != null && clip.getItemCount() > 0) {
                    CharSequence text = clip.getItemAt(0).coerceToText(getApplicationContext());
                    binding.editTextUbicacion.append(text);
                }
            }
            return true;
        }else
        {
            return super.onContextItemSelected(item);
        }
    }

}
