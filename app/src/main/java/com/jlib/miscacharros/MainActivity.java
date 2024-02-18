package com.jlib.miscacharros;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.tabs.TabLayout;
import com.jlib.miscacharros.controlador.ControladorTipo;
import com.jlib.miscacharros.ui.acercadeActivity;
import com.jlib.miscacharros.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabs;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_main);

        tabs = findViewById(R.id.tabs);

        // creamos las tabs
        tabs.addTab(tabs.newTab().setText("Cacharros"));
        tabs.addTab(tabs.newTab().setText("Categorías"));
        tabs.addTab(tabs.newTab().setText("Acerca de ..."));

        //tipos = ((Aplicacion) getApplication().tipos);
        //usoLugar = new ControladorTipo(this, tipos);

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition())
                {
                    case 0:
                        break;
                    case 1:
                        break;
                    case 2: // acerca de...
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

        });


    }



}