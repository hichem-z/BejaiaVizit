package com.exemple.projetws.data;

import com.exemple.projetws.model.Restaurant;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ThreeInterface {

    @GET("restaurants")
    public Single<List<Restaurant>> getRestaurants(@Query("format") String format);
}
