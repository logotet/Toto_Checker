package com.logotet.totochecker.models;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.SparseArray;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.logotet.totochecker.repo.AppRepository;

import java.util.ArrayList;
import java.util.List;

public class MainActivityViewModel extends ViewModel {

    private AppRepository appRepository;
    private List<String> matchingNumbers = new ArrayList<>();
    private List<String> userNumbers = new ArrayList<>();
    private String convertedText;

//    LiveData
    private MutableLiveData<List<String>> winningNumbers49;
    private MutableLiveData<List<String>> winningNumbersFirst35;
    private MutableLiveData<List<String>> winningNumbersSec35;
    private MutableLiveData<List<String>> winningNumbers42;

    public MainActivityViewModel() {
        appRepository = AppRepository.getInstance();
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

    public List<String> getUserNumbers() {
        return userNumbers;
    }


    public String getConvertedText() {
        return convertedText;
    }

    public List<String> getMatchingNumbers() {
        return matchingNumbers;
    }

//    public List<String> getWinningNumbersFirst35() {
//        return appRepository.getNumbersFirst35();
//    }

//    LiveData

    public MutableLiveData<List<String>> getWinningNumbers49(){
        if(winningNumbers49 == null){
            winningNumbers49 = new MutableLiveData<>();
            winningNumbers49.postValue(appRepository.getNumbers49());
        }
        return winningNumbers49;
    }

    public MutableLiveData<List<String>> getWinningNumbers35() {
        if(winningNumbersFirst35 == null){
            winningNumbersFirst35 = new MutableLiveData<>();
            winningNumbersFirst35.postValue(appRepository.getNumbersFirst35());
        }
        return winningNumbersFirst35;
    }

    public MutableLiveData<List<String>> getWinningNumbersFirst35() {
        if(winningNumbersFirst35 == null){
            winningNumbersFirst35 = new MutableLiveData<>();
            winningNumbersFirst35.postValue(appRepository.getNumbersFirst35());
        }
        return winningNumbersFirst35;
    }

    public MutableLiveData<List<String>> getWinningNumbersSec35() {
        if (winningNumbersSec35 == null) {
            winningNumbersSec35 = new MutableLiveData<>();
            winningNumbersSec35.postValue(appRepository.getNumbersSecond35());
        }
        return winningNumbersSec35;
    }

    public MutableLiveData<List<String>> getWinningNumbers42() {
        if(winningNumbers42 == null){
            winningNumbers42 = new MutableLiveData<>();
            winningNumbers42.postValue(appRepository.getNumbers42());
        }
        return winningNumbers42;
    }




    //    public List<String> getWinningNumbersSecond35() {
//        return appRepository.getNumbersSecond35();
//    }

//    public List<String> getWinningNumbers42() {
//        return appRepository.getNumbers42();
//    }

//    public List<String> getWinningNumbers49() {
//        return appRepository.getNumbers49();
//    }




}
