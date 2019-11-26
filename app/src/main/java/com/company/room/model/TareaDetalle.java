package com.company.room.model;

import androidx.room.DatabaseView;

@DatabaseView("SELECT Tarea.id, Tarea.descripcion, Tarea.fecha, Prioridad.descripcion AS prioridad " +
        "FROM Tarea JOIN Prioridad ON Tarea.prioridadId = Prioridad.id")
public class TareaDetalle {
    public int id;
    public String descripcion;
    public String fecha;
    public String prioridad;
}
