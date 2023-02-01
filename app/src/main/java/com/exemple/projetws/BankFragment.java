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
import com.exemple.projetws.model.SiteTouristique;
import com.exemple.projetws.ui.BanqueAdapter;
import com.exemple.projetws.ui.SiteAdapter;
import com.exemple.projetws.ui.main.MainActivity;
import com.exemple.projetws.ui.main.OneViewModel;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.CONNECTIVITY_SERVICE;


public class BankFragment extends Fragment {
    RecyclerView recyclerBank;
    BanqueAdapter banqueAdapter;
    private List<Banque> listBank;
    private BanqueAdapter.OnBanqueListner onBanqueListner;
    OneViewModel oneViewModel;
    NetworkInfo networkInfo;
    ConnectivityManager connectivityManager;

    public BankFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (container!=null){
            container.removeAllViews();
        }

            View view = inflater.inflate(R.layout.fragment_bank, container, false);
        MainActivity.showProgress();
        recyclerBank = view.findViewById(R.id.recyclerBank);
        listBank = new ArrayList<>();
        banqueAdapter = new BanqueAdapter(listBank,getActivity(),onBanqueListner,getFragmentManager());
        banqueAdapter.setOnBanqueListner(new BanqueAdapter.OnBanqueListner() {
            @Override
            public void onClickBanque(int position) {

            }
        });
        recyclerBank.setAdapter(banqueAdapter);
        recyclerBank.setLayoutManager(new LinearLayoutManager(getActivity()));
        oneViewModel = ViewModelProviders.of(this).get(OneViewModel.class);
        connectivityManager= (ConnectivityManager) getActivity().getSystemService(CONNECTIVITY_SERVICE);
        networkInfo=connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()){
        oneViewModel.getBanques();
        oneViewModel.banqueMutableLiveData.observe(this, new Observer<List<Banque>>() {
            @Override
            public void onChanged(List<Banque> banques) {
                listBank= banques;
                banqueAdapter.setBanqueList(listBank);
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