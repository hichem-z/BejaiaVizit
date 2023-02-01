package com.exemple.projetws;

import android.app.ProgressDialog;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.exemple.projetws.model.Hotel;
import com.exemple.projetws.model.Restaurant;
import com.exemple.projetws.model.SiteTouristique;
import com.exemple.projetws.ui.HotelAdapter;
import com.exemple.projetws.ui.RestaurantAdapter;
import com.exemple.projetws.ui.SiteAdapter;
import com.exemple.projetws.ui.main.MainActivity;
import com.exemple.projetws.ui.main.OneViewModel;
import com.exemple.projetws.ui.main.ThreeViewModel;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.CONNECTIVITY_SERVICE;


public class RestaurantFragment extends Fragment {
    RecyclerView recyclerRestau;
    RestaurantAdapter restaurantAdapter;
    private List<Restaurant> listRestaurants;
    private RestaurantAdapter.onRestaurantListner onRestaurantListner;
    ThreeViewModel threeViewModel;
    NetworkInfo networkInfo;
    ConnectivityManager connectivityManager;
    public RestaurantFragment() {

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (container!=null){
            container.removeAllViews();
        }

        View view = inflater.inflate(R.layout.fragment_restaurant, container, false);
        MainActivity.showProgress();
        recyclerRestau = view.findViewById(R.id.recycler);
        listRestaurants = new ArrayList<>();
        restaurantAdapter = new RestaurantAdapter(listRestaurants,getActivity(),onRestaurantListner,getFragmentManager());
        restaurantAdapter.setonRestaurantListner(new RestaurantAdapter.onRestaurantListner() {
            @Override
            public void onClickRestaurant(int position) {

            }
        });
        recyclerRestau.setAdapter(restaurantAdapter);
        recyclerRestau.setLayoutManager(new LinearLayoutManager(getActivity()));
        threeViewModel = ViewModelProviders.of(this).get(ThreeViewModel.class);
        connectivityManager= (ConnectivityManager) getActivity().getSystemService(CONNECTIVITY_SERVICE);
        networkInfo=connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()){
        threeViewModel.getRetaurants();
        threeViewModel.restaurantMutableLiveData.observe(this, new Observer<List<Restaurant>>() {
            @Override
            public void onChanged(List<Restaurant> restaurants) {
                MainActivity.progressDialog.dismiss();
                listRestaurants= restaurants;
                restaurantAdapter.setList(listRestaurants);
            }
        });
        }else{
            MainActivity.progressDialog.dismiss();

            Toast.makeText(getActivity(),"connexion perdu",Toast.LENGTH_SHORT).show();
        }

        return view;
    }
}