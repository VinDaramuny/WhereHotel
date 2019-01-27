package com.androidteam.wherehotel.wherehotel;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class HotelAdapter extends RecyclerView.Adapter<HotelAdapter.ViewHolder> {
    private Context context;
    final private List<Hotel> hotelList;

    public HotelAdapter(Context context, List<Hotel> hotelList) {
        this.context = context;
        this.hotelList = hotelList;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView hotelName;
        TextView hotelDescription;
        ImageView hotelImg;
        TextView hotelPrice;
        TextView hotelLocation;
        public ViewHolder(View itemView){
            super(itemView);
//            hotelName = itemView.findViewById(R.id.textViewName);
//            hotelDescription = itemView.findViewById(R.id.textViewDesc);
//            hotelImg = itemView.findViewById(R.id.imageViewImg);
//            hotelPrice = itemView.findViewById(R.id.textViewPrice);
//            hotelLocation = itemView.findViewById(R.id.textViewLocation);

        }


    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
