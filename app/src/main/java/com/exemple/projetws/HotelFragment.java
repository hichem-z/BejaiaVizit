package com.exemple.projetws;

import android.app.ProgressDialog;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.exemple.projetws.model.Hotel;
import com.exemple.projetws.ui.HotelAdapter;
import com.exemple.projetws.ui.main.MainActivity;
import com.exemple.projetws.ui.main.OneViewModel;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.CONNECTIVITY_SERVICE;


public class HotelFragment extends Fragment {
    private OneViewModel oneViewModel;
    private List<Hotel>listeHotels;
    RecyclerView recyclerView;
    HotelAdapter ha;
    NetworkInfo networkInfo;
    ConnectivityManager connectivityManager;
    HotelAdapter.OnHotelListner onHotelListner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (container!=null){
            container.removeAllViews();
        }

                View view = inflater.inflate(R.layout.fragment_hotel, container, false);
        MainActivity.showProgress();
        recyclerView = view.findViewById(R.id.recycler);
        listeHotels = new ArrayList<>();
        ha = new HotelAdapter(listeHotels,getActivity().getApplicationContext(),onHotelListner);


        ha.setOnHotelListner(new HotelAdapter.OnHotelListner() {
            @Override
            public void onClickHotel(int position) {
                connectivityManager= (ConnectivityManager) getActivity().getSystemService(CONNECTIVITY_SERVICE);
                networkInfo=connectivityManager.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {

                Bundle bundle = new Bundle();
                bundle.putInt("id_hotel",listeHotels.get(position).getId_hotel());
                HotelDetFragment hdt = new HotelDetFragment();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                hdt.setArguments(bundle);
                fragmentTransaction.replace(R.id.fragment,hdt,"hotelfragment");
                fragmentTransaction.addToBackStack("hotelfragment").commit();
                }else{

                    Toast.makeText(getActivity(),"connexion perdu",Toast.LENGTH_SHORT).show();
                }

            }
        });
        recyclerView.setAdapter(ha);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        oneViewModel = ViewModelProviders.of(this).get(OneViewModel.class);
        connectivityManager= (ConnectivityManager) getActivity().getSystemService(CONNECTIVITY_SERVICE);
        networkInfo=connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
        oneViewModel.getHotels();

        oneViewModel.hotelmMutableLiveData.observe(this, new Observer<List<Hotel>>() {
            @Override
            public void onChanged(List<Hotel> hotels) {
                MainActivity.progressDialog.dismiss();

                listeHotels=hotels;
                ha.setHotelList(listeHotels);
            }
        });
        }else{
            MainActivity.progressDialog.dismiss();

            Toast.makeText(getActivity(),"connexion perdu",Toast.LENGTH_SHORT).show();
        }


        return view;
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}