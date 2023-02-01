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

import com.exemple.projetws.model.Guide;
import com.exemple.projetws.model.Taxi;
import com.exemple.projetws.ui.GuideAdapter;
import com.exemple.projetws.ui.main.MainActivity;
import com.exemple.projetws.ui.main.OneViewModel;
import com.exemple.projetws.ui.main.TaxiAdapter;
import com.exemple.projetws.ui.main.TwoViewModel;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.CONNECTIVITY_SERVICE;


public class GuidesFragment extends Fragment {
    RecyclerView recyclerGuides;
    private List<Guide> listguides;
    NetworkInfo networkInfo;
    ConnectivityManager connectivityManager;
    private GuideAdapter guideAdapter;
    private GuideAdapter.OnGuideListner onGuideListner;
    OneViewModel oneViewModel;
    public GuidesFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (container!=null){
            container.removeAllViews();
        }

        View view = inflater.inflate(R.layout.fragment_guides, container, false);
        MainActivity.showProgress();
        recyclerGuides = view.findViewById(R.id.recyclerGuides);
        listguides = new ArrayList<>();
        guideAdapter = new GuideAdapter(listguides,getActivity(),onGuideListner,getFragmentManager());
        guideAdapter.setOnGuideListner(new GuideAdapter.OnGuideListner() {
            @Override
            public void onClickGuide(int position) {

            }
        });
        recyclerGuides.setAdapter(guideAdapter);
        recyclerGuides.setLayoutManager(new LinearLayoutManager(getActivity()));
        oneViewModel = ViewModelProviders.of(this).get(OneViewModel.class);
        connectivityManager= (ConnectivityManager) getActivity().getSystemService(CONNECTIVITY_SERVICE);
        networkInfo=connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()){
        oneViewModel.getGuides();

        oneViewModel.guidesMutableLiveData.observe(this, new Observer<List<Guide>>() {
            @Override
            public void onChanged(List<Guide> guides) {
                listguides = guides;
                guideAdapter.setList(listguides);
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