package com.logotet.totochecker.models;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.logotet.totochecker.data.local.NumbersEntity;
import com.logotet.totochecker.repo.AppRepository;

public class AddNumbersFragmentViewModel extends AndroidViewModel {

   private AppRepository repository;

    public AddNumbersFragmentViewModel(@NonNull Application application) {
        super(application);
        repository = new AppRepository();
        repository.initDb(application.getApplicationContext());
    }

    public void insertNumbersIntoDb(NumbersEntity entity) {
        repository.insertNumbersToDb(entity);
    }

    public void updateEntity(NumbersEntity entity){ repository.updateEntity(entity);}

}
