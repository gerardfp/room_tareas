package com.company.room.db;

import android.content.Context;
import android.os.AsyncTask;

import com.company.room.model.Prioridad;
import com.company.room.model.Tarea;
import com.company.room.model.TareaDetalle;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Tarea.class, Prioridad.class}, views = {TareaDetalle.class}, exportSchema = false, version = 1)
public abstract class TareasAppDatabase extends RoomDatabase {

    private static TareasAppDatabase INSTANCE;

    public abstract TareasAppDao dao();

    public static TareasAppDatabase getInstance(final Context context){
        if(INSTANCE == null){
            synchronized (TareasAppDatabase.class) {
                if(INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context, TareasAppDatabase.class, "tareas-db")
                            .fallbackToDestructiveMigration()
                            .addCallback(new Callback() {
                                @Override
                                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                    super.onCreate(db);
                                    insertarDatosIniciales();
                                }
                            })
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static void insertarDatosIniciales(){
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                INSTANCE.dao().insertarPrioridad(new Prioridad("ALTA"));
                INSTANCE.dao().insertarPrioridad(new Prioridad("MEDIA"));
                INSTANCE.dao().insertarPrioridad(new Prioridad("BAJA"));

                INSTANCE.dao().insertarTareaDetalle("Comprar pilas", "1/1/1970", "ALTA");
                INSTANCE.dao().insertarTareaDetalle("Vender pilas", "2/1/1970", "BAJA");
                INSTANCE.dao().insertarTareaDetalle("Alquilar pilas", "3/1/1970", "MEDIA");
            }
        });
    }
}