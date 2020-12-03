package com.woowahan.woowahanfoods.FeedViewer.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.woowahan.woowahanfoods.Address.Adapter.AddressAdapter;
import com.woowahan.woowahanfoods.DataModel.Juso;
import com.woowahan.woowahanfoods.DataModel.Restaurant;
import com.woowahan.woowahanfoods.R;

import java.util.ArrayList;

public class GridViewAdapter extends RecyclerView.Adapter<GridViewAdapter.CustomViewHolder> {
    Context context;

    private ArrayList<Restaurant> arrayList;
    public GridViewAdapter.OnListItemSelectedInterface mListener;

    public GridViewAdapter(Context context, ArrayList<Restaurant> arrayList, GridViewAdapter.OnListItemSelectedInterface mListener) {
        this.context = context;
        this.arrayList = arrayList;
        this.mListener = mListener;
    }

    public interface OnListItemSelectedInterface {
        void onItemSelected(View v, int position);
    }

    //뷰 홀더 객체 생성
    @NonNull
    @Override
    public GridViewAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_view_item, parent, false);
        GridViewAdapter.CustomViewHolder holder = new GridViewAdapter.CustomViewHolder(view);
        return holder;
    }

    //데이터를 뷰홀더에 바인딩
    @Override
    public void onBindViewHolder(@NonNull GridViewAdapter.CustomViewHolder holder, int position) {
        Glide.with(context).load(arrayList.get(position).mediaURL).into(holder.iv);
    }

    //전체 아이템 갯수 리턴
    @Override
    public int getItemCount() {
        return (null != arrayList ? arrayList.size() : 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        public ImageView iv;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.iv = itemView.findViewById(R.id.img_view);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onItemSelected(view, getAdapterPosition());
                }
            });
        }
    }
}
