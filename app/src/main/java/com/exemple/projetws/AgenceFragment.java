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
import com.exemple.projetws.model.Taxi;
import com.exemple.projetws.ui.main.AgenceAdapter;
import com.exemple.projetws.ui.main.MainActivity;
import com.exemple.projetws.ui.main.TaxiAdapter;
import com.exemple.projetws.ui.main.TwoViewModel;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.CONNECTIVITY_SERVICE;


public class AgenceFragment extends Fragment {

    RecyclerView recyclerAgence;
    private List<Agence> listAgence;
    private AgenceAdapter agenceAdapter;
    private AgenceAdapter.OnAgenceListner onAgenceListner;
    TwoViewModel twoViewModel;

    NetworkInfo networkInfo;
    ConnectivityManager connectivityManager;


    public AgenceFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (container!=null){
            container.removeAllViews();
        }

        View view = inflater.inflate(R.layout.fragment_agence, container, false);
        MainActivity.showProgress();
        recyclerAgence = view.findViewById(R.id.recyclerAgence);
        listAgence = new ArrayList<>();
    agenceAdapter = new AgenceAdapter(listAgence,this.getActivity(),onAgenceListner,getFragmentManager());
        agenceAdapter.setOnAgenceListner(new AgenceAdapter.OnAgenceListner() {
            @Override
            public void onClickAgence(int position) {

            }
        });
        recyclerAgence.setAdapter(agenceAdapter);
        recyclerAgence.setLayoutManager(new LinearLayoutManager(getActivity()));
        twoViewModel = ViewModelProviders.of(this).get(TwoViewModel.class);
        connectivityManager= (ConnectivityManager) getActivity().getSystemService(CONNECTIVITY_SERVICE);
        networkInfo=connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()){
        twoViewModel.getAgences();
        twoViewModel.agenceMutableLiveData.observe(this, new Observer<List<Agence>>() {
            @Override
            public void onChanged(List<Agence> agences) {
                listAgence = agences;
                agenceAdapter.setAgenceList(listAgence);
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