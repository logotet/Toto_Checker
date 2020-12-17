package com.logotet.totochecker.data.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {NumbersEntity.class}, version = 1, exportSchema = false)
@TypeConverters(Converters.class)
public abstract class NumbersDatabase extends RoomDatabase {

    private static NumbersDatabase dbInstance;
    public abstract NumbersDao numbersDao();

    public static  NumbersDatabase getInstance(Context context){
        if(dbInstance == null){
            dbInstance = Room.databaseBuilder(context.getApplicationContext(), NumbersDatabase.class, "substance-database.db")
                    .allowMainThreadQueries()
                    .build();
        }
        return dbInstance;
    }



}
