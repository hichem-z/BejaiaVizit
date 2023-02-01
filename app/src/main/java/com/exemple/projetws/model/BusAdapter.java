package com.exemple.projetws.model;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.exemple.projetws.MapFragment;
import com.exemple.projetws.R;

import java.util.ArrayList;
import java.util.List;

public class BusAdapter extends RecyclerView.Adapter<BusAdapter.BusViewHolder> {
    private List<Bus> list = new ArrayList<Bus>();
    private Context context;
    private OnBusListner onBusListner;
    private FragmentManager fragmentManager;
    public BusAdapter(List<Bus> list, Context context, OnBusListner onBusListner,FragmentManager fragmentManager) {
        this.list = list;
        this.context = context;
        this.onBusListner = onBusListner;
        this.fragmentManager= fragmentManager;
    }

    public BusAdapter() {    }

    @NonNull
    @Override
    public BusViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BusViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bus, parent, false), onBusListner);
    }

    @Override
    public void onBindViewHolder(@NonNull BusViewHolder holder, int position) {
        holder.idligne.setText(list.get(position).getIdligne());
        holder.txtdepart.setText(list.get(position).getDepart());
        holder.txtarrive.setText(list.get(position).getArrive());
        holder.imageBus.setImageResource(R.drawable.bus);
        holder.imageBus.setScaleType(ImageView.ScaleType.CENTER);
        holder.imagedep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                MapFragment mapsFragment = new MapFragment();
                bundle.putFloat("alt", list.get(position).getX());
                bundle.putFloat("long", list.get(position).getY());
                mapsFragment.setArguments(bundle);
                mapsFragment.show(fragmentManager,"mapBus");
            }
        });
        holder.imageArrive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                MapFragment mapsFragment = new MapFragment();
                bundle.putFloat("alt", list.get(position).getA());
                bundle.putFloat("long", list.get(position).getB());
                mapsFragment.setArguments(bundle);
                mapsFragment.show(fragmentManager,"mapBus");
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class BusViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView idligne,txtdepart,txtarrive;
        ImageView imagedep,imageArrive,imageBus;
        OnBusListner onBusListner;

        public BusViewHolder(@NonNull View itemView, OnBusListner onBusListner) {
            super(itemView);
             idligne= itemView.findViewById(R.id.numbus);
            txtdepart = itemView.findViewById(R.id.txtdepart);
            txtarrive = itemView.findViewById(R.id.txtarrive);
            imagedep = itemView.findViewById(R.id.imagedepart);
            imageArrive = itemView.findViewById(R.id.imagearrive);
            imageBus = itemView.findViewById(R.id.imageBus);
            this.onBusListner = onBusListner;
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            onBusListner.onClickBus(getAdapterPosition());
        }
    }

    public void setBuslList(List<Bus> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void setOnBusListner(OnBusListner onBusListner) {
        this.onBusListner = onBusListner;
    }

    public interface OnBusListner {
        public void onClickBus(int position);
    }

}
