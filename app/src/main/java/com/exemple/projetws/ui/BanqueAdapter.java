package com.exemple.projetws.ui;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
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
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.exemple.projetws.MapFragment;
import com.exemple.projetws.R;
import com.exemple.projetws.model.Banque;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.CONNECTIVITY_SERVICE;

public class BanqueAdapter extends RecyclerView.Adapter<BanqueAdapter.BanqueViewHolder> {
    private List<Banque> list = new ArrayList<Banque>();
    private Context context;
    private OnBanqueListner onBanqueListner;
    private FragmentManager fragmentManager;
    NetworkInfo networkInfo;
    ConnectivityManager connectivityManager;

    public BanqueAdapter(List<Banque> list, Context context, OnBanqueListner onBanqueListner,FragmentManager fragmentManager) {
        this.list = list;
        this.context = context;
        this.onBanqueListner = onBanqueListner;
        this.fragmentManager=fragmentManager;
    }

    public BanqueAdapter() {
    }

    @NonNull
    @Override
    public BanqueViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BanqueViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bank, parent, false), onBanqueListner);
    }

    @Override
    public void onBindViewHolder(@NonNull BanqueViewHolder holder, int position) {
        Glide.with(context).load(list.get(position).getImage()).timeout(60000).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                return false;
            }
        }).into(holder.imageViewBanque);
        holder.txtnom.setText(list.get(position).getNom());
        holder.txtappel.setOnClickListener(new View.OnClickListener() {
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
        holder.txttel.setText(list.get(position).getTelephone());
        holder.txtadresse.setText(list.get(position).getAdresse());
        holder.imageViewLocation.setOnClickListener(new View.OnClickListener() {
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
                mapsFragment.show(fragmentManager,"mapbank");
            }else{
                Toast.makeText(context,"connexion perdu",Toast.LENGTH_SHORT).show();
            }

            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class BanqueViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txttel,txtnom,txtadresse,txtappel;
        ImageView imageViewBanque,imageViewLocation;

        OnBanqueListner onBanqueListner;

        public BanqueViewHolder(@NonNull View itemView, OnBanqueListner onBanqueListner) {
            super(itemView);
            txtnom = itemView.findViewById(R.id.namebank);
            txtadresse = itemView.findViewById(R.id.adressebank);
            txttel = itemView.findViewById(R.id.telbank);
            txtappel = itemView.findViewById(R.id.appeler);

            imageViewBanque = itemView.findViewById(R.id.imagebank);
            imageViewLocation= itemView.findViewById(R.id.bank_location);
            this.onBanqueListner = onBanqueListner;
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            onBanqueListner.onClickBanque(getAdapterPosition());
        }
    }

    public void setBanqueList(List<Banque> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void setOnBanqueListner(OnBanqueListner onBanqueListner) {
        this.onBanqueListner = onBanqueListner;
    }

    public interface OnBanqueListner {
        public void onClickBanque(int position);
    }

}
