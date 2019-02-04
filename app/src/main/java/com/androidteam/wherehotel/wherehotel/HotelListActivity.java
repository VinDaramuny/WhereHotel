package com.androidteam.wherehotel.wherehotel;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HotelListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private HotelAdapter hotelAdapter;
    private List<Hotel>hotelList =new ArrayList<>();

    private FirebaseDatabase database;
    private DatabaseReference reference;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_list);

        Log.d("README", String.valueOf(hotelList.size()));
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


        //hotelList.get(0).print();
        //hotelList.get(1).print();
//        Hotel hotel = new Hotel("Hotel name","https://s-ec.bstatic.com/images/hotel/max1280x900/101/101430248.jpg"
//                ,"No desc","1000$","Phnompenh");
//        Hotel hotel1 = new Hotel("Hotel name","https://s-ec.bstatic.com/images/hotel/max1280x900/101/101430248.jpg"
//                ,"No desc","1000$","Phnompenh");
//
//        hotelList.add(hotel);
//        hotelList.add(hotel1);



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
}
