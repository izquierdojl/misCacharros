package com.jlib.miscacharros.ui.cacharro;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.jlib.miscacharros.Aplicacion;
import com.jlib.miscacharros.R;
import com.jlib.miscacharros.controlador.cacharro.ControladorCacharro;
import com.jlib.miscacharros.controlador.contacto.ControladorContacto;
import com.jlib.miscacharros.controlador.tipo.ControladorTipo;
import com.jlib.miscacharros.databinding.VistaCacharroDetalleBinding;
import com.jlib.miscacharros.modelo.Cacharro;
import com.jlib.miscacharros.modelo.Contacto;
import com.jlib.miscacharros.modelo.Tipo;
import com.jlib.miscacharros.ui.tipo.AdaptadorSpinnerTipos;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class VistaDetalleCacharroActivity extends AppCompatActivity {
    private AdaptadorCacharrosBD adaptador;
    private ControladorCacharro controller;
    private ControladorTipo controllerTipo;
    private ControladorContacto controllerContacto;
    private int pos;
    private int modo; //1 - Añadir  2 - Editar
    private Cacharro cacharro;
    private @NonNull VistaCacharroDetalleBinding binding;


    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        binding = VistaCacharroDetalleBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot()); // obtiene el layout de la actividad
        Bundle extras = getIntent().getExtras();
        int id = extras.getInt("id",0);
        pos = extras.getInt("pos",0);
        modo = extras.getInt("modo",0);
        controller = ((Aplicacion) getApplication()).getControllerCacharro();
        controllerTipo = ((Aplicacion) getApplication()).getControllerTipo();
        controllerContacto = ((Aplicacion) getApplication()).getControllerContacto();
        adaptador = controller.getCacharros().getAdaptador();
        if( modo == 2 )
            cacharro = controller.getCacharros().elemento(id);
        else
            cacharro = new Cacharro();
        Toolbar toolbar = binding.toolbarDetalleCacharro;
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        binding.actionSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cacharro.setName(binding.editTextName.getText().toString());
                if( modo == 1 ) {
                    controller.anade(cacharro);
                    adaptador.setCursor(controller.getCacharros().extraeCursor());
                    adaptador.notifyDataSetChanged();
                } else if (modo==2) {
                    controller.actualiza(cacharro.getId(),cacharro);
                    adaptador.setCursor(controller.getCacharros().extraeCursor());
                    adaptador.notifyItemChanged(pos);
                   // setResult(RESULT_OK);
                }
                finish();
            }
        });

        AdaptadorSpinnerTipos adaptadorSpinnerTipos = new AdaptadorSpinnerTipos(this, controllerTipo.getTipos().getLista() );
        binding.spinnerTipo.setAdapter(adaptadorSpinnerTipos);
        binding.spinnerTipo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Tipo tipoSeleccionado = (Tipo) parent.getItemAtPosition(position);
                int idTipoSeleccionado = tipoSeleccionado.getId();
                cacharro.setIdTipo(idTipoSeleccionado);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        binding.editTextContacto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Initialize dialog
                Dialog dialog=new Dialog(VistaDetalleCacharroActivity.this);

                // set custom dialog
                dialog.setContentView(R.layout.dialog_searchable_spinner);

                // set custom height and width
                dialog.getWindow().setLayout(650,800);

                // set transparent background
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                // show dialog
                dialog.show();

                // Initialize and assign variable
                EditText editText=dialog.findViewById(R.id.edit_text);
                ListView listView=dialog.findViewById(R.id.list_view);
                listView.setBackgroundColor(getColor(R.color.list_back_color));

                ArrayList<String> mostrarBusqueda = new ArrayList<String>();
                for( Contacto contacto : controllerContacto.getContactos().getLista())
                    mostrarBusqueda.add( contacto.getName() );
                // Initialize array adapter

                ArrayAdapter<String> adapter;
                adapter = new ArrayAdapter<>(VistaDetalleCacharroActivity.this, android.R.layout.simple_list_item_1 , mostrarBusqueda );

                listView.setAdapter(adapter);
                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        adapter.getFilter().filter(s);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        binding.editTextContacto.setText(adapter.getItem(position));
                        String nombre = adapter.getItem(position);
                        for( Contacto contacto : controllerContacto.getContactos().getLista() ) {
                            if (contacto.getName().equals(nombre) ){
                                cacharro.setIdContacto(contacto.getId());
                                break;
                            }
                        }
                        dialog.dismiss();
                    }
                });
            }
        });

        /*
        AdaptadorSpinnerContactos adaptadorSpinnerContactos = new AdaptadorSpinnerContactos(this, controllerContacto.getContactos().getLista() );
        binding.spinnerContacto.setAdapter(adaptadorSpinnerContactos);
        binding.spinnerContacto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Contacto contactoSeleccionado = (Contacto) parent.getItemAtPosition(position);
                int idContactoSeleccionado = contactoSeleccionado.getId();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        */

        binding.buttonAdjuntarImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkCameraPermission();
            }
        });

        actualizaVistas(cacharro,modo);

    }

    /**
     * Actualiza la pantalla del formulario del contacto
     * @param cacharro Objeto contacto
     * @param modo 1 - Añadir    2 - Editar
     */
    public void actualizaVistas(Cacharro cacharro, int modo)
    {
        if( modo == 2 ) { // EDITAR
            binding.editTextName.setText(cacharro.getName());
            binding.editTextFabricante.setText(cacharro.getFabricante());
            int index = 0;
            for( Tipo tipo : controllerTipo.getTipos().getLista() ){
                if( tipo.getId() == cacharro.getIdTipo() ){
                    binding.spinnerTipo.setSelection(index);
                    break;
                }
                index++;
            }
            for( Contacto contacto : controllerContacto.getContactos().getLista() ) {
                if (contacto.getId() == cacharro.getIdContacto() ){
                    binding.editTextContacto.setText(contacto.getName());
                    break;
                }
            }
            /*
            for (int i = 0; i < binding.spinnerTipo.getAdapter().getCount(); i++) {
                Tipo tipo = (Tipo) binding.spinnerTipo.getAdapter().getItem(i);
                if( cacharro.getIdTipo() == tipo.getId()) {
                    binding.spinnerTipo.setSelection(i);
                }
            }

            Contacto contacto;
            contacto = controllerContacto.getContactos().elemento(cacharro.getIdTipo());
            if ( contacto != null )
              binding.editTextContacto.setText( contacto.getName() );
            */
        } else if ( modo == 1 ) { // AÑADIR --> DE MOMENTO NO HACEMOS NADA

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar_detalle_cacharro, menu);
        binding.toolbarDetalleCacharro.setTitle("Detalle Cacharro");
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

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_PERMISSION_CAMERA = 2;
    private static final int pic_id = 123;
    private String currentPhotoPath;

    // Método para verificar y solicitar permisos de cámara en tiempo de ejecución
    private void checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted, request the permission
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.CAMERA},
                    REQUEST_PERMISSION_CAMERA);
        } else {
            // Permission has already been granted
            dispatchTakePictureIntent();
        }
    }

    // Método para crear un archivo de imagen
    private void createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        currentPhotoPath = image.getAbsolutePath();
    }

    // Método para iniciar la actividad de captura de imágenes
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(camera_intent, pic_id);

        /*
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            try {
                createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                ex.printStackTrace();
            }
            // Continue only if the File was successfully created
            if (currentPhotoPath != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        new File(currentPhotoPath));
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
        */

    }

    // Manejar el resultado de la solicitud de permisos
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION_CAMERA) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, dispatch the take picture intent
                dispatchTakePictureIntent();
            } else {
                // Permission denied
                // You can show a message or take alternative action here
            }
        }
    }

    // Manejar el resultado de la actividad de captura de imágenes
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == pic_id ) {
            // Image captured and saved to fileUri specified in the Intent
            // Now you can do something with the saved image, such as displaying it in an ImageView
            // For example, you can set the captured image to an ImageView
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            binding.imageView.setImageBitmap(photo);
            cacharro.setImagen(currentPhotoPath.getBytes());
        }
    }

}
