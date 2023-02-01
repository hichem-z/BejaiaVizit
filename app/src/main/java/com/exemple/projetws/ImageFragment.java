package com.exemple.projetws;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

public class ImageFragment extends DialogFragment {

String url;
String nom,prenom;
TextView txt;
    public ImageFragment(String url,String prenom,String nom) {
        this.url=url;
        this.nom=nom;
        this.prenom=prenom;
    }
    public ImageFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_image, container, false);
       // getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
setCancelable(true);
setStyle(STYLE_NO_TITLE,R.style.Theme_AppCompat);
txt=view.findViewById(R.id.txtguide);
txt.setText(prenom+" "+nom);
getDialog().getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        ImageView i = view.findViewById(R.id.grandeimage);
        Glide.with(getContext()).load(url).timeout(60000).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                return false;
            }
        }).into(i);
        return view;
    }
}