package com.woowahan.woowahanfoods.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        protected TextView content;
        protected TextView time;
        protected ConstraintLayout bg;

        public Holder(View view) {
            super(view);
            this.title = (TextView) view.findViewById(R.id.title);
            this.content = (TextView) view.findViewById(R.id.content);
            this.time = (TextView) view.findViewById(R.id.time);
            this.bg = (ConstraintLayout) view.findViewById(R.id.comment);

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
        int communityType = list.get(position).restaurantID;
        int communityID = list.get(position).schoolID;
        String content = list.get(position).schoolName;
        String title = "title";
        holder.title.setText(title);
        int lastIndex = content.length() > 15 ? 15 : content.length();
        holder.content.setText(list.get(position).schoolName + " : " + content.substring(0, lastIndex));
        holder.itemView.setTag(position);

    }
}
