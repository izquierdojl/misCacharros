package com.jlib.miscacharros.ui.cacharro;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.jlib.miscacharros.Aplicacion;
import com.jlib.miscacharros.R;
import com.jlib.miscacharros.controlador.tipo.ControladorTipo;
import com.jlib.miscacharros.databinding.VistaCacharroListaBinding;
import com.jlib.miscacharros.modelo.Tipo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class VistaListaCacharroFragment extends Fragment {

    private AdaptadorCacharrosBD adaptador;
    private @NonNull VistaCacharroListaBinding binding;

    private boolean[] filtroTipos;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = VistaCacharroListaBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adaptador = ((Aplicacion) requireActivity().getApplication()).getControllerCacharro().getCacharros().getAdaptador();
        adaptador.activity = getActivity();
        adaptador.controllerTipo = ((Aplicacion) requireActivity().getApplication()).getControllerTipo();
        filtroTipos = new boolean[adaptador.controllerTipo.getTipos().tamano()];
        Arrays.fill(filtroTipos,true);

        RecyclerView recyclerView = binding.recyclerView;
        recyclerView.setHasFixedSize(true);
        //recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setLayoutManager( new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
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
                int pos= recyclerView.getChildAdapterPosition(v); // es la posición física
                TextView textView_contactId = v.findViewById(R.id.Id_Cacharro);
                int id = Integer.parseInt(textView_contactId.getText().toString());
                if (getActivity() != null) {
                    adaptador.controller.mostrar(getActivity(), id, pos);
                }
            }
        });

        binding.botonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Acción a realizar al hacer clic en el botón flotante de "Añadir"
                // Por ejemplo, abrir una nueva actividad o mostrar un diálogo
                adaptador.controller.setActividad(getActivity());
                adaptador.controller.nuevo();
            }
        });

        binding.botonFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDlgFilter();
            }
        });

        if( recyclerView.getAdapter().getItemCount() > 0 )
            editTextSearch.setVisibility(View.VISIBLE);
        else
            editTextSearch.setVisibility(View.GONE);

    }

    @Override
    public void onResume() {
        super.onResume();
        adaptador.notifyDataSetChanged();
        if( binding.recyclerView.getAdapter().getItemCount() > 0 )
            binding.editTextSearch.setVisibility(View.VISIBLE);
        else
            binding.editTextSearch.setVisibility(View.GONE);
    }

    private void showDlgFilter() {
        List<Tipo> tiposList = adaptador.controllerTipo.getTipos().getLista();
        List<String> nombresTipos = new ArrayList<>();
        for (Tipo tipo : tiposList) {
            nombresTipos.add(tipo.getNombre());
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(adaptador.activity);
        builder.setTitle("Seleccione tipos")
                .setMultiChoiceItems(nombresTipos.toArray(new String[0]), filtroTipos, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        filtroTipos[which] = isChecked;
                    }
                })
                .setPositiveButton("Aplicar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // Aquí puedes aplicar los filtros seleccionados a tu lista
                        aplicarFiltros(tiposList, filtroTipos);
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // Cierra el diálogo
                    }
                })
                .setNeutralButton("Invertir selección", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        for (int i = 0; i < filtroTipos.length; i++) {
                            filtroTipos[i] = !filtroTipos[i];
                        }
                        ((AlertDialog) dialog).getListView().invalidateViews();
                        aplicarFiltros(tiposList, filtroTipos);
                    }
                });
        builder.create().show();
    }

    private void aplicarFiltros(List<Tipo> tipos, boolean[] tiposSeleccionados) {
        // Aquí puedes aplicar los filtros seleccionados a tu lista
        ArrayList<Tipo> tiposSeleccionadosList = new ArrayList<>();
        for (int i = 0; i < tiposSeleccionados.length; i++) {
            if (tiposSeleccionados[i]) {
                tiposSeleccionadosList.add(tipos.get(i));
            }
        }
        // Puedes usar esta lista para filtrar tu lista de elementos
        Cursor newCursor = adaptador.controller.getContactos().extraeCursorFiltradoTipo(tiposSeleccionadosList);
        if( newCursor != null ) {
            adaptador.setCursor(newCursor);
            binding.recyclerView.getAdapter().notifyDataSetChanged();
        }

    }

}
