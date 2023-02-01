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

import com.exemple.projetws.model.Agence;
import com.exemple.projetws.model.Bus;
import com.exemple.projetws.model.BusAdapter;
import com.exemple.projetws.ui.main.AgenceAdapter;
import com.exemple.projetws.ui.main.MainActivity;
import com.exemple.projetws.ui.main.TwoViewModel;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.CONNECTIVITY_SERVICE;


public class BusFragment extends Fragment {
    RecyclerView recyclerBus;
    private List<Bus> listBus;
    private BusAdapter busAdapter;
    private BusAdapter.OnBusListner onBusListner;
    TwoViewModel twoViewModel;
    NetworkInfo networkInfo;
    ConnectivityManager connectivityManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (container!=null){
            container.removeAllViews();
        }

        View view = inflater.inflate(R.layout.fragment_bus, container, false);
        MainActivity.showProgress();
        recyclerBus = view.findViewById(R.id.recyclerBus);
        listBus = new ArrayList<>();
        busAdapter = new BusAdapter(listBus,getActivity().getApplicationContext(),onBusListner,getFragmentManager());
        busAdapter.setOnBusListner(new BusAdapter.OnBusListner() {
            @Override
            public void onClickBus(int position) {

            }
        });
        recyclerBus.setAdapter(busAdapter);
        recyclerBus.setLayoutManager(new LinearLayoutManager(getActivity()));
        twoViewModel = ViewModelProviders.of(this).get(TwoViewModel.class);
        connectivityManager= (ConnectivityManager) getActivity().getSystemService(CONNECTIVITY_SERVICE);
        networkInfo=connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()){
        twoViewModel.getBus();
        twoViewModel.busMutableLiveData.observe(this, new Observer<List<Bus>>() {
            @Override
            public void onChanged(List<Bus> agences) {
                listBus = agences;
                busAdapter.setBuslList(listBus);
                busAdapter.notifyDataSetChanged();
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