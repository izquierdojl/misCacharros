package com.jlib.miscacharros.ui.cacharro;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

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
                cacharro.setFabricante(binding.editTextFabricante.getText().toString());
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
                editText.setBackgroundColor(getColor(R.color.list_back_color));
                editText.setTextColor(getColor(R.color.list_text_color));
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


        binding.buttonAdjuntarImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( cacharro.getImagen() != null && cacharro.getImagen().length > 0 ){
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setTitle("Confirmación")
                            .setMessage("¿ Ya existe una imagen registrada, seguro de continuar ? La imagen se reemplazará")
                            .setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss(); // Cierra el cuadro de diálogo
                                    checkCameraPermission();
                                }
                            })
                            .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss(); // Cierra el cuadro de diálogo
                                }
                            })
                            .show();

                }else {
                    checkCameraPermission();
                }
            }
        });

        binding.buttonAdjuntarImagenGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( cacharro.getImagen() != null && cacharro.getImagen().length > 0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setTitle("Confirmación")
                            .setMessage("¿ Ya existe una imagen registrada, seguro de continuar ? La imagen se reemplazará")
                            .setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss(); // Cierra el cuadro de diálogo
                                    checkGalleryPermission();
                                }
                            })
                            .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss(); // Cierra el cuadro de diálogo
                                }
                            })
                            .show();

                } else {
                    checkGalleryPermission();
                }
            }
        });

        binding.buttonAdjuntarArchivo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cacharro.getNomarchivo()!=null && !cacharro.getNomarchivo().isEmpty()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setTitle("Confirmación")
                            .setMessage("¿ Ya existe una archivo adjuntado, seguro de continuar ? El archivo se reemplazará")
                            .setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss(); // Cierra el cuadro de diálogo
                                    checkFilePermission();
                                    //
                                }
                            })
                            .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss(); // Cierra el cuadro de diálogo
                                }
                            })
                            .show();

                } else {
                    checkFilePermission();
                }
            }
        });

        binding.buttonAbrirArchivo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirArchivo();
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
            if( cacharro.getImagen()!=null && cacharro.getImagen().length > 0 )
            {
                byte[] photoBytes = cacharro.getImagen(); //
                Bitmap photoBmp = BitmapFactory.decodeByteArray(photoBytes,0,photoBytes.length);
                binding.imageView.setImageBitmap(photoBmp);
                binding.imageView.setVisibility(View.VISIBLE);
            }
            if(cacharro.getNomarchivo()!=null && !cacharro.getNomarchivo().isEmpty()){
                binding.buttonAbrirArchivo.setVisibility(View.VISIBLE);
            }else{
                binding.buttonAbrirArchivo.setVisibility(View.GONE);
            }
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

    private static final int REQUEST_PERMISSION_CAMERA = 2;
    private static final int pic_id = 123;
    private static final int REQUEST_CODE_PERMISSION = 100;
    private static final int PICK_FILE_REQUEST = 1;
    private static final int PICK_IMAGE_REQUEST = 2;

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

    // Método para iniciar la actividad de captura de imágenes
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(camera_intent, pic_id);
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

    private void checkGalleryPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted, request the permission
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_CODE_PERMISSION);
        } else {
            // Permission has already been granted
            openGallery();
        }
    }


    private void openGallery() {
        // Intent para abrir la galería
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST);
    }


    private void checkFilePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted, request the permission
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_CODE_PERMISSION);
        } else {
            // Permission has already been granted
            openFile();
        }
    }

    private void openFile()
    {
        Intent fileIntent = new Intent(Intent.ACTION_GET_CONTENT);
        fileIntent.setType("*/*"); // Selecciona cualquier tipo de archivo
        fileIntent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(Intent.createChooser(fileIntent, "Seleccionar archivo"), PICK_FILE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == pic_id && resultCode == RESULT_OK && data != null && data.getData() != null) {
            // Image captured and saved to fileUri specified in the Intent
            // Now you can do something with the saved image, such as displaying it in an ImageView
            // For example, you can set the captured image to an ImageView
            try {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                binding.imageView.setImageBitmap(photo);
                binding.imageView.setVisibility(View.VISIBLE);
                ByteArrayOutputStream photoStream = new ByteArrayOutputStream();
                photo.compress(Bitmap.CompressFormat.JPEG, 100, photoStream);
                cacharro.setImagen(photoStream.toByteArray());
            }catch (Exception e ) {
                throw new RuntimeException(e);
            }

        }
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri selectedImageUri = data.getData();
            binding.imageView.setImageURI(selectedImageUri);
            binding.imageView.setVisibility(View.VISIBLE);
            try {
                Bitmap photo = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);
                ByteArrayOutputStream photoStream = new ByteArrayOutputStream();
                photo.compress(Bitmap.CompressFormat.JPEG, 100, photoStream);
                cacharro.setImagen(photoStream.toByteArray());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        if (requestCode == PICK_FILE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri selectedFileUri = data.getData();
            InputStream inputStream = null;
            ByteArrayOutputStream byteArrayOutputStream = null;
            try {
                inputStream = getContentResolver().openInputStream(selectedFileUri);
                byteArrayOutputStream = new ByteArrayOutputStream();
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    byteArrayOutputStream.write(buffer, 0, bytesRead);
                }
                byte[] fileBytes = byteArrayOutputStream.toByteArray();
                cacharro.setArchivo(fileBytes);
                String fileName = getFileName(selectedFileUri);
                cacharro.setNomarchivo(fileName);
                binding.buttonAbrirArchivo.setVisibility(View.VISIBLE);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                // Asegúrate de cerrar los InputStream y ByteArrayOutputStream
                try {
                    if (inputStream != null) {
                        inputStream.close();
                    }
                    if (byteArrayOutputStream != null) {
                        byteArrayOutputStream.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private String getFileName(Uri uri) {
        String fileName = null;
        Cursor cursor = null;
        try {
            cursor = getContentResolver().query(uri, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                fileName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return fileName;
    }

    protected void abrirArchivo() {

        byte[] fileBytes = cacharro.getArchivo();

        File tempFile = new File(cacharro.getNomarchivo());
        if (tempFile.exists())
            tempFile.delete();
        String mimeType = getMimeType(tempFile.getPath());

        // Crear un intent para abrir el archivo con la aplicación predeterminada
        Intent viewIntent = new Intent(Intent.ACTION_VIEW);
        viewIntent.setDataAndType(Uri.fromFile(tempFile), mimeType);
        viewIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Comprobar si hay aplicaciones disponibles para manejar el archivo
        PackageManager packageManager = getPackageManager();
        List<ResolveInfo> activities;
        activities = packageManager.queryIntentActivities(viewIntent, 0);
        boolean isIntentSafe = activities.size() > 0;
        if (isIntentSafe) {
            // Si hay aplicaciones disponibles para manejar el archivo, iniciar la actividad
            startActivity(viewIntent);
        } else {
            Toast.makeText(this, "No hay aplicaciones disponibles para abrir el archivo.", Toast.LENGTH_SHORT).show();
        }

    }

    private File createTempFile(byte[] fileBytes) {
        File tempFile = null;
        FileOutputStream fos = null;
        try {
            tempFile = File.createTempFile("temp_file", ".pdf", getCacheDir());
            fos = new FileOutputStream(tempFile);
            fos.write(fileBytes);
            fos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return tempFile;
    }


    // Método para obtener el tipo MIME del archivo
    private String getMimeType(String path) {
        String extension = MimeTypeMap.getFileExtensionFromUrl(path);
        return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension.toLowerCase());
    }




}