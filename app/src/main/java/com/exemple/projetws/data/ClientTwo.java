package com.exemple.projetws.data;

import com.exemple.projetws.model.Agence;
import com.exemple.projetws.model.Bus;
import com.exemple.projetws.model.Taxi;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClientTwo {

    public static String BASE_URL = "http://apitransport.eredlearning.com/";
    private TwoInterface twoInterface;
    private static ClientTwo instance;


    public ClientTwo() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).
                addCallAdapterFactory(RxJava3CallAdapterFactory.create()).
                addConverterFactory(GsonConverterFactory.create()).build();
        twoInterface = retrofit.create(TwoInterface.class);

    }
    public static ClientTwo getInstance(){
        if (instance==null){
            instance = new ClientTwo();
        }
            return instance;
    }

    public Single<List<Taxi>> getAllTaxi(){
        return twoInterface.getAllTaxi();

    }
    public Single<List<Agence>> getAllAgence(){
        return twoInterface.getAllAgence();

    }
    public Single<List<Bus>> getAllBus(){
        return twoInterface.getAllBus();
    }

}
