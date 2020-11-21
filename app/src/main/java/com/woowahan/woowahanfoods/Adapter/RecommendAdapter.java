package com.woowahan.woowahanfoods.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.woowahan.woowahanfoods.Dataframe.Juso;
import com.woowahan.woowahanfoods.R;

import java.util.ArrayList;

public class RecommendAdapter extends RecyclerView.Adapter<RecommendAdapter.CustomViewHolder> {
    private ArrayList<Juso> arrayList;
    public OnListItemSelectedInterface mListener;

    public RecommendAdapter(ArrayList<Juso> arrayList, OnListItemSelectedInterface mListener) {
        this.arrayList = arrayList;
        this.mListener = mListener;
    }

    public interface OnListItemSelectedInterface {
        void onItemSelected(View v, int position);
    }

    //뷰 홀더 객체 생성
    @NonNull
    @Override
    public RecommendAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.address_list, parent, false);
        CustomViewHolder holder = new CustomViewHolder(view);
        return holder;
    }

    //데이터를 뷰홀더에 바인딩
    @Override
    public void onBindViewHolder(@NonNull RecommendAdapter.CustomViewHolder holder, int position) {
        holder.tv_dong.setText(arrayList.get(position).jibunAddr);
        holder.tv_road.setText(arrayList.get(position).roadAddr);

        holder.tv_dong.setTag(position);
        holder.tv_dong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    //전체 아이템 갯수 리턴
    @Override
    public int getItemCount() {
        return (null != arrayList ? arrayList.size() : 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_dong;
        public TextView tv_road;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tv_dong = itemView.findViewById(R.id.tv_dong);
            this.tv_road = itemView.findViewById(R.id.tv_road);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onItemSelected(view, getAdapterPosition());
                }
            });
        }
    }
}
