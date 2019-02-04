package com.androidteam.wherehotel.wherehotel;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

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
        //TextView hotelDescription;
        ImageView hotelImg;
        TextView hotelPrice;
        TextView hotelLocation;
        public ViewHolder(View itemView){
            super(itemView);
            hotelName = itemView.findViewById(R.id.textViewName);
            //hotelDescription = itemView.findViewById(R.id.textViewDesc);
            hotelImg = itemView.findViewById(R.id.imageViewImg);
            hotelPrice = itemView.findViewById(R.id.textViewPrice);
            hotelLocation = itemView.findViewById(R.id.textViewLocation);

        }


    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_hotel_list_item,parent,false);
        ViewHolder viewHolder =new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Hotel hotel =hotelList.get(position);
        holder.hotelName.setText(hotel.getHotelName());
        holder.hotelPrice.setText(hotel.getHotelPrice());
        Glide.with(context).asBitmap().load(hotel.getHotelImg()).into(holder.hotelImg);
        holder.hotelLocation.setText(getGeo(hotel.getHotelLocation()));
        //Glide.with(mContext).asBitmap().load(product.getImgUrl()).into(holder.productImg);

    }

    private String getGeo(String location) {
        String geoCode = " no location yet";

        return geoCode;
    }

    @Override
    public int getItemCount() {
        return hotelList.size();
    }
}
