package com.exemple.projetws.ui;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.exemple.projetws.MapFragment;
import com.exemple.projetws.R;
import com.exemple.projetws.model.SiteTouristique;
import com.exemple.projetws.ui.main.ImageAdapter;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.CONNECTIVITY_SERVICE;

public class SiteAdapter extends RecyclerView.Adapter<SiteAdapter.SiteViewHolder> {
    private List<SiteTouristique> list = new ArrayList<SiteTouristique>();
    private Context context;
    private OnSiteListner onSiteListner;
    private FragmentManager fragmentManager;
    NetworkInfo networkInfo;
    ConnectivityManager connectivityManager;

    public SiteAdapter(List<SiteTouristique> list, Context context, OnSiteListner onSiteListner,FragmentManager fragmentManager) {
        this.list = list;
        this.context = context;
        this.onSiteListner = onSiteListner;
        this.fragmentManager=fragmentManager;
    }

    public SiteAdapter() {
    }

    @NonNull
    @Override
    public SiteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SiteViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.site_item, parent, false), onSiteListner);
    }

    @Override
    public void onBindViewHolder(@NonNull SiteViewHolder holder, int position) {
        ImageAdapter imageAdapter = new ImageAdapter(context,fragmentManager);
        holder.viewPager.setAdapter(imageAdapter);
        imageAdapter.setUrls(list.get(position).getImage());
        holder.ts.setText(list.get(position).getNom());
        holder.tsdesc.setText(list.get(position).getDescription());
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
                mapsFragment.show(fragmentManager,"mapsite");
                }else{
                    Toast.makeText(context,"connexion perdu",Toast.LENGTH_SHORT).show();
                }
            }
        });
        holder.viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int positionim, float positionOffset, int positionOffsetPixels) {
                holder.txtnum.setText(positionim+1+"/"+list.get(position).getImage().size());
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

    public class SiteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView ts,tsdesc,txtnum;
        ImageView imageView;
        ViewPager viewPager;
        OnSiteListner onSiteListner;

        public SiteViewHolder(@NonNull View itemView, OnSiteListner onSiteListner) {
            super(itemView);
            viewPager = itemView.findViewById(R.id.scrollimages);
            ts = itemView.findViewById(R.id.holtel_name);
            tsdesc = itemView.findViewById(R.id.site_decsc);
            txtnum = itemView.findViewById(R.id.txtnum);
            imageView = itemView.findViewById(R.id.site_location);
            this.onSiteListner = onSiteListner;
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            onSiteListner.onClickSite(getAdapterPosition());
        }
    }

    public void setList(List<SiteTouristique> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void setOnSiteListner(OnSiteListner onSiteListner) {
        this.onSiteListner = onSiteListner;
    }

    public interface OnSiteListner {
        public void onClickSite(int position);
    }

}
