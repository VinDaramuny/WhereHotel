package com.androidteam.wherehotel.wherehotel;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private final int REQUEST_CODE_LOCATION_PERMISSION = 2;

    private MapView mapView;
    private GoogleMap googleMap;

    private FusedLocationProviderClient locationProviderClient;
    private Marker userMarker;
    private String[] latlng;
    private double latitude;
    private double longtitude;
    private String hotelName;
    private String hotelLocation;

    String tmp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        Toolbar toolbar = findViewById(R.id.toolbar);

        Intent getHotel = getIntent();
        hotelName = getHotel.getStringExtra("name");
        hotelLocation = getHotel.getStringExtra("location");
        tmp = getHotel.getStringExtra("latlong");
        latlng = tmp.split(",");

        latitude = Double.parseDouble(latlng[0]);
        longtitude = Double.parseDouble(latlng[1]);
        //hotel = (Hotel)getHotel.getSerializableExtra("test");
        //Log.d("OPENIT",hotel.getHotelName());



        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        mapView = findViewById(R.id.map_view);
        mapView.onCreate(savedInstanceState);
        // Load map object
        mapView.getMapAsync(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();

        mapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();

        mapView.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mapView.onDestroy();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        LatLng location = new LatLng(latitude, longtitude);

        googleMap.addMarker(new MarkerOptions().position(location).snippet(hotelLocation).title(hotelName)).showInfoWindow();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        googleMap.setMyLocationEnabled(true);
        googleMap.getUiSettings().setMyLocationButtonEnabled(true);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location,15));
        //loadUserCurrentLocation();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == REQUEST_CODE_LOCATION_PERMISSION && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            loadUserCurrentLocation();
        }
    }

    private void loadUserCurrentLocation(){
        locationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            String[] permissions = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
            ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE_LOCATION_PERMISSION);
            return;
        }
        Task<Location> locationTask = locationProviderClient.getLastLocation();
        locationTask.addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                if(task.isSuccessful()){
                    Location location = task.getResult();
                    if(location != null){
                        // Move camera
                        moveCamera(location);
                    } else {
                        Toast.makeText(MapActivity.this, "Current location not found.", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(MapActivity.this, "Error while getting current location.", Toast.LENGTH_LONG).show();
                }
                requestLocationUpdate();
            }
        });
    }
    @SuppressLint("MissingPermission")
    private void requestLocationUpdate(){
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//        LocationCallback locationCallback = new LocationCallback(){
//            @Override
//            public void onLocationResult(LocationResult locationResult) {
//                super.onLocationResult(locationResult);
//            }
//        };
        locationProviderClient.requestLocationUpdates(locationRequest, new LocationCallback(){
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                Location updateLocation = locationResult.getLastLocation();
                processAddAndMoveMarker(updateLocation);
                //locationProviderClient.removeLocationUpdates(this);

            }
        },null);

    }
    private void processAddAndMoveMarker(Location location){

        LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());
        if(userMarker == null){

            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.title("U are here");
            markerOptions.position(latLng);
            BitmapDescriptor carIcon = BitmapDescriptorFactory.fromResource(R.drawable.ic_carnoerror);

            markerOptions.icon(carIcon);
            userMarker = googleMap.addMarker(markerOptions);
        }
        userMarker.setPosition(latLng);

    }
    private void moveCamera(Location location){
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 18);
        googleMap.animateCamera(cameraUpdate);
    }

}