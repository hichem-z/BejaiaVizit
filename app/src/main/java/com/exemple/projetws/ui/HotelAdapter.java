package com.exemple.projetws.ui;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.exemple.projetws.R;
import com.exemple.projetws.model.Hotel;

import java.util.ArrayList;
import java.util.List;

public class HotelAdapter extends RecyclerView.Adapter<HotelAdapter.HotelViewHolder> {
    private List<Hotel> hotelList= new ArrayList<Hotel>();
    private Context context;
    private OnHotelListner onHotelListner;
    NetworkInfo networkInfo;
    ConnectivityManager connectivityManager;

    public HotelAdapter(List<Hotel> hotelList, Context context, OnHotelListner onHotelListner) {
        this.hotelList = hotelList;
        this.context = context;
        this.onHotelListner = onHotelListner;
    }

    public HotelAdapter() {
    }

    @NonNull
    @Override
    public HotelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HotelViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.hotel_item,parent,false),onHotelListner);
    }

    @Override
    public void onBindViewHolder(@NonNull HotelViewHolder holder, int position) {
        holder.th.setText(hotelList.get(position).getNom());
        Glide.with(context).load(hotelList.get(position).getImageCover()).timeout(60000).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                return false;
            }
        }).centerCrop().into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return hotelList.size();
    }

    public class HotelViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView th ;
        ImageView imageView;
        OnHotelListner onHotelListner;

        public HotelViewHolder(@NonNull View itemView,OnHotelListner onHotelListner) {
            super(itemView);
        th = itemView.findViewById(R.id.holtel_name);
        imageView = itemView.findViewById(R.id.imageHotel);
        this.onHotelListner=onHotelListner;
        itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            onHotelListner.onClickHotel(getAdapterPosition());
        }
    }
    public void setHotelList(List<Hotel> hotelList){
        this.hotelList=hotelList;
        notifyDataSetChanged();
    }

    public void setOnHotelListner(OnHotelListner onHotelListner) {
        this.onHotelListner = onHotelListner;
    }

    public interface OnHotelListner{
        public void onClickHotel(int position);
    }

}
