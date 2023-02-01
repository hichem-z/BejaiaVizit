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

import com.exemple.projetws.model.SiteTouristique;
import com.exemple.projetws.ui.SiteAdapter;
import com.exemple.projetws.ui.main.MainActivity;
import com.exemple.projetws.ui.main.OneViewModel;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.CONNECTIVITY_SERVICE;

public class SiteFragment extends Fragment {

    RecyclerView recyclerSite;
    SiteAdapter siteAdapter;
    private List<SiteTouristique> listSite;
    private SiteAdapter.OnSiteListner onSiteListner;
    OneViewModel oneViewModel;

    NetworkInfo networkInfo;
    ConnectivityManager connectivityManager;

    public SiteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (container!=null){
            container.removeAllViews();
        }

        View view = inflater.inflate(R.layout.fragment_site, container, false);
        MainActivity.showProgress();
        recyclerSite = view.findViewById(R.id.recyclerSite);
        listSite = new ArrayList<>();
        siteAdapter = new SiteAdapter(listSite,getActivity().getApplicationContext(),onSiteListner,getFragmentManager());
        siteAdapter.setOnSiteListner(new SiteAdapter.OnSiteListner() {
            @Override
            public void onClickSite(int position) {

            }
        });
        recyclerSite.setAdapter(siteAdapter);
        recyclerSite.setLayoutManager(new LinearLayoutManager(getActivity()));
        oneViewModel = ViewModelProviders.of(this).get(OneViewModel.class);
        connectivityManager= (ConnectivityManager) getActivity().getSystemService(CONNECTIVITY_SERVICE);
        networkInfo=connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()){
        oneViewModel.getSites();
        oneViewModel.siteMutableLiveData.observe(this, new Observer<List<SiteTouristique>>() {
            @Override
            public void onChanged(List<SiteTouristique> siteTouristiques) {
                listSite= siteTouristiques;
                siteAdapter.setList(listSite);
                MainActivity.progressDialog.dismiss();

            }
        });
        }else{
            MainActivity.progressDialog.dismiss();

            Toast.makeText(getActivity(),"connexion perdu",Toast.LENGTH_SHORT).show();
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

}