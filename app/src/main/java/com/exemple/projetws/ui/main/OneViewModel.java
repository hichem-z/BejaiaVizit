package com.exemple.projetws.ui.main;


import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.exemple.projetws.BankFragment;
import com.exemple.projetws.GuidesFragment;
import com.exemple.projetws.HotelFragment;
import com.exemple.projetws.InscriptionFragment;
import com.exemple.projetws.SiteFragment;
import com.exemple.projetws.data.ClientOne;
import com.exemple.projetws.model.Banque;
import com.exemple.projetws.model.Guide;
import com.exemple.projetws.model.Hotel;
import com.exemple.projetws.model.SiteTouristique;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class OneViewModel extends ViewModel {

    public MutableLiveData<List<Hotel>> hotelmMutableLiveData  = new MutableLiveData<>();
    public MutableLiveData<Hotel> hotelMutableLiveData = new MutableLiveData<>();
    public  MutableLiveData<List<Banque>> banqueMutableLiveData = new MutableLiveData<>();
    public  MutableLiveData<List<SiteTouristique>> siteMutableLiveData = new MutableLiveData<>();
    public  MutableLiveData<List<Guide>> guidesMutableLiveData = new MutableLiveData<>();
    public  MutableLiveData<Guide> guideMutableLiveData= new MutableLiveData<>();
    public CompositeDisposable compositeDisposable = new CompositeDisposable();

public void getHotels(){

     ClientOne.getInstance().getHotels().subscribeOn(Schedulers.io()).
             observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<List<Hotel>>() {
         @Override
         public void onSubscribe(@NonNull Disposable d) {
            compositeDisposable.add(d);
         }

         @Override
         public void onNext(@NonNull List<Hotel> hotels) {
                hotelmMutableLiveData.setValue(hotels);
         }

         @Override
         public void onError(@NonNull Throwable e) {
             MainActivity.showerror(new HotelFragment());
         }

         @Override
         public void onComplete() {

         }
     });
}
public void getHotel(int id_hotel){
    ClientOne.getInstance().getHotel(id_hotel).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Hotel>() {
        @Override
        public void onSubscribe(@NonNull Disposable d) {
            compositeDisposable.add(d);
        }

        @Override
        public void onNext(@NonNull Hotel hotel) {
                hotelMutableLiveData.setValue(hotel);
        }

        @Override
        public void onError(@NonNull Throwable e) {
            MainActivity.showerror(new HotelFragment());
        }

        @Override
        public void onComplete() {

        }
    });


}

public void getBanques(){
ClientOne.getInstance().getBanques().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<List<Banque>>() {
    @Override
    public void onSubscribe(@NonNull Disposable d) {
        compositeDisposable.add(d);

    }


    @Override
    public void onNext(@NonNull List<Banque> banques) {
                banqueMutableLiveData.setValue(banques);
    }

    @Override
    public void onError(@NonNull Throwable e) {
        MainActivity.showerror(new BankFragment());
    }

    @Override
    public void onComplete() {

    }
});

}
    public void getGuides(){
        Single<List<Guide>> observable = ClientOne.getInstance().getGuides().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        compositeDisposable.add(observable.subscribe(o->guidesMutableLiveData.setValue(o),e->MainActivity.showerror(new GuidesFragment())));
    }
public void getSites(){

    Observable<List<SiteTouristique>> observable = ClientOne.getInstance().getsites().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    compositeDisposable.add(observable.subscribe(o->siteMutableLiveData.setValue(o),e->MainActivity.showerror(new SiteFragment())));

}
public void addGuide(Guide guide){
    Observable<Guide> observable = ClientOne.getInstance().addGuide(guide).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    compositeDisposable.add(observable.subscribe(o->guideMutableLiveData.setValue(o),e->MainActivity.showerror(new InscriptionFragment())));
}


    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
}
}
