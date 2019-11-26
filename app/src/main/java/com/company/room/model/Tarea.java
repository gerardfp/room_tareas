package com.company.room.model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = Prioridad.class,
                                  parentColumns = "id",
                                  childColumns = "prioridadId"))
public class Tarea {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String descripcion;
    public String fecha;
    public int prioridadId;

    public Tarea(String descripcion, String fecha, int prioridadId) {
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.prioridadId = prioridadId;
    }
}
