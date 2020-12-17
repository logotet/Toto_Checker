package com.logotet.totochecker.data.remote;

import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class DataAsyncTask extends AsyncTask<Void, Void, Elements> {

    @Override
    protected Elements doInBackground(Void... voids) {
        try {
            Document doc = Jsoup.connect("https://www.toto.bg/check/6x49").get();
            Elements elementsByClass = doc.getElementsByClass("ball-white");
            return elementsByClass;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(Elements elements) {
        super.onPostExecute(elements);


    }

}
