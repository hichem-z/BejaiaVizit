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

import com.exemple.projetws.model.Banque;
import com.exemple.projetws.model.Taxi;
import com.exemple.projetws.ui.BanqueAdapter;
import com.exemple.projetws.ui.main.MainActivity;
import com.exemple.projetws.ui.main.OneViewModel;
import com.exemple.projetws.ui.main.TaxiAdapter;
import com.exemple.projetws.ui.main.TwoViewModel;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.CONNECTIVITY_SERVICE;

public class TaxiFragment extends Fragment {
    RecyclerView recyclerTaxi;
    private List<Taxi> listTaxi;
    private TaxiAdapter taxiAdapter;
    private TaxiAdapter.OnTaxiListner onTaxiListner;
    TwoViewModel twoViewModel;
    NetworkInfo networkInfo;
    ProgressDialog progressDialog;
    ConnectivityManager connectivityManager;


    public TaxiFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (container!=null){
            container.removeAllViews();
        }

        View view = inflater.inflate(R.layout.fragment_taxi, container, false);
  MainActivity.showProgress();
        recyclerTaxi = view.findViewById(R.id.recyclerTaxi);
        listTaxi = new ArrayList<>();
        taxiAdapter = new TaxiAdapter(listTaxi,getActivity().getApplicationContext(),onTaxiListner,getFragmentManager());
        taxiAdapter.setOnTaxiListner(new TaxiAdapter.OnTaxiListner() {
            @Override
            public void onClickTaxi(int position) {

            }
        });
        recyclerTaxi.setAdapter(taxiAdapter);
        recyclerTaxi.setLayoutManager(new LinearLayoutManager(getActivity()));
        twoViewModel = ViewModelProviders.of(this).get(TwoViewModel.class);
        connectivityManager= (ConnectivityManager) getActivity().getSystemService(CONNECTIVITY_SERVICE);
        networkInfo=connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()){
        twoViewModel.gettaxi();
        twoViewModel.taxiMutableLiveData.observe(this, new Observer<List<Taxi>>() {
            @Override
            public void onChanged(List<Taxi> taxis) {
                listTaxi = taxis;
                taxiAdapter.setTaxiList(listTaxi);

                taxiAdapter.notifyDataSetChanged();
                MainActivity.progressDialog.dismiss();

            }
        });
        }else{
            MainActivity.progressDialog.dismiss();
            Toast.makeText(getActivity(),"connexion perdu",Toast.LENGTH_SHORT).show();
        }
        return view;


    }
}