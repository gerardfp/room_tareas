package com.company.room.viewmodel;

import android.app.Application;
import android.os.AsyncTask;

import com.company.room.db.TareasAppDao;
import com.company.room.db.TareasAppDatabase;
import com.company.room.model.Prioridad;
import com.company.room.model.Tarea;
import com.company.room.model.TareaDetalle;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class TareasAppViewModel extends AndroidViewModel {

    private TareasAppDao dao;

    public TareasAppViewModel(@NonNull Application application) {
        super(application);

        dao = TareasAppDatabase.getInstance(application).dao();
    }

    public LiveData<List<Prioridad>> obtenerPrioridades(){
        return dao.obtenerPrioridades();
    }

    public LiveData<List<TareaDetalle>> obtenerTareasDetalle(){
        return dao.obtenerTareasDetalle();
    }

    public void insertarTarea(final Tarea tarea){
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                dao.insertarTarea(tarea);
            }
        });
    }

    public void eliminarTarea(final int id){
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                dao.eliminarTarea(id);
            }
        });
    }
}