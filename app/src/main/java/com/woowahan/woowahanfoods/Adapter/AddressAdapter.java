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

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.CustomViewHolder> {
    private ArrayList<Juso> arrayList;

    public AddressAdapter(ArrayList<Juso> arrayList) {
        this.arrayList = arrayList;
    }

    //처음 생성되는 생명주기
    @NonNull
    @Override
    public AddressAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.address_list, parent, false);
        CustomViewHolder holder = new CustomViewHolder(view);
        return holder;
    }

    //추가될 때
    @Override
    public void onBindViewHolder(@NonNull AddressAdapter.CustomViewHolder holder, int position) {
        holder.tv_dong.setText(arrayList.get(position).jibunAddr);
        holder.tv_road.setText(arrayList.get(position).roadAddr);

        holder.tv_dong.setTag(position);
        holder.tv_dong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //주소선택하고 값 넘겨준다.
            }
        });
    }

    @Override
    public int getItemCount() {
        return (null != arrayList ? arrayList.size() : 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        protected TextView tv_dong;
        protected TextView tv_road;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tv_dong = (TextView)itemView.findViewById(R.id.tv_dong);
            this.tv_road = (TextView)itemView.findViewById(R.id.tv_road);
        }
    }
}
