package com.exemple.projetws.ui.main;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.exemple.projetws.AgenceFragment;
import com.exemple.projetws.BusFragment;
import com.exemple.projetws.TaxiFragment;
import com.exemple.projetws.data.ClientTwo;
import com.exemple.projetws.model.Agence;
import com.exemple.projetws.model.Bus;
import com.exemple.projetws.model.Taxi;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class TwoViewModel extends ViewModel {

public MutableLiveData<List<Taxi>> taxiMutableLiveData  = new MutableLiveData<>();
public MutableLiveData<List<Agence>> agenceMutableLiveData  = new MutableLiveData<>();
public MutableLiveData<List<Bus>> busMutableLiveData  = new MutableLiveData<>();
public CompositeDisposable compositeDisposable = new CompositeDisposable();

public void gettaxi(){
    Single<List<Taxi>> observable = ClientTwo.getInstance().getAllTaxi().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    compositeDisposable.add(observable.subscribe(o->taxiMutableLiveData.setValue(o),e->MainActivity.showerror(new TaxiFragment())));

}
    public void getAgences(){
        Single<List<Agence>> observable = ClientTwo.getInstance().getAllAgence().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        compositeDisposable.add(observable.subscribe(o->agenceMutableLiveData.setValue(o),e->MainActivity.showerror(new AgenceFragment())));

    }
    public void getBus(){
    Single<List<Bus>> single = ClientTwo.getInstance().getAllBus().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    compositeDisposable.add(single.subscribe(o->busMutableLiveData.setValue(o),e->MainActivity.showerror(new BusFragment())));

    }
    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
}
}
