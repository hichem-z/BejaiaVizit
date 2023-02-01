package com.exemple.projetws.ui;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.exemple.projetws.ImageFragment;
import com.exemple.projetws.R;
import com.exemple.projetws.model.Guide;

import java.util.ArrayList;
import java.util.List;

public class GuideAdapter extends RecyclerView.Adapter<GuideAdapter.GuideViewHolder> {
    private List<Guide> list = new ArrayList<Guide>();
    private Context context;
    private FragmentManager fragmentManager;
    private OnGuideListner onGuideListner;

    public GuideAdapter(List<Guide> list, Context context, OnGuideListner onGuideListner, FragmentManager fragmentManager) {
        this.list = list;
        this.context = context;
        this.onGuideListner = onGuideListner;
        this.fragmentManager=fragmentManager;
    }

    public GuideAdapter() {
    }

    @NonNull
    @Override
    public GuideViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new GuideViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_guide, parent, false), onGuideListner);
    }

    @Override
    public void onBindViewHolder(@NonNull GuideViewHolder holder, int position) {
        Glide.with(context).load(list.get(position).getPhoto()).timeout(60000).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                return false;
            }
        }).into(holder.imageView);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageFragment imageFragment = new ImageFragment(list.get(position).getPhoto(),list.get(position).getPrenom(),list.get(position).getNom());
             imageFragment.show(fragmentManager,"image");
            }
        });
        holder.txtns.setText(list.get(position).getNom()+" "+list.get(position).getPrenom()+" ("+list.get(position).getAge()+" ans)");
        holder.txttel.setText(list.get(position).getTelephone());
        holder.txtemail.setText(list.get(position).getEmail());
        holder.txtdesc.setText(list.get(position).getDescription());
        holder.callguide.setOnClickListener(new View.OnClickListener() {
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
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class GuideViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txtns,txttel,txtemail,txtdesc;
        ImageView imageView,callguide;
        OnGuideListner onGuideListner;

        public GuideViewHolder(@NonNull View itemView, OnGuideListner onGuideListner) {
            super(itemView);
            txtns = itemView.findViewById(R.id.lastfirstname);
            txttel = itemView.findViewById(R.id.tel);
            txtemail = itemView.findViewById(R.id.email);
            txtdesc = itemView.findViewById(R.id.desc);
            imageView = itemView.findViewById(R.id.imageGuide);
            callguide = itemView.findViewById(R.id.callguide);
            this.onGuideListner = onGuideListner;
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            onGuideListner.onClickGuide(getAdapterPosition());
        }
    }

    public void setList(List<Guide> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void setOnGuideListner(OnGuideListner onGuideListner) {
        this.onGuideListner = onGuideListner;
    }

    public interface OnGuideListner {
        public void onClickGuide(int position);
    }

}
