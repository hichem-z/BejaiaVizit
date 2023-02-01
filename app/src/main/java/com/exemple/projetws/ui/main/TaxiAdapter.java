package com.exemple.projetws.ui.main;

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

import com.exemple.projetws.MapFragment;
import com.exemple.projetws.R;
import com.exemple.projetws.model.Taxi;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.CONNECTIVITY_SERVICE;

public class TaxiAdapter extends RecyclerView.Adapter<TaxiAdapter.TaxiViewHolder> {
    private List<Taxi> list = new ArrayList<Taxi>();
    private Context context;
    private OnTaxiListner onTaxiListner;
    private FragmentManager fragmentManager;
    NetworkInfo networkInfo;
    ConnectivityManager connectivityManager;

    public TaxiAdapter(List<Taxi> list, Context context, OnTaxiListner onTaxiListner,FragmentManager fragmentManager) {
        this.list = list;
        this.context = context;
        this.onTaxiListner = onTaxiListner;
        this.fragmentManager= fragmentManager;
    }

    public TaxiAdapter() {

    }

    @NonNull
    @Override
    public TaxiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TaxiViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.taxi_item, parent, false), onTaxiListner);
    }

    @Override
    public void onBindViewHolder(@NonNull TaxiViewHolder holder, int position) {
                holder.txtadresse.setText(list.get(position).getAdresse());
                holder.txtprix.setText(list.get(position).getPrix());
        holder.icone.setImageResource(R.drawable.taxi);
        holder.taxiLocation.setOnClickListener(new View.OnClickListener() {
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

    public class TaxiViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txtadresse,txtprix;
        OnTaxiListner onTaxiListner;
        ImageView icone, taxiLocation;

        public TaxiViewHolder(@NonNull View itemView, OnTaxiListner onTaxiListner) {
            super(itemView);
            txtadresse = itemView.findViewById(R.id.adressetaxi);
            txtprix = itemView.findViewById(R.id.prixtaxi);
            icone = itemView.findViewById(R.id.imagetaxi);
            taxiLocation = itemView.findViewById(R.id.taxi_location);
            this.onTaxiListner = onTaxiListner;
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            onTaxiListner.onClickTaxi(getAdapterPosition());
        }
    }

    public void setTaxiList(List<Taxi> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void setOnTaxiListner(OnTaxiListner onTaxiListner) {
        this.onTaxiListner = onTaxiListner;
    }

    public interface OnTaxiListner {
        public void onClickTaxi(int position);
    }

}
