package com.exemple.projetws;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.exemple.projetws.ui.main.MainActivity;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import static android.content.Context.CONNECTIVITY_SERVICE;


public class
BottDialogFragment extends BottomSheetDialogFragment {

    LinearLayout laytaxi,layagence,laybus;
    NetworkInfo networkInfo;
    ConnectivityManager connectivityManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_bott_dialog, container, false);
        laybus = view.findViewById(R.id.laybus);
        layagence = view.findViewById(R.id.layagence);
        laytaxi = view.findViewById(R.id.laytaxi);
        laybus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connectivityManager= (ConnectivityManager) getActivity().getSystemService(CONNECTIVITY_SERVICE);
                networkInfo=connectivityManager.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()){
                    HomeFragment.handler.removeCallbacks(HomeFragment.update);
                    FragmentTransaction ft =getFragmentManager().beginTransaction();
                ft.replace(R.id.fragment, new BusFragment(), "bus");
                ft.addToBackStack("bus").commit();
                dismiss(); }else{
                    Toast.makeText(getActivity(),"connexion perdu",Toast.LENGTH_SHORT).show();
                }
            }
        });
        laytaxi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    connectivityManager= (ConnectivityManager) getActivity().getSystemService(CONNECTIVITY_SERVICE);
                    networkInfo=connectivityManager.getActiveNetworkInfo();
                    if (networkInfo != null && networkInfo.isConnected()){
                        HomeFragment.handler.removeCallbacks(HomeFragment.update);

                        FragmentTransaction ft =getFragmentManager().beginTransaction();
                ft.replace(R.id.fragment, new TaxiFragment(), "taxi");
                ft.addToBackStack("taxi").commit();
                dismiss(); }else{
                        Toast.makeText(getActivity(),"connexion perdu",Toast.LENGTH_SHORT).show();
                    }

            }
        });
        layagence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connectivityManager= (ConnectivityManager) getActivity().getSystemService(CONNECTIVITY_SERVICE);
                networkInfo=connectivityManager.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()){
                    HomeFragment.handler.removeCallbacks(HomeFragment.update);
                    FragmentTransaction ft =getFragmentManager().beginTransaction();
                ft.replace(R.id.fragment, new AgenceFragment(), "agence");
                ft.addToBackStack("agence").commit();
                dismiss();
                }else{
                    Toast.makeText(getActivity(),"connexion perdu",Toast.LENGTH_SHORT).show();
                }

            }
        });
        setCancelable(true);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return view;
    }
}