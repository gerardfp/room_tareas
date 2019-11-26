package com.company.room.db;

import com.company.room.model.Prioridad;
import com.company.room.model.Tarea;
import com.company.room.model.TareaDetalle;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public abstract class TareasAppDao {

    @Insert
    public abstract void insertarPrioridad(Prioridad prioridad);

    @Query("SELECT * FROM Prioridad")
    public abstract LiveData<List<Prioridad>> obtenerPrioridades();



    @Insert
    public abstract void insertarTarea(Tarea tarea);

    @Query("DELETE FROM Tarea WHERE id=:id")
    public abstract void eliminarTarea(int id);

    @Query("SELECT * FROM TareaDetalle")
    public abstract LiveData<List<TareaDetalle>> obtenerTareasDetalle();

    @Query("INSERT INTO Tarea(descripcion, fecha, prioridadId) VALUES (:descripcion, :fecha, (SELECT id FROM Prioridad WHERE descripcion = :prioridad))")
    public abstract void insertarTareaDetalle(String descripcion, String fecha, String prioridad);

}
