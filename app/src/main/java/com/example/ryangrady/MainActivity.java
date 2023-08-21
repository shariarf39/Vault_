package com.example.ryangrady;



import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ryangrady.photo.PhotoAlbum;
import com.example.ryangrady.photo.PhotosAlbumActivty;

public class MainActivity extends AppCompatActivity {
    Context mContext = this;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



    }



}
