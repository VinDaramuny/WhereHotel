package com.androidteam.wherehotel.wherehotel;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class HotelListActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private RecyclerView recyclerView;
    private HotelAdapter hotelAdapter;
    private List<Hotel>hotelList =new ArrayList<>();

    private DrawerLayout drawerLayout;

    private FirebaseDatabase database;
    private DatabaseReference reference;

    FirebaseAuth firebaseAuth;
    FirebaseUser currentUser;
    private String userName;
    private String userProfileUrl;

    CircleImageView pfImageView;
    TextView usernameTextView;

    LoginManager loginManager;
    private boolean mFromSavedInstanceState;
    private int mCurrentSelectedPosition;
    Intent intent;
    TextView mTextView;
    Button mapButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_list);

        TextView mTextView = (TextView) findViewById(R.id.textView);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        NavigationView navigationView = findViewById(R.id.nav);
        navigationView.bringToFront();
        drawerLayout = findViewById(R.id.drawer);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.app_name,R.string.app_name);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        Button mapButton;
        mapButton = findViewById(R.id.btn_map);

        navigationView.setNavigationItemSelectedListener(this);
        firebaseAuth  = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();
        Log.d("IMGURL",currentUser.getPhotoUrl().toString());
        userName = currentUser.getDisplayName();
        userProfileUrl = currentUser.getPhotoUrl().toString();


        View headerView = navigationView.getHeaderView(0);

        usernameTextView = headerView.findViewById(R.id.header_user_name);
        pfImageView = headerView.findViewById(R.id.header_pf);
        usernameTextView.setText(userName);
        Glide.with(this).asBitmap().load(userProfileUrl).into(pfImageView);


        //Log.d("README", String.valueOf(hotelList.size()));
        recyclerView=findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager layoutManager =new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        hotelAdapter = new HotelAdapter(this,hotelList);
        hotelAdapter.notifyDataSetChanged();
        //recyclerView.setAdapter(hotelAdapter);


        database = FirebaseDatabase.getInstance();
        reference = database.getReference().child("Hotel");
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                hotelList.add(dataSnapshot.getValue(Hotel.class));
                recyclerView.setAdapter(hotelAdapter);

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Hotel hotel = dataSnapshot.getValue(Hotel.class);
                int index = getItemIndex(hotel);
                hotelList.set(index,hotel);
                hotelAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                Log.e("README","onchild added");
                Hotel hotel = dataSnapshot.getValue(Hotel.class);
                int index = getItemIndex(hotel);
                hotelList.remove(index);
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    private int getItemIndex(Hotel hotel){
        int index = 0;
        for(int i = 0;i<hotelList.size();i++){
            if(hotelList.get(i).equals(hotel)){
                index = 1;
                break;
            }
        }
        return index;

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Context context;
        Resources resources;
        switch (item.getItemId())
        {

            case R.id.logout:
                firebaseAuth.getInstance().signOut();
                loginManager.getInstance().logOut();
                intent = new Intent(this,SignInActivity.class);
                startActivity(intent);
        }
        return false;
    }
}


