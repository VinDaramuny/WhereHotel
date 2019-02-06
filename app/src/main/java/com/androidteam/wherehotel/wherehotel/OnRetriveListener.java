package com.androidteam.wherehotel.wherehotel;

import java.util.List;

public interface OnRetriveListener {
    void OnRetriveStart();
    void OnRetriveSuccess(List<Product> products);
}
