package com.exemple.projetws.data;
import com.exemple.projetws.model.Banque;
import com.exemple.projetws.model.Guide;
import com.exemple.projetws.model.Hotel;
import com.exemple.projetws.model.SiteTouristique;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClientOne {

    private static String BASE_URL = "https://oneservice.azurewebsites.net/api/";

    private OneInterface oneInterface;
    private static ClientOne instance;


    public ClientOne() {

        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).
                addCallAdapterFactory(RxJava3CallAdapterFactory.create()).
                addConverterFactory(GsonConverterFactory.create()).build();
        oneInterface = retrofit.create(OneInterface.class);
    }

    public static ClientOne getInstance() {
        if (instance==null){
            instance = new ClientOne();

        }
        return instance;
    }

    public Observable<List<Hotel>> getHotels(){
        return oneInterface.getHotels();
    }
    public Observable<List<SiteTouristique>> getsites(){

        return oneInterface.getSites();
    }
    public Observable<List<Banque>> getBanques(){

        return oneInterface.getBanques();
    }
    public Observable<Hotel> getHotel(int id_hotel){
        return oneInterface.getHotel(id_hotel);

    }
    public Observable<List<SiteTouristique>> getSites(){

        return oneInterface.getSites();

    }
    public Observable<Guide> addGuide(Guide guide){

        return  oneInterface.addGuide(guide);
    }
    public Single<List<Guide>> getGuides(){
        return oneInterface.getAllGuides();
    }

}
