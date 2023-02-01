package com.exemple.projetws;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.exemple.projetws.model.Hotel;
import com.exemple.projetws.ui.HotelAdapter;
import com.exemple.projetws.ui.main.ImageAdapter;
import com.exemple.projetws.ui.main.MainActivity;
import com.exemple.projetws.ui.main.OneViewModel;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static android.content.Context.CONNECTIVITY_SERVICE;

public class HomeFragment extends Fragment {
    ViewPager hsv;
    List<String> urls ;
    ImageAdapter imageAdapter;
    private RelativeLayout layrestau,laytrans,layhotel,laybanque,laysite;
    Button btntouver;
    boolean check = false;
    TextView tvCity,tvTemp,tvdesc;
    public static final Handler handler = new Handler();
    public static Runnable update;
    ImageView iconWeather;
    int currentPageCunter = 0 ;
    NetworkInfo networkInfo;
    ConnectivityManager connectivityManager;
    public HomeFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (container != null) {
            container.removeAllViews();

        }
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        connectivityManager= (ConnectivityManager) getActivity().getSystemService(CONNECTIVITY_SERVICE);
        networkInfo=connectivityManager.getActiveNetworkInfo();
        List<String> urls = new ArrayList<>();
        if (networkInfo != null && networkInfo.isConnected()){
            urls.add("https://tinyurl.com/yzey27km");
            urls.add("https://tinyurl.com/ye6xwgrl");
            urls.add("https://tinyurl.com/yfghtsfs");
            urls.add("https://tinyurl.com/ye6xwgrl");
            try {
                find_weather();
            }catch (Exception e){

            }
        }else{
            Toast.makeText(getActivity(),"connexion perdu",Toast.LENGTH_SHORT).show();
        }


        imageAdapter = new ImageAdapter(getContext(),getFragmentManager());
        layrestau = view.findViewById(R.id.layrestau);
        laybanque = view.findViewById(R.id.laybanques);
        hsv = view.findViewById(R.id.scrollimages);
        laysite = view.findViewById(R.id.laysite);
        layhotel = view.findViewById(R.id.layhotel);
        laytrans = view.findViewById(R.id.laytrans);
        iconWeather = (ImageView) view.findViewById(R.id.iconWeather);
        tvTemp = (TextView) view.findViewById(R.id.tvTemp);
        tvCity = (TextView) view.findViewById(R.id.tvCity);
        tvdesc = (TextView) view.findViewById(R.id.tvdesc);
        btntouver = view.findViewById(R.id.btntrouver);
        btntouver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connectivityManager= (ConnectivityManager) getActivity().getSystemService(CONNECTIVITY_SERVICE);
                networkInfo=connectivityManager.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()){
                    handler.removeCallbacks(update);
                    GuidesFragment guidesFragment = new GuidesFragment();
                    openFragment(guidesFragment, guidesFragment.getClass().getSimpleName());
                }else{
                    Toast.makeText(getActivity(),"connexion perdu",Toast.LENGTH_SHORT).show();
                }


            }
        });




        imageAdapter.setUrls(urls);
        hsv.setAdapter(imageAdapter);
        update  = new Runnable() {
            @Override
            public void run() {

                if (currentPageCunter == urls.size()){
                    check= false;


                }
                if (currentPageCunter == 0){
                    check= true;
                }
                if (check){
                    hsv.setCurrentItem(currentPageCunter++,true);
                }else {
                    hsv.setCurrentItem(currentPageCunter--,true);

                }
                System.out.println("slide "+currentPageCunter);
                handler.postDelayed(this,4000);
            }
        };


        layhotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connectivityManager= (ConnectivityManager) getActivity().getSystemService(CONNECTIVITY_SERVICE);
                networkInfo=connectivityManager.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()){
                handler.removeCallbacks(update);
                HotelFragment hotelFragment = new HotelFragment();
                openFragment(hotelFragment, hotelFragment.getClass().getSimpleName());
            }else{
                    Toast.makeText(getActivity(),"connexion perdu",Toast.LENGTH_SHORT).show();
                }}
        });
        layrestau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connectivityManager= (ConnectivityManager) getActivity().getSystemService(CONNECTIVITY_SERVICE);
                networkInfo=connectivityManager.getActiveNetworkInfo();

                if (networkInfo != null && networkInfo.isConnected()){
                handler.removeCallbacks(update);
                RestaurantFragment restaurantFragment = new RestaurantFragment();
                openFragment(restaurantFragment, restaurantFragment.getClass().getSimpleName());}else{
                    Toast.makeText(getActivity(),"connexion perdu",Toast.LENGTH_SHORT).show();
                }
            }
        });
        laysite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connectivityManager= (ConnectivityManager) getActivity().getSystemService(CONNECTIVITY_SERVICE);
                networkInfo=connectivityManager.getActiveNetworkInfo();

                if (networkInfo != null && networkInfo.isConnected()){
                handler.removeCallbacks(update);
                SiteFragment siteFragment = new SiteFragment();
                openFragment(siteFragment, siteFragment.getClass().getSimpleName());
            }else{
                    Toast.makeText(getActivity(),"connexion perdu",Toast.LENGTH_SHORT).show();
                }}
        });
        laybanque.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connectivityManager= (ConnectivityManager) getActivity().getSystemService(CONNECTIVITY_SERVICE);
                networkInfo=connectivityManager.getActiveNetworkInfo();

                if (networkInfo != null && networkInfo.isConnected()){
                handler.removeCallbacks(update);
                BankFragment bankFragment = new BankFragment();
                openFragment(bankFragment, bankFragment.getClass().getSimpleName());}else{
                    Toast.makeText(getActivity(),"connexion perdu",Toast.LENGTH_SHORT).show();
                }
            }
        });
        laytrans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connectivityManager= (ConnectivityManager) getActivity().getSystemService(CONNECTIVITY_SERVICE);
                networkInfo=connectivityManager.getActiveNetworkInfo();

                if (networkInfo != null && networkInfo.isConnected()){
                BottDialogFragment bottDialogFragment = new BottDialogFragment();

                bottDialogFragment.show(getFragmentManager(), "bottDialog");
                }else{
                    Toast.makeText(getActivity(),"connexion perdu",Toast.LENGTH_SHORT).show();
                }

            }
        });
        update.run();
        return view;
    }



    public void openFragment(Fragment fragment,String tag ){
          FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
          fragmentTransaction.replace(R.id.fragment,fragment,tag);
        fragmentTransaction.addToBackStack(tag).commit();
    }
    private void find_weather() {
        Ion.with(this)
                .load("http://api.openweathermap.org/data/2.5/weather?q=bejaia%2Cdz&appid=22fe4650ee94ad7dbb1cb6b85592c437&units=Imperial&lang=fr&fbclid=IwAR0xCb9OczGwWoTvgLB74zBuXfxFiLqWUibK9DTA99lPy-of9Qwb7NfSkpI")
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        // do stuff with the result or error
                        if(e !=null){
                            e.printStackTrace();
                        }else {
                            //convert json convert to java
                            //Recupération de la température
                            JsonObject main=result.get("main").getAsJsonObject();
                            double temp=main.get("temp").getAsDouble();
                            double temp_int=Double.parseDouble(String.valueOf(temp));
                            double centi=(temp_int -32) /1.8000;
                            centi=Math.round(centi);
                            int i=(int)centi;
                            tvTemp.setText(String.valueOf(i)+" °C");
                            //Récupération de la ville
                            String city =result.get("name").getAsString();
                            JsonObject sys=result.get("sys").getAsJsonObject();
                            String country=sys.get("country").getAsString();
                            tvCity.setText(city+" , "+country);

                            //Récupération de l'image
                            try {
                                JsonArray weather=result.get("weather").getAsJsonArray();
                                String desc=weather.get(0).getAsJsonObject().get("description").getAsString().toUpperCase();
                                tvdesc.setText(desc);
                                String icon=weather.get(0).getAsJsonObject().get("icon").getAsString();
                                loadIcon(icon);

                            }catch (Exception d){
                                d.printStackTrace();
                            }

                        }
                    }
                });
    }

    private void loadIcon(String icon) {
        Glide.with(this)
                .load("https://openweathermap.org/img/w/"+icon+".png").timeout(60000)
                .into(iconWeather);
    }
}