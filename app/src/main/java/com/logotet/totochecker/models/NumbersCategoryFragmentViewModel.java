package com.logotet.totochecker.models;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.SparseArray;

import androidx.lifecycle.AndroidViewModel;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.logotet.totochecker.data.local.NumbersEntity;
import com.logotet.totochecker.repo.AppRepository;

import java.util.ArrayList;
import java.util.List;

public class NumbersCategoryFragmentViewModel extends AndroidViewModel {

    private AppRepository appRepository;
    private List<String> matchingNumbers = new ArrayList<>();
    private List<String> userNumbers = new ArrayList<>();
    private String convertedText;

    public NumbersCategoryFragmentViewModel(Application application) {
        super(application);
        appRepository = AppRepository.getInstance();
        appRepository.initDb(application.getApplicationContext());
    }

    public void detectText(Context context, Bitmap bitmap) {
        TextRecognizer textRecognizer = new TextRecognizer.Builder(context).build();
        Frame frame = new Frame.Builder().setBitmap(bitmap).build();
        SparseArray<TextBlock> sparseArray = textRecognizer.detect(frame);
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < sparseArray.size(); i++) {
            TextBlock tx = sparseArray.get(i);
            String str = tx.getValue();
            stringBuilder.append(str);
        }
        convertedText = stringBuilder.toString().toUpperCase();
    }

    public void insertIntoDb(NumbersEntity entity){
        appRepository.insertNumbersToDb(entity);
    }

    public void setMatchingNumbers(List<String> winNumbs, List<String> userNumbs) {
        matchingNumbers.clear();
        for (int i = 0; i < userNumbs.size(); i++) {
            String userNumb = userNumbs.get(i);
            if (winNumbs.contains(userNumb)) {
                matchingNumbers.add(userNumb);
            }
        }
    }

    public void setUserNumbers(String userInput) {
        if (userInput != null) {
            char[] input = userInput.toCharArray();
            for (char c : input) {
                if (Character.isDigit(c)) {
                    userNumbers.add(String.valueOf(c));
                }
            }
        }
    }


    public NumbersEntity toEntity(List<String> numbers, String category){
        return  new NumbersEntity(numbers, category);
    }

    public List<String> getUserNumbers() {
        return userNumbers;
    }

    public String getConvertedText() {
        return convertedText;
    }

    public List<String> getMatchingNumbers() {
        return matchingNumbers;
    }

    public List<String> getWinningNumbersFirst35() {
        return appRepository.getNumbersFirst35();
    }

    public List<String> getWinningNumbersSecond35() {
        return appRepository.getNumbersSecond35();
    }

    public List<String> getWinningNumbers42() {
        return appRepository.getNumbers42();
    }

    public List<String> getWinningNumbers49() {
        return appRepository.getNumbers49();
    }




}
