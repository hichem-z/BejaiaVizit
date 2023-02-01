package com.exemple.projetws.data;

import com.exemple.projetws.model.Agence;
import com.exemple.projetws.model.Bus;
import com.exemple.projetws.model.Taxi;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;

public interface TwoInterface {

    @GET("Transport/2?fbclid=IwAR0Gd6KTZlfmL1YYH2g1oAXLOC-fEObYXlY7vz88CsJQJP2wmnBCEpV6v_8")
    public Single<List<Taxi>> getAllTaxi();

    @GET("Transport/1?fbclid=IwAR0Gd6KTZlfmL1YYH2g1oAXLOC-fEObYXlY7vz88CsJQJP2wmnBCEpV6v_8")
    public Single<List<Bus>> getAllBus();

    @GET("Transport/3?fbclid=IwAR0Gd6KTZlfmL1YYH2g1oAXLOC-fEObYXlY7vz88CsJQJP2wmnBCEpV6v_8")
    public Single<List<Agence>> getAllAgence();

}
