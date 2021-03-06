package com.androidteam.wherehotel.wherehotel;


import android.util.Log;

import java.io.Serializable;

public class Hotel {

    private String hotelName;
    private String hotelImg;
    private String hotelDescription;
    private String hotelPrice;
    private String hotelLocation;
    private String hotelRating;

    public String getLatLong() {
        return latLong;
    }

    public void setLatLong(String latLong) {
        this.latLong = latLong;
    }

    private String latLong;

    public String getHotelRating() {
        return hotelRating;
    }

    public void setHotelRating(String hotelRating) {
        this.hotelRating = hotelRating;
    }

    public Hotel(String hotelName, String hotelImg, String hotelDescription, String hotelPrice, String hotelLocation, String hotelRating,String latLong){
        this.hotelName = hotelName;
        this.hotelImg = hotelImg;
        this.hotelDescription = hotelDescription;
        this.hotelPrice = hotelPrice;
        this.hotelLocation = hotelLocation;
        this.hotelRating = hotelRating;
        this.latLong = latLong;
    }
    public Hotel(){}

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getHotelImg() {
        return hotelImg;
    }

    public void setHotelImg(String hotelImg) {
        this.hotelImg = hotelImg;
    }

    public String getHotelDescription() {
        return hotelDescription;
    }

    public void setHotelDescription(String hotelDescription) {
        this.hotelDescription = hotelDescription;
    }

    public String getHotelPrice() {
        return hotelPrice;
    }

    public void setHotelPrice(String hotelPrice) {
        this.hotelPrice = hotelPrice;
    }

    public String getHotelLocation() {
        return hotelLocation;
    }

    public void setHotelLocation(String hotelLocation) {
        this.hotelLocation = hotelLocation;
    }

    public void print(){
        Log.d("HOTEL", getHotelName());
    }
}
