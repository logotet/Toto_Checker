package com.logotet.totochecker.repo;

import android.app.Application;
import android.content.Context;

import com.logotet.totochecker.data.local.NumbersDatabase;
import com.logotet.totochecker.data.local.NumbersEntity;
import com.logotet.totochecker.data.local.DataListener;
import com.logotet.totochecker.data.remote.NumberLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppRepository extends Application {
    //    TODO: The repo is extending Application just to use the context but in the meantime context reference
//     should be avoided in static instances.
//     Should check for better solution or using Dagger.
    Context context;
    Executor executor;
    DataListener dataListener;

    private static AppRepository instance;
    private NumberLoader numberLoader;
    private NumbersDatabase db;

    public static AppRepository getInstance() {
        if (instance == null) {
            instance = new AppRepository();
        }
        return instance;
    }

    public AppRepository() {
        numberLoader = NumberLoader.getInstance();
    }

    public void initDb(Context context) {
        db = NumbersDatabase.getInstance(context);
    }


    //    Get data from scraping the website
    public List<String> getNumbers49() {
        return numberLoader.getWin49();
    }

    public List<String> getNumbers42() {
        return numberLoader.getWin42();
    }

    public List<String> getNumbersFirst35() {
        return numberLoader.getWinFirst35();
    }

    public List<String> getNumbersSecond35() {
        return numberLoader.getWinSecond35();
    }


//    Get data from the local database

    public void insertNumbersToDb(NumbersEntity entity) {
        executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            db.numbersDao().insertValue(entity);
        });
    }

    public void getAllFromDb(DataListener dataListener) {
        executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            List<NumbersEntity> values = db.numbersDao().getAllNumbersValues();
            dataListener.onDataReceived(values);
        });
    }

    public void getValueByNumbers(DataListener dataListener, List<String> numbers) {
        executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            NumbersEntity value = db.numbersDao().getValueByNumbers(numbers);
            List<NumbersEntity> values = new ArrayList<>();
            values.add(value);
            dataListener.onDataReceived(values);
        });
    }

    public void getValuesByCategory(DataListener dataListener, String category) {
        executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            List<NumbersEntity> values = db.numbersDao().getAllValuessByCategory(category);
            dataListener.onDataReceived(values);
        });
    }

    public void deleteValueByNumbers(List<String> values) {
        executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            db.numbersDao().deleteValueByNumbers(values);
        });
    }

    public void deleteEntity(NumbersEntity entity) {
        executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            db.numbersDao().delete(entity);
        });
    }

    public void deleteAllValues() {
        executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            db.numbersDao().deleteAll();
        });
    }

    public void updateEntity(NumbersEntity entity) {
        executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            db.numbersDao().updateEntity(entity);
        });
    }


}
