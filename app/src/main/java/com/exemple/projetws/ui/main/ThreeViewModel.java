package com.exemple.projetws.ui.main;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.exemple.projetws.RestaurantFragment;
import com.exemple.projetws.data.ClientThree;
import com.exemple.projetws.model.Restaurant;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ThreeViewModel extends ViewModel {
    public MutableLiveData<List<Restaurant>> restaurantMutableLiveData = new MutableLiveData<>();
    public CompositeDisposable compositeDisposable = new CompositeDisposable();

    public void getRetaurants(){
        Single<List<Restaurant>> single = ClientThree.getInstance().getRestaurants().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        compositeDisposable.add(single.subscribe(s->restaurantMutableLiveData.setValue(s),e->MainActivity.showerror(new RestaurantFragment())));
    }


}
