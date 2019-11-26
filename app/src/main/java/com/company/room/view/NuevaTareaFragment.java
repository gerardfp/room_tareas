package com.company.room.view;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.company.room.R;
import com.company.room.model.Prioridad;
import com.company.room.model.Tarea;
import com.company.room.viewmodel.TareasAppViewModel;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class NuevaTareaFragment extends Fragment {

    private TareasAppViewModel tareasAppViewModel;
    private NavController navController;

    private EditText descripcionEditText;
    private Spinner prioridadSpinner;

    private int idPrioridadSeleccionada;

    public NuevaTareaFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_nueva_tarea, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);
        tareasAppViewModel = ViewModelProviders.of(requireActivity()).get(TareasAppViewModel.class);

        descripcionEditText = view.findViewById(R.id.edittext_descripcion);
        prioridadSpinner = view.findViewById(R.id.spinner_prioridad);

        view.findViewById(R.id.button_crearTarea).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(descripcionEditText.getText().toString().isEmpty()){
                    descripcionEditText.setError("Introduzca la descripción");
                    return;
                }

                tareasAppViewModel.insertarTarea(new Tarea(descripcionEditText.getText().toString(), LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), idPrioridadSeleccionada));
                navController.popBackStack();
            }
        });

        tareasAppViewModel.obtenerPrioridades().observe(this, new Observer<List<Prioridad>>() {
            @Override
            public void onChanged(final List<Prioridad> prioridades) {
                prioridadSpinner.setAdapter(new ArrayAdapter<>(requireActivity(), R.layout.support_simple_spinner_dropdown_item, prioridades));
                prioridadSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        idPrioridadSeleccionada = prioridades.get(i).id;
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {}
                });
            }
        });
    }
}
