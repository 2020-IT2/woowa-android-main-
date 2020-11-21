package com.woowahan.woowahanfoods.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.location.Location;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.woowahan.woowahanfoods.Dataframe.Restaurant;
import com.woowahan.woowahanfoods.MainActivity;
import com.woowahan.woowahanfoods.R;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class RestaurantListAdapter extends RecyclerView.Adapter<RestaurantListAdapter.Holder> {
    private ArrayList<Restaurant> list = new ArrayList<>();
    private Context context;
    private Location myLoc;
    private RestaurantListAdapter.OnListItemSelectedInterface mListener;
    public DecimalFormat df = new DecimalFormat("###.##");

    public RestaurantListAdapter(Location myLoc, Context context, ArrayList<Restaurant> list, RestaurantListAdapter.OnListItemSelectedInterface listener) {
        this.context = context;
        this.list = list;
        this.mListener = listener;
        this.myLoc = myLoc;
    }

    public interface OnListItemSelectedInterface {
        void onItemSelected(View v, int position);
    }

    @NonNull
    @Override
    public RestaurantListAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_list_tab_sub_item, parent, false);
        RestaurantListAdapter.Holder holder = new RestaurantListAdapter.Holder(view);

        return holder;
    }

    @Override
    public int getItemCount() {
        return (null != list ? list.size() : 0);
    }

    public class Holder extends RecyclerView.ViewHolder{
        protected TextView tv_restaurant_name;
        protected TextView tv_restaurant_exp;
        protected TextView tv_likes;
        protected TextView tv_replys;
        protected TextView tv_distance;
        protected TextView tv_address;
        protected ImageView imageView;

        public Holder(View view) {
            super(view);
            this.tv_restaurant_name = (TextView) view.findViewById(R.id.tv_restaurant_name);
            this.tv_restaurant_exp = (TextView) view.findViewById(R.id.tv_restaurant_exp);
            this.tv_likes = (TextView) view.findViewById(R.id.tv_likes);
            this.tv_replys = (TextView) view.findViewById(R.id.tv_replys);
            this.tv_distance = (TextView) view.findViewById(R.id.tv_distance);
            this.tv_address = (TextView) view.findViewById(R.id.tv_address);
            this.imageView = (ImageView)view.findViewById(R.id.icon);

            if (Build.VERSION.SDK_INT >= 21) {
                // 21 버전 이상일 때
                GradientDrawable drawable=
                        (GradientDrawable) context.getDrawable(R.drawable.my_rect);
                // 둥근 사각형
                imageView.setBackground(drawable);
                imageView.setClipToOutline(true);
                // 동그라미
//                imageView.setBackground(new ShapeDrawable(new OvalShape()));
//                imageView.setClipToOutline(true);
            }

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemSelected(v, getAdapterPosition());
                }
            });

//            view.setOnLongClickListener(new View.OnLongClickListener(){
//                @Override
//                public boolean onLongClick(View v){
//                    Log.d("hello", "long clicked");
//                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
//                    builder.setTitle("삭제알림");
//                    builder.setMessage("알림을 삭제 하시겠습니까?");
//                    builder.setPositiveButton("확인",
//                            new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int which) {
//                                    int position = getAdapterPosition();
//                                    notifyItemRemoved(position);
//                                }
//                            });
//                    builder.show();
//                    return true;
//                }
//            });

        }

    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantListAdapter.Holder holder, final int position) {
        Location location = new Location("restaurant loc");
        location.setLatitude(list.get(position).lon);
        location.setLongitude(list.get(position).lat);

        float distance  = myLoc.distanceTo(location);

        holder.tv_restaurant_name.setText(list.get(position).restaurantName);
        holder.tv_restaurant_exp.setText(list.get(position).restaurantDetail);
        holder.tv_likes.setText(String.valueOf(list.get(position).likes));
        holder.tv_replys.setText(String.valueOf(list.get(position).replys));
        holder.tv_distance.setText("3" + " km");
        holder.tv_address.setText(list.get(position).address);
        Glide.with(context).load(list.get(position).media_url).into(holder.imageView);
    }
}
