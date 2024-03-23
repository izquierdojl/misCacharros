package com.jlib.miscacharros.ui.tipo;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.jlib.miscacharros.R;
import com.jlib.miscacharros.controlador.tipo.ControladorTipo;
import com.jlib.miscacharros.datos.tipo.TiposBDAdapter;
import com.jlib.miscacharros.modelo.Tipo;
import com.jlib.miscacharros.databinding.VistaTipoDetalleBinding;

public class VistaDetalleTipoActivity extends AppCompatActivity {
    private TiposBDAdapter tipos;
    private ControladorTipo contTipo;
    private int pos;
    private Tipo tipo;
    private VistaTipoDetalleBinding binding;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        binding = VistaTipoDetalleBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot()); // obtiene el layout de la actividad
        Bundle extras = getIntent().getExtras();
        pos = extras.getInt("pos",0);
        //contTipo = new ControladorTipo(this, tipos); // creamos el objeto controlador
        //tipos = ((Aplicacion) getApplication()).tipos;
        //tipo = tipos.tipo(pos);
        tipo = tipos.elementoPos(pos);

        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        actualizaVistas();
    }

    public void actualizaVistas()
    {
        //tipo = tipos.tipo(pos);
        tipo = tipos.elementoPos(pos);
        binding.nombre.setText(tipo.getNombre()+" ("+tipo.getId()+")");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar_tipo, menu);
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

}
