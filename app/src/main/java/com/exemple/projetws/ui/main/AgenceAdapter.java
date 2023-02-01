package com.exemple.projetws.ui.main;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.exemple.projetws.MapFragment;
import com.exemple.projetws.R;
import com.exemple.projetws.model.Agence;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.content.Context.CONNECTIVITY_SERVICE;

public class AgenceAdapter extends RecyclerView.Adapter<AgenceAdapter.agenceViewHolder> {
    private List<Agence> list = new ArrayList<Agence>();
    private Context context;
    private OnAgenceListner onAgenceListner;
    private FragmentManager fragmentManager;
    NetworkInfo networkInfo;
    ConnectivityManager connectivityManager;

    public AgenceAdapter(List<Agence> list, Context context, OnAgenceListner onAgenceListner,FragmentManager fragmentManager) {
        this.list = list;
        this.context = context;
        this.onAgenceListner = onAgenceListner;
        this.fragmentManager=fragmentManager;
    }

    public AgenceAdapter() {
    }

    @NonNull
    @Override
    public agenceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new agenceViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_agence, parent, false), onAgenceListner);
    }

    @Override
    public void onBindViewHolder(@NonNull agenceViewHolder holder, int position) {
        holder.txtadresse.setText(list.get(position).getAdresse());
        holder.txtprix.setText(list.get(position).getPrix());
        holder.txtnom.setText(list.get(position).getNom());
        holder.txtappel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setData(Uri.parse("tel:"+list.get(position).getNumero()));
                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
                        ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CALL_PHONE},1);
                    }else{
                        context.startActivity(intent);
                    }



            }
        });
        holder.imageAgence.setImageResource(R.drawable.voiture);
        holder.imageAgence.setScaleType(ImageView.ScaleType.CENTER);
        holder.txtnumero.setText(list.get(position).getNumero());
        holder.agenceLocation.setOnClickListener(new View.OnClickListener() {
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
                mapsFragment.show(fragmentManager,"mapAgence");
                }else{
                    Toast.makeText(context,"connexion perdu",Toast.LENGTH_SHORT).show();
                }
            }
        });
        holder.btnV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] tabV = list.get(position).getModelVehicule().split(" ,");
                List<String> s = new ArrayList<>();


                for (String ss :tabV){
                    s.add(ss);
                }
            createDialog(s);
            }
        });
    }

    void createDialog(List<String> list){
        ListView car = new ListView(context);
        car.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        car.setBackgroundResource(R.drawable.back_banck);

        ArrayAdapter adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1,android.R.id.text1, list){

            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View v =super.getView(position, convertView, parent);
                TextView textView= (TextView) v.findViewById(android.R.id.text1);
                textView.setGravity(Gravity.CENTER);
                textView.setTextColor(Color.parseColor("#000000"));
                return v;
            }
        };
        car.setAdapter(adapter);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(true);
        builder.setView(car);
        builder.create().show();

    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class agenceViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txtadresse,txtprix,txtnumero,txtnom,txtappel;
        ImageView agenceLocation,imageAgence;
        ImageButton btnV;
        OnAgenceListner onAgenceListner;

        public agenceViewHolder(@NonNull View itemView, OnAgenceListner onAgenceListner) {
            super(itemView);
            txtadresse = itemView.findViewById(R.id.adresse);
            txtnom = itemView.findViewById(R.id.nom);
            txtnumero = itemView.findViewById(R.id.numero);
            txtappel = itemView.findViewById(R.id.appeler);
            txtprix = itemView.findViewById(R.id.prix);
            btnV = itemView.findViewById(R.id.btnvehicule);
            agenceLocation = itemView.findViewById(R.id.agence_location);
            imageAgence = itemView.findViewById(R.id.imageAgence);

            this.onAgenceListner = onAgenceListner;
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            onAgenceListner.onClickAgence(getAdapterPosition());
        }
    }

    public void setAgenceList(List<Agence> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void setOnAgenceListner(OnAgenceListner onAgenceListner) {
        this.onAgenceListner = onAgenceListner;
    }

    public interface OnAgenceListner {
        public void onClickAgence(int position);
    }

}
