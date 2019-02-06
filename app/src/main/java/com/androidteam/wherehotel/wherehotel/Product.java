package com.androidteam.wherehotel.wherehotel;

import java.io.Serializable;

public class Product implements Serializable {

    private String title;
    private String desc;
    private String  rate;
    private String  price;
    private String  imgUrl;

    public Product(String title, String desc, String rate, String price, String imgUrl) {
        this.title = title;
        this.desc = desc;
        this.rate = rate;
        this.price = price;
        this.imgUrl = imgUrl;
    }
    public Product(){}
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

}

