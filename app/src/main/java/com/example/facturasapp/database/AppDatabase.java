package com.example.facturasapp.database;

//En esta clase voy a implementar el room

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.facturasapp.model.FacturaVO;

@Database(entities = {FacturaVO.class}, version = 1 )
public abstract class AppDatabase extends RoomDatabase {

    public abstract FacturaDao facturaDao();

    private static AppDatabase INSTANCE;

    public static AppDatabase getDbInstance(Context context){

        if (INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "DB_NAME")
                    .allowMainThreadQueries()
                    .build();
        }

        return INSTANCE;
    }
}
