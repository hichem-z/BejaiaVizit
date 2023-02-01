package com.exemple.projetws;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.exemple.projetws.model.Hotel;
import com.exemple.projetws.ui.HotelAdapter;
import com.exemple.projetws.ui.main.ImageAdapter;
import com.exemple.projetws.ui.main.MainActivity;
import com.exemple.projetws.ui.main.OneViewModel;
import com.google.android.gms.maps.MapView;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.CONNECTIVITY_SERVICE;


public class HotelDetFragment extends Fragment {
    private OneViewModel oneViewModel;
    TextView txtdesc,txttel,txtadresse,txtnom,txtlocalisation;
    ImageView imageHotel,callhotel;
    ViewPager hsv;
    NetworkInfo networkInfo;

    ConnectivityManager connectivityManager;
    HotelAdapter.OnHotelListner onHotelListner;
    TextView txtnum;


    ImageAdapter imageAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (container != null) {
            container.removeAllViews();
        }
       View view = inflater.inflate(R.layout.fragment_hotel_det, container, false);
        MainActivity.showProgress();
        txtadresse = view.findViewById(R.id.txtadresse);
        txtnom = view.findViewById(R.id.txtnom);
        txttel = view.findViewById(R.id.txttel);
        txtdesc = view.findViewById(R.id.txtdesc);
        hsv = view.findViewById(R.id.scrollimages);

        imageHotel = view.findViewById(R.id.imegecover);
        callhotel = view.findViewById(R.id.call_hotel);
        callhotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               try {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:"+txttel.getText().toString()));
                   if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
                       ActivityCompat.requestPermissions((Activity) getActivity(), new String[]{Manifest.permission.CALL_PHONE},1);
                   }else{
                       startActivity(intent);
                   }
            } catch (Exception e) {
                Log.e("Demo application", "Failed to invoke call", e);
            }
            }
        });
        imageAdapter = new ImageAdapter(getContext(),getFragmentManager());
        txtnum = view.findViewById(R.id.txtnum);
        txtlocalisation = view.findViewById(R.id.txtlocalisation);
        txtlocalisation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connectivityManager= (ConnectivityManager) getActivity().getSystemService(CONNECTIVITY_SERVICE);
                networkInfo=connectivityManager.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {
                Bundle bundle = new Bundle();
                MapFragment mapsFragment = new MapFragment();

//                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                bundle.putFloat("alt", (float) 36.752518);
               bundle.putFloat("long", (float) 5.071240);
//
                mapsFragment.setArguments(bundle);
//                fragmentTransaction.replace(R.id.fragment,mapsFragment,"map");
//                fragmentTransaction.addToBackStack("map").commit();
               mapsFragment.show(getFragmentManager(),"hdf");
                }else{
                    Toast.makeText(getActivity(),"connexion perdu",Toast.LENGTH_SHORT).show();
                }
            }
        });

        Bundle bundle = this.getArguments();
        oneViewModel = ViewModelProviders.of(this).get(OneViewModel.class);
        if (bundle!=null){

            connectivityManager= (ConnectivityManager) getActivity().getSystemService(CONNECTIVITY_SERVICE);
            networkInfo=connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
            oneViewModel.getHotel(bundle.getInt("id_hotel"));
            oneViewModel.hotelMutableLiveData.observe(this, new Observer<Hotel>() {
                @Override
                public void onChanged(Hotel hotel) {
                    txtnom.setText(hotel.getNom());
                    txtdesc.setText(hotel.getDescription());
                    txtadresse.setText(hotel.getAdresse());
                    txttel.setText(hotel.getTelephone());
                            Glide.with(getContext()).
                                    load(hotel.getImageCover()).listener(new RequestListener<Drawable>() {
                                @Override
                                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                    return false;
                                }
                            }).listener(new RequestListener<Drawable>() {
                                @Override
                                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                    return false;
                                }
                            }).timeout(60000).
                                    into(imageHotel);
                            hsv.setAdapter(imageAdapter);
                            imageAdapter.setUrls(hotel.getImages());

                            hsv.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                                @Override
                                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                                    txtnum.setText(position+1+"/"+hotel.getImage().size());
                                }

                                @Override
                                public void onPageSelected(int position) {

                                }

                                @Override
                                public void onPageScrollStateChanged(int state) {

                                }
                            });
//                            LinearLayout linearLayout  = new LinearLayout(getContext());
//                         linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
//                        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
//                    for (String s : hotel.getImage()){
//                                ImageView i = new ImageView(getContext());
////                                i.setImageResource(R.drawable.h1);
//                                i.setMaxWidth(100);
//                                i.setMaxHeight(100);
//
//                                Glide.with(getContext()).
//                                        load(s).
//                                        into(i);
//                                linearLayout.addView(i);
//                        System.out.println(s);
//                            }
//
//                    hsv.addView(linearLayout);
                    MainActivity.progressDialog.dismiss();

                }
            });
            }else{
                MainActivity.progressDialog.dismiss();

                Toast.makeText(getActivity(),"connexion perdu",Toast.LENGTH_SHORT).show();
            }
       }
        return view;
    }
}