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

import com.woowahan.woowahanfoods.Dataframe.Restaurant;
import com.woowahan.woowahanfoods.MainActivity;
import com.woowahan.woowahanfoods.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class RestaurantListAdapter extends RecyclerView.Adapter<RestaurantListAdapter.Holder> {
    private ArrayList<Restaurant> list = new ArrayList<>();
    private Context context;
    private RestaurantListAdapter.OnListItemSelectedInterface mListener;

    public RestaurantListAdapter(Context context, ArrayList<Restaurant> list, RestaurantListAdapter.OnListItemSelectedInterface listener) {
        this.context = context;
        this.list = list;
        this.mListener = listener;
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
        protected TextView title;
        protected TextView address;
        protected TextView time;
        protected ConstraintLayout bg;
        protected ImageView imageView;

        public Holder(View view) {
            super(view);
            this.title = (TextView) view.findViewById(R.id.title);
            this.address = (TextView) view.findViewById(R.id.address);
            this.time = (TextView) view.findViewById(R.id.time);
            this.bg = (ConstraintLayout) view.findViewById(R.id.comment);
            this.imageView = (ImageView)view.findViewById(R.id.icon);

            if (Build.VERSION.SDK_INT >= 21) {
                // 21 버전 이상일 때
                GradientDrawable drawable=
                        (GradientDrawable) context.getDrawable(R.drawable.my_rect);

                imageView.setBackground(drawable);
                imageView.setClipToOutline(true);

                imageView.setBackground(new ShapeDrawable(new OvalShape()));
                imageView.setClipToOutline(true);
            }


            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bg.setBackgroundColor(Color.parseColor("#FFFFFF"));
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

        Location locationA = new Location("my loc");
        locationA.setLatitude(55);
        locationA.setLongitude(55);

        Location locationB = new Location("restaurant loc");
        locationA.setLatitude(31);
        locationA.setLongitude(34);

        float distance  = locationA.distanceTo(locationB);

        holder.title.setText(list.get(position).regionName);
        holder.address.setText(list.get(position).address);
    }
}
