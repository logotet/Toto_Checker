package com.logotet.totochecker.data.remote;

import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class NumberLoader {

    private List<String> win49 = new ArrayList<>();
    private List<String> winFirst35 = new ArrayList<>();
    private List<String> winSecond35 = new ArrayList<>();
    private List<String> win42 = new ArrayList<>();
    private Elements elementList;
    private static NumberLoader instance;

    private NumberLoader() {
        setElementsList();
        convertData(elementList);
    }

    public static NumberLoader getInstance(){
        if(instance == null){
            instance = new NumberLoader();
        }
        return instance;
    }

    public Elements setElementsList()  {
        DataAsyncTask asyncTask = new DataAsyncTask();
        try {
            elementList =asyncTask.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return elementList;
    }

    public void convertData(Elements elements){
        for (int i = 0; i < elements.size(); i++) {
            String value = elements.get(i).text();
            if (i < 6) {
                win49.add(value);
            } else if (i < 11) {
                winFirst35.add(value);
            } else if (i < 16) {
                winSecond35.add(value);
            } else if (i < 22) {
                win42.add(value);
            }
        }
    }

    public List<String> getWin49() {
        return win49;
    }

    public List<String> getWinFirst35() {
        return winFirst35;
    }

    public List<String> getWinSecond35() {
        return winSecond35;
    }

    public List<String> getWin42() {
        return win42;
    }

//    @Override
//    public void onElementsRecieved(Elements elements) {
//
//    }
}

