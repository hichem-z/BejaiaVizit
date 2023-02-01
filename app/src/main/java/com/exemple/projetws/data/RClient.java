package com.exemple.projetws.data;

import com.exemple.projetws.model.Hotel;
import com.exemple.projetws.model.ImageResponse;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RClient {
    private static String BASE_URL = "http://apitransport.eredlearning.com/projectsw/";

    private RInterface rInterface;
    private static RClient instance;


    public RClient() {

        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).
                addCallAdapterFactory(RxJava3CallAdapterFactory.create()).
                addConverterFactory(GsonConverterFactory.create()).build();
        rInterface = retrofit.create(RInterface.class);
    }

    public static RClient getInstance() {
        if (instance==null){
            instance = new RClient();

        }
        return instance;
    }

    public Single<ImageResponse> upImage(String s,String id){
        return rInterface.uploadImage(s,id);
    }
}
