package com.jlib.miscacharros;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.google.android.material.tabs.TabLayout;
import com.jlib.miscacharros.controlador.tipo.ControladorTipo;
import com.jlib.miscacharros.ui.acercade.acercadeActivity;
import com.jlib.miscacharros.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabs;
    private ActivityMainBinding binding;
    private ControladorTipo controladorTipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_main);

        tabs = findViewById(R.id.tabs);

        // creamos las tabs
        tabs.addTab(tabs.newTab().setText("Cacharros"));
        tabs.addTab(tabs.newTab().setText("Categorías"));
        tabs.addTab(tabs.newTab().setText("Lugares"));
        tabs.addTab(tabs.newTab().setText("Acerca de ..."));

        controladorTipo = ((Aplicacion) getApplication()).getControllerTipo();
        controladorTipo.setActividad(this);


        //tipos = ((Aplicacion) getApplication()).tipos ;
        //if( tipos.tamano() == 0 )
        //   tipos.anadeEjemplos();
        //controllerTipo.setTipos(tipos);



        /// aquí dibujaremos en un futuro la pantalla principal


        /// acciones al pulsar sobre el tab
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition())
                {
                    case 1:
                        lanzarVistaTipo(null);
                        break;
                    case 3: // acerca de...
                        lanzarAcercaDe(null);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) { // se ejecuta al salir del tab
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) { //al volver al tab
                onTabSelected(tab);
            }

            public void lanzarAcercaDe(View view) {
                Intent i = new Intent(MainActivity.this, acercadeActivity.class);
                startActivity(i);
            }

            public void lanzarVistaTipo(View view)
            {
                //contTipo.mostrar(0); // con esto se mostraría directamente

                controladorTipo.listar();

                // muy interesante, cuadro de selección para pedir un dato
                /*
                final EditText entrada = new EditText(MainActivity.this);
                entrada.setText("0");
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Selección de tipo")
                        .setMessage("indica su id:")
                        .setView(entrada)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                int id = Integer.parseInt (entrada.getText().toString());
                                contTipo.mostrar(id);
                            }})
                        .setNegativeButton("Cancelar", null)
                        .show();
                 */
            }

        });

    }



}