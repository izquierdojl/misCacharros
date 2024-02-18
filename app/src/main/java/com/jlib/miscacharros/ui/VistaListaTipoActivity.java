package com.jlib.miscacharros.ui;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jlib.miscacharros.Aplicacion;
import com.jlib.miscacharros.controlador.ControladorTipo;
import com.jlib.miscacharros.databinding.VistaTipoListaBinding;

public class VistaListaTipoActivity extends AppCompatActivity{

    public AdaptadorTipos adaptador;
    private VistaTipoListaBinding binding;
    private ControladorTipo contTipo;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        adaptador = ((Aplicacion) getApplication()).adaptador;
        contTipo = new ControladorTipo(this, adaptador.tipos);

        binding = VistaTipoListaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot()); // obtiene el layout de la actividad

        RecyclerView recyclerView = binding.recyclerView;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adaptador);

        adaptador.setOnItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos= binding.recyclerView.getChildAdapterPosition(v);
                contTipo.mostrar(pos);
            }
        });


    }

}
