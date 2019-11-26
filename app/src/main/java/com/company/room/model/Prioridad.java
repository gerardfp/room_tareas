package com.company.room.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Prioridad {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String descripcion;

    public Prioridad(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return descripcion;
    }
}
