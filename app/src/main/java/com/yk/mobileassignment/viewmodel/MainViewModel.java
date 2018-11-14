package com.yk.mobileassignment.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.databinding.ObservableField;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.yk.mobileassignment.R;
import com.yk.mobileassignment.model.City;
import com.yk.mobileassignment.model.Coord;
import com.yk.mobileassignment.model.Trie;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private MutableLiveData<List<City>> cities;
    private MutableLiveData<Coord> coord;
    private ObservableField<Boolean> isLoading = new ObservableField<>();
    private List<City> cityList;
    private Context context;
    private Gson gson;
    private Trie trie;
    private LoadingAsyncTask loadingAsyncTask;

    public MainViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public void init() {
        cities = new MutableLiveData<>();
        coord = new MutableLiveData<>();
        isLoading.set(false);
        trie = new Trie();
        loadingAsyncTask = new LoadingAsyncTask();
        loadingAsyncTask.execute();
    }

    private void fetchList() {
        gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        Type collectionType = new TypeToken<List<City>>() {
        }.getType();
        cityList = gson.fromJson(readFileFromRawDirectory(R.raw.cities), collectionType);

        Collections.sort(cityList, new Comparator<City>() {
            @Override
            public int compare(City o1, City o2) {
                return o1.getDisplayName().toLowerCase().compareTo(o2.getDisplayName().toLowerCase());
            }
        });

        for (City city : cityList) {
            trie.insert(city);
        }
    }

    public void onItemClick(Coord selectedCoord) {
        this.coord.setValue(selectedCoord);
    }

    public MutableLiveData<Coord> getCoord() {
        return coord;
    }

    public MutableLiveData<List<City>> getCities() {
        return cities;
    }

    public ObservableField<Boolean> getIsLoading() {
        return isLoading;
    }

    public void onTextChanged(String query) {
        if (query.isEmpty()) {
            cities.setValue(cityList);
        } else {
            cities.setValue(trie.autocomplete(query));
        }
    }

    private String readFileFromRawDirectory(int resourceId) {
        InputStream iStream = context.getResources().openRawResource(resourceId);
        ByteArrayOutputStream byteStream = null;
        try {
            byte[] buffer = new byte[iStream.available()];
            iStream.read(buffer);
            byteStream = new ByteArrayOutputStream();
            byteStream.write(buffer);
            byteStream.close();
            iStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return byteStream.toString();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        loadingAsyncTask.cancel(true);
    }

    class LoadingAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            isLoading.set(true);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            fetchList();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            cities.postValue(cityList);
            isLoading.set(false);
        }
    }
}
