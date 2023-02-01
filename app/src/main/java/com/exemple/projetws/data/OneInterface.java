package com.exemple.projetws.data;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

import com.exemple.projetws.model.Banque;
import com.exemple.projetws.model.Guide;
import com.exemple.projetws.model.Hotel;
import com.exemple.projetws.model.SiteTouristique;
import com.exemple.projetws.model.Taxi;

public interface OneInterface {

    @GET("hotels")
    public Observable<List<Hotel>> getHotels();

    @GET("banques")
    public Observable<List<Banque>> getBanques();

    @GET("sites")
    public Observable<List<SiteTouristique>> getSites();
    @GET("hotel/{id_hotel}")
    public Observable<Hotel> getHotel(@Path("id_hotel") int id_hotel);

    @POST("guide/add")
    public Observable<Guide> addGuide(@Body Guide guide);

    @GET("guide/getGuides")
    public Single<List<Guide>> getAllGuides();









}
