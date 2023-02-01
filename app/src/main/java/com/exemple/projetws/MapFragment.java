package com.exemple.projetws;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Locale;

public class MapFragment extends DialogFragment implements OnMapReadyCallback {

    private static final String key = "MapViewBundleKey";
    private MapView mapView;
    private Button btngm;
    float alt=0,lon=0;

    public MapFragment() {

    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_map, container, false);
            setCancelable(true);
            btngm = v.findViewById(R.id.btngm);
            btngm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        String uri = String.format("geo:<lat>,<long>?q=<"+alt+">,<"+lon+">");
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                        startActivity(intent);
                    }catch (Exception e){
                        Toast.makeText(getActivity().getApplicationContext(),"application google Map non disponible",Toast.LENGTH_SHORT).show();
                    }

                }
            });
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        Bundle b = getArguments();
           if (b!=null){
               alt = b.getFloat("alt");
               lon = b.getFloat("long");
           }
            Bundle bundle = null;
            if (savedInstanceState!=null){
            bundle = savedInstanceState.getBundle(key);
            }
            mapView = v.findViewById(R.id.mymap);

            mapView.onCreate(bundle);
            mapView.getMapAsync(this);

        return v ;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng markerLoc=new LatLng(alt, lon);
    googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(CameraPosition.builder().target(markerLoc)
            .zoom(15.0f).build()));
            googleMap.addMarker(new MarkerOptions()
                .position(markerLoc)
                .title("Lacation"));


    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Bundle mapBundle = outState.getBundle(key);
        if (mapBundle!=null){
            mapBundle = new Bundle();
            outState.putBundle(key,mapBundle);
        }
        mapView.onSaveInstanceState(mapBundle);

    }

    @Override
    public void onPause() {
        super.onPause();
    mapView.onPause();
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

}