package com.exemple.projetws.data;

import com.exemple.projetws.model.Restaurant;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClientThree {
    public static String BASE_URL = "https://salim06.pythonanywhere.com/";
    private ThreeInterface threeInterface;
    private static ClientThree instance;

    public ClientThree() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).
                addCallAdapterFactory(RxJava3CallAdapterFactory.create()).
                addConverterFactory(GsonConverterFactory.create()).build();
        threeInterface = retrofit.create(ThreeInterface.class);

    }
    public static ClientThree getInstance(){
        if (instance==null){
            instance = new ClientThree();
        }
        return instance;
    }

    public Single<List<Restaurant>> getRestaurants(){

        return threeInterface.getRestaurants("json");
    }


}
