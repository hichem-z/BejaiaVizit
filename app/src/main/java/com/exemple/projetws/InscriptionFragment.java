package com.exemple.projetws;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.blogspot.atifsoftwares.circularimageview.CircularImageView;
import com.exemple.projetws.data.ClientOne;
import com.exemple.projetws.data.RClient;
import com.exemple.projetws.model.Guide;
import com.exemple.projetws.model.ImageResponse;
import com.exemple.projetws.ui.main.OneViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.CONNECTIVITY_SERVICE;


public class InscriptionFragment extends Fragment {
    CircularImageView imageView;
    EditText txtnom,txtprenom,txtdatenai,txttel,txtemail,txtdesc;
    Button btnsave;
    OneViewModel oneViewModel;
    Activity context;
    Uri uri;
    Bitmap bitmap ;
    Guide guidephoto;
    NetworkInfo networkInfo;
    ConnectivityManager connectivityManager;
    ProgressDialog progressDialog;



    public InscriptionFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (container!=null){
            container.removeAllViews();
        }

        View view = inflater.inflate(R.layout.fragment_inscription, container, false);
        HomeFragment.handler.removeCallbacks(HomeFragment.update);
        context = getActivity();
        txtnom = view.findViewById(R.id.nameEt);
        imageView = view.findViewById(R.id.profileIv);
        txtprenom = view.findViewById(R.id.prenomEt);
        txtdatenai = view.findViewById(R.id.dobEt);
        txtdesc = view.findViewById(R.id.bioEt);
        txtemail = view.findViewById(R.id.emailEt);
        txttel = view.findViewById(R.id.phoneEt);
        btnsave = view.findViewById(R.id.btnsave);

        Context context = getContext();
        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connectivityManager= (ConnectivityManager) getActivity().getSystemService(CONNECTIVITY_SERVICE);
                networkInfo=connectivityManager.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {
                    add();
                }else{
                    Toast.makeText(getActivity(),"connexion perdu",Toast.LENGTH_SHORT).show();
                }
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.startPickImageActivity(getActivity(),getFragmentManager().findFragmentById(R.id.fragment));

            }
        });


        return view;
    }

    private void add(){
        final Guide[] guideResponse = new Guide[1];
        Guide guide = new Guide();
        guide.setNom(txtnom.getText().toString().trim());
        guide.setPrenom(txtprenom.getText().toString().trim());
        if (!txtdatenai.getText().toString().trim().isEmpty()){guide.setAge(Integer.valueOf(txtdatenai.getText().toString().trim())); }
        guide.setTelephone(txttel.getText().toString().trim());
        guide.setDescription(txtdesc.getText().toString().trim());
        guide.setEmail(txtemail.getText().toString().trim());
        oneViewModel = ViewModelProviders.of(this).get(OneViewModel.class);

        if (guide.getNom()==null){
            Toast.makeText(getContext(),"le champ nom ne peut être vide",Toast.LENGTH_SHORT).show();

        }else if (guide.getPrenom()==null){
            Toast.makeText(getContext(),"le champ prenom ne peut être vide",Toast.LENGTH_SHORT).show();

        }else if (guide.getTelephone()==null){
            Toast.makeText(getContext(),"verifier le champ téléphone",Toast.LENGTH_SHORT).show();
        }else if (guide.getEmail()==null){
            Toast.makeText(getContext(),"verifier le champ email",Toast.LENGTH_SHORT).show();

        }else if (guide.getAge()==0){
            Toast.makeText(getContext(),"vous devez avoir plus de 18 ans",Toast.LENGTH_SHORT).show();

        }else if (guide.getDescription()==null){
            Toast.makeText(getContext(),"le champ description ne peut être vide",Toast.LENGTH_SHORT).show();

        }else if(bitmap==null) {
            Toast.makeText(getContext(),"veuillez choisir une photo de profile",Toast.LENGTH_SHORT).show();

        }else{
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.show();
            progressDialog.setCancelable(false);
            progressDialog.setContentView(R.layout.prog);
            progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            oneViewModel.addGuide(guide);
            oneViewModel.guideMutableLiveData.observe(this, new Observer<Guide>() {
                @Override
                public void onChanged(Guide guid) {
                    if (guid != null) {
                        guidephoto = guid;
                        guidephoto.setPhoto("http://apitransport.eredlearning.com/projectsw/guide/images/" + guid.get_id() + ".jpg");
                        uploadImage(guid.get_id());

                    } else {
                        Toast.makeText(getContext(),"erreur",Toast.LENGTH_SHORT).show();


                    }

                }
            });
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {


        if (requestCode==CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode== Activity.RESULT_OK){
            Uri imageuri=CropImage.getPickImageResultUri(getContext(),data);

            if (CropImage.isReadExternalStoragePermissionsRequired(getContext(),imageuri)){

                uri= imageuri;

                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},0);
            }else {

                startCrop(imageuri);
            }
        }
        if (requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){

            CropImage.ActivityResult result=CropImage.getActivityResult(data);
            if (resultCode== RESULT_OK){
                imageView.setImageURI(result.getUri());
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(),result.getUri()) ;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Toast.makeText(getContext(),"image sélectionnée",Toast.LENGTH_SHORT).show();
            }
        }



    }

    private void startCrop(Uri imageuri) {
        Activity activity = getActivity();
        activity.setTheme(android.R.style.Theme_Material_Light_DarkActionBar);
        CropImage.activity(imageuri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setMultiTouchEnabled(true)
                .start(activity,getFragmentManager().findFragmentById(R.id.fragment));
    }

    public void uploadImage(String s){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,70,byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        String imageencode = Base64.encodeToString(bytes,Base64.DEFAULT);
        Single<ImageResponse> single =RClient.getInstance().upImage(imageencode,s).subscribeOn(Schedulers.io()).subscribeOn(AndroidSchedulers.mainThread());
        single.subscribe(new SingleObserver<ImageResponse>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onSuccess(@NonNull ImageResponse s) {
                if (s.getMessage().equals("uploaded")){
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        public void run() {
                            updatephotovalue(guidephoto);
                        }
                    });

                }else{


                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
            }
        });

    }
    private void updatephotovalue(Guide guidephoto) {
        oneViewModel = ViewModelProviders.of(getActivity()).get(OneViewModel.class);
        oneViewModel.addGuide(guidephoto);
        oneViewModel.guideMutableLiveData.removeObservers(getViewLifecycleOwner());
        oneViewModel.guideMutableLiveData.observe(getViewLifecycleOwner(), new Observer<Guide>() {
            @Override
            public void onChanged(Guide guid) {
                progressDialog.dismiss();
                reset();
                Toast.makeText(getContext(),guid.getPrenom()+" ,vous êtes désormais un guide chez Bejaia vizit",Toast.LENGTH_LONG).show();


            }
        });
    }
    void reset(){
        txtnom.setText(null);
        txttel.setText(null);
        txtemail.setText(null);
        txtdesc.setText(null);
        txtprenom.setText(null);
        txtdatenai.setText(null);
        imageView.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.mipmap.ajoutpho));

    }

    @Override
    public void onStop() {
        super.onStop();
        HomeFragment.handler.removeCallbacks(HomeFragment.update);
    }

    @Override
    public void onResume() {
        super.onResume();
        HomeFragment.handler.removeCallbacks(HomeFragment.update);


    }

    @Override
    public void onStart() {
        super.onStart();

    }
}