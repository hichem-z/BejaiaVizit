package com.exemple.projetws.ui;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.exemple.projetws.MapFragment;
import com.exemple.projetws.R;
import com.exemple.projetws.model.Restaurant;
import com.exemple.projetws.ui.main.ImageAdapter;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.CONNECTIVITY_SERVICE;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.RestaurantViewModel> {
    private List<Restaurant> list = new ArrayList<Restaurant>();
    private Context context;
    private onRestaurantListner onRestaurantListner;
    private FragmentManager fragmentManager;
    NetworkInfo networkInfo;
    ConnectivityManager connectivityManager;

    public RestaurantAdapter(List<Restaurant> list, Context context, onRestaurantListner onRestaurantListner, FragmentManager fragmentManager) {
        this.list = list;
        this.context = context;
        this.onRestaurantListner = onRestaurantListner;
        this.fragmentManager=fragmentManager;
    }

    public RestaurantAdapter() {
    }

    @NonNull
    @Override
    public RestaurantViewModel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RestaurantViewModel(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_restau, parent, false), onRestaurantListner);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantViewModel holder, int position) {
        ImageAdapter imageAdapter = new ImageAdapter(context,fragmentManager);
        holder.viewPager.setAdapter(imageAdapter);
        List<String> ls = new ArrayList<>();
        ls.add(list.get(position).getUrl1());
        ls.add(list.get(position).getUrl2());
        ls.add(list.get(position).getUrl3());
        ls.add(list.get(position).getUrl4());
        ls.add(list.get(position).getUrl4());
        imageAdapter.setUrls(ls);
        holder.th.setText(list.get(position).getName());

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connectivityManager= (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
                networkInfo=connectivityManager.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()){
                Bundle bundle = new Bundle();
                MapFragment mapsFragment = new MapFragment();
                bundle.putFloat("alt", list.get(position).getX());
                bundle.putFloat("long", list.get(position).getY());
                mapsFragment.setArguments(bundle);
                mapsFragment.show(fragmentManager,"maprestau");  }else{
                    Toast.makeText(context,"connexion perdu",Toast.LENGTH_SHORT).show();
                }
            }
        });
        holder.imagecall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setData(Uri.parse("tel:"+list.get(position).getTelephone()));
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CALL_PHONE},1);
                }else{
                    context.startActivity(intent);
                }
            }
        });
        holder.viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int positionim, float positionOffset, int positionOffsetPixels) {
                holder.txtnum.setText(positionim+1+"/"+ls.size());
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class RestaurantViewModel extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView th,txttel,txtnum;
        ImageView imageView,imagecall;
        ViewPager viewPager;
        onRestaurantListner onRestaurantListner;

        public RestaurantViewModel(@NonNull View itemView, onRestaurantListner onRestaurantListner) {
            super(itemView);
            viewPager = itemView.findViewById(R.id.scrollimages);
            th = itemView.findViewById(R.id.restau_name);
            txtnum = itemView.findViewById(R.id.txtnum);
            imagecall = itemView.findViewById(R.id.restau_call);
            imageView = itemView.findViewById(R.id.restau_location);

            this.onRestaurantListner = onRestaurantListner;
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            onRestaurantListner.onClickRestaurant(getAdapterPosition());
        }
    }

    public void setList(List<Restaurant> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void setonRestaurantListner(onRestaurantListner onRestaurantListner) {
        this.onRestaurantListner = onRestaurantListner;
    }

    public interface onRestaurantListner {
        public void onClickRestaurant(int position);
    }

}
