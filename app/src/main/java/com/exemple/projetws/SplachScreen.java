package com.exemple.projetws;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.exemple.projetws.ui.main.MainActivity;

public class SplachScreen extends AppCompatActivity {
    Animation topAnim,bottomAnim;
    LinearLayout lay1,lay2;
    Button btni,bntc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splach_screen);

        //Animations
        topAnim= AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnim= AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        lay1=findViewById(R.id.lay1);
        lay2=findViewById(R.id.lay2);
        lay1.setAnimation(topAnim);
        lay2.setAnimation(bottomAnim);
        bntc = findViewById(R.id.button2);
        btni = findViewById(R.id.button);
        bntc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(SplachScreen.this, MainActivity.class);
                getIntent().setAction(null);
                getIntent().setType(null);
                in.setAction(Intent.ACTION_MAIN);
                in.setType(Intent.CATEGORY_LAUNCHER);
                startActivity(in);
                finish();
            }
        });
        btni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(SplachScreen.this, MainActivity.class);
                 in.putExtra("location","inscription");
                startActivity(in);
            }
        });




    }
}