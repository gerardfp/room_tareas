package com.company.room.view;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.company.room.R;
import com.company.room.model.TareaDetalle;
import com.company.room.viewmodel.TareasAppViewModel;

import java.util.List;

public class ListaTareasFragment extends Fragment {

    private NavController navController;
    private TareasAppViewModel tareasAppViewModel;
    private TareasAdapter tareasAdapter;

    public ListaTareasFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_lista_tareas, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);
        tareasAppViewModel = ViewModelProviders.of(requireActivity()).get(TareasAppViewModel.class);


        view.findViewById(R.id.fab_nuevaTarea).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.nuevaTareaFragment);
            }
        });

        RecyclerView recyclerView = view.findViewById(R.id.recycler_listaTareas);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));

        tareasAdapter = new TareasAdapter();
        recyclerView.setAdapter(tareasAdapter);

        tareasAppViewModel.obtenerTareasDetalle().observe(this, new Observer<List<TareaDetalle>>() {
            @Override
            public void onChanged(List<TareaDetalle> queryResult) {
                tareasAdapter.establecerListaTareas(queryResult);
            }
        });
    }


    class TareasAdapter extends RecyclerView.Adapter<TareasAdapter.TareaViewHolder>{

        List<TareaDetalle> tareaDetalleList;

        @NonNull
        @Override
        public TareaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new TareaViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_tarea, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull TareaViewHolder holder, int position) {
            final TareaDetalle tareaDetalle = tareaDetalleList.get(position);

            holder.descripcionTextView.setText(tareaDetalle.descripcion);
            holder.fechaTextView.setText(tareaDetalle.fecha);
            holder.prioridadTextView.setText(tareaDetalle.prioridad);

            holder.eliminarImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    tareasAppViewModel.eliminarTarea(tareaDetalle.id);
                }
            });
        }

        @Override
        public int getItemCount() {
            return tareaDetalleList != null ? tareaDetalleList.size() : 0;
        }

        void establecerListaTareas(List<TareaDetalle> list){
            tareaDetalleList = list;
            notifyDataSetChanged();
        }

        class TareaViewHolder extends RecyclerView.ViewHolder {
            TextView descripcionTextView, fechaTextView, prioridadTextView;
            ImageView eliminarImageView;

            TareaViewHolder(@NonNull View itemView) {
                super(itemView);
                descripcionTextView = itemView.findViewById(R.id.textview_descripcion);
                fechaTextView = itemView.findViewById(R.id.textview_fecha);
                prioridadTextView = itemView.findViewById(R.id.textview_prioridad);
                eliminarImageView = itemView.findViewById(R.id.imageview_eliminar);
            }
        }
    }
}
