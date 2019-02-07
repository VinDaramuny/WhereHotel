package com.androidteam.wherehotel.wherehotel;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class StartupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_startup);

        delayToStartSignInActivity();
    }
    private void delayToStartSignInActivity(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startSignInActivity();
                finishStartUpActivity();
            }
        }, 2000);


    }
    private void startSignInActivity(){
        Intent intent = new Intent(this,SignInActivity.class);
        startActivity(intent);
    }

    private void finishStartUpActivity(){finish();}

}

