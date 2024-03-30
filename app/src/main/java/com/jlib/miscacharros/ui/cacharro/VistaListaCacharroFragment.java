package com.jlib.miscacharros.ui.cacharro;

import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jlib.miscacharros.Aplicacion;
import com.jlib.miscacharros.R;
import com.jlib.miscacharros.databinding.VistaCacharroListaBinding;

import java.util.Locale;

public class VistaListaCacharroFragment extends Fragment {

    private AdaptadorCacharrosBD adaptador;
    private @NonNull VistaCacharroListaBinding binding;

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

        RecyclerView recyclerView = binding.recyclerView;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
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



}
