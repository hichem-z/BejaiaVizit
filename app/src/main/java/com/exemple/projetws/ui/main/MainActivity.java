package com.exemple.projetws.ui.main;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.exemple.projetws.HomeFragment;
import com.exemple.projetws.InscriptionFragment;
import com.exemple.projetws.R;
import com.exemple.projetws.SplachScreen;
import com.exemple.projetws.model.Hotel;
import com.exemple.projetws.ui.HotelAdapter;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.List;

public class MainActivity extends AppCompatActivity {
String location = "";
static Context context;
public static FragmentTransaction fragmentTransaction;
   public static ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bundle bundle = getIntent().getExtras();
        fragmentTransaction=getSupportFragmentManager().beginTransaction();
        context=MainActivity.this;
        if (bundle!=null){
            location=bundle.getString("location");
            }
            if (location.equals("inscription")){
                FragmentTransaction ft =getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.fragment, new InscriptionFragment(), "inscription");
                ft.commit();
            }else{

                    FragmentTransaction ft =getSupportFragmentManager().beginTransaction();
                    HomeFragment homeFragment = new HomeFragment();
                    homeFragment.handler.removeCallbacks(homeFragment.update);
                    ft.replace(R.id.fragment, new HomeFragment(), "home");
                    ft.commit();
            }



    }

    @Override
    public void onBackPressed() {
        int fragments = getSupportFragmentManager().getBackStackEntryCount();

        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {

            super.onBackPressed();
        }

    }


    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("resume");

    }

    @Override
    protected void onStop() {
        super.onStop();
        System.out.println("stop");

//        getIntent().setAction(Intent.ACTION_MAIN);
//        getIntent().setType(Intent.CATEGORY_LAUNCHER);

    }

    @Override
    protected void onStart() {
        super.onStart();

        System.out.println("start");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("destroy");
        HomeFragment.handler.removeCallbacks(HomeFragment.update);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        System.out.println("restart");
       // HomeFragment.update.run();

    }
    public static void showProgress(){
         progressDialog = new ProgressDialog(context);
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setContentView(R.layout.prog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    }
    public static void showerror(Fragment fragment){
        Toast.makeText(context,"erreur de chargement",Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.getContext().setTheme(R.style.DialogTheme);
        builder.setInverseBackgroundForced(true);
        builder.setCancelable(false);
        builder.setTitle("Echec");
        builder.setMessage("Voulez vous réessayer ?");
        builder.setPositiveButton("OUI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                fragmentTransaction.replace(R.id.fragment, fragment, fragment.getClass().getName());
                fragmentTransaction.commit();
                fragmentTransaction =fragment.getFragmentManager().beginTransaction();
            }
        });
        builder.setNegativeButton("NON", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

}