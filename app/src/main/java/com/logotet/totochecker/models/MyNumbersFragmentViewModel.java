package com.logotet.totochecker.models;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.logotet.totochecker.data.local.NumbersEntity;
import com.logotet.totochecker.repo.AppRepository;
import com.logotet.totochecker.data.local.DataListener;

import java.util.ArrayList;
import java.util.List;

public class MyNumbersFragmentViewModel extends AndroidViewModel {

    private AppRepository appRepository;
    private List<String> matchingNumbers = new ArrayList<>();
    private List<String> userNumbers = new ArrayList<>();

    public MyNumbersFragmentViewModel(@NonNull Application application) {
        super(application);
        appRepository = new AppRepository();
        appRepository.initDb(getApplication().getApplicationContext());
    }

    public void getAllData(DataListener listener) {
        appRepository.getAllFromDb(listener);
    }

    public void getValueByNumbers(List<String> numbers, DataListener listener) {
        appRepository.getValueByNumbers(listener, numbers);
    }

    public void getAllDataFromCategory(String category, DataListener listener) {
        appRepository.getValuesByCategory(listener, category);
    }

    public void deleteValue(List<String> numbers) {
        appRepository.deleteValueByNumbers(numbers);
    }

    public void deleteEntity(NumbersEntity entity) {
        appRepository.deleteEntity(entity);
    }

    public void updateEntity(NumbersEntity entity){ appRepository.updateEntity(entity);}

    public void deleteAllValues() {
        appRepository.deleteAllValues();
    }

    //TODO make a method that checks every number from the database and sets the matching ones
    public void setMatchingNumbers(List<String> winNumbs, List<String> userNumbs) {
        matchingNumbers.clear();
        for (int i = 0; i < userNumbs.size(); i++) {
            String userNumb = userNumbs.get(i);
            if (winNumbs.contains(userNumb)) {
                matchingNumbers.add(userNumb);
            }
        }
    }

}
