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

public class StartupActivity extends AppCompatActivity implements OnRetriveListener{


    private FirebaseDatabase database;
    private DatabaseReference reference;
    private ProgressDialog progressDialog;
    private OnRetriveListener mListener;

    final private List<Product> productList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_startup);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference().child("Product");
        mListener = this;
        mListener.OnRetriveStart();
        //delayToStartSignInActivity();
    }
    private void delayToStartSignInActivity(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startSignInActivity();
                finishStartUpActivity();
            }
        }, 3000);


    }
    private void startSignInActivity(){
        Intent intent = new Intent(this,SignInActivity.class);
        startActivity(intent);
    }
    private int getItemIndex(Product product){
        int index = 0;
        for(int i = 0;i<productList.size();i++){
            if(productList.get(i).equals(product)){
                index = 1;
                break;
            }
        }
        return index;

    }
    private void finishStartUpActivity(){finish();}

    @Override
    public void OnRetriveStart() {
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                productList.add(dataSnapshot.getValue(Product.class));
                Log.d("StartUp", String.valueOf(productList.size()));


            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Product product =dataSnapshot.getValue(Product.class);
                int index = getItemIndex(product);
                productList.set(index,product);

                //mListener.OnRetriveSuccess(productList);
                //progressDialog.dismiss();


            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                Product product =dataSnapshot.getValue(Product.class);
                int index = getItemIndex(product);
                productList.remove(index);

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mListener.OnRetriveSuccess(productList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void OnRetriveSuccess(List<Product> products) {
        Intent intent = new Intent(this, SignInActivity.class);
        intent.putExtra("Products", (Serializable) products);
        startActivity(intent);
        this.finish();
    }
}

