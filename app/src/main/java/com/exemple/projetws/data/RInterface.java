package com.exemple.projetws.data;

import com.exemple.projetws.model.ImageResponse;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface RInterface {

    @FormUrlEncoded
    @POST("guide/uploadImage.php")
    Single<ImageResponse> uploadImage(@Field("image") String image,@Field("id") String id);

}
