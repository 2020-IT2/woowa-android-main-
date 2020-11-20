package com.woowahan.woowahanfoods.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.woowahan.woowahanfoods.Dataframe.Juso;
import com.woowahan.woowahanfoods.Dataframe.MyAddress;
import com.woowahan.woowahanfoods.Dataframe.User;
import com.woowahan.woowahanfoods.Home;
import com.woowahan.woowahanfoods.MainActivity;
import com.woowahan.woowahanfoods.R;

import java.util.ArrayList;

public class MyAddressAdapter extends RecyclerView.Adapter<MyAddressAdapter.CustomViewHolder> {
    private ArrayList<MyAddress> arrayList;
    public MyAddressAdapter.OnListItemSelectedInterface mListener;
    public Context mContext;

    public MyAddressAdapter(Context mContext, ArrayList<MyAddress> arrayList, MyAddressAdapter.OnListItemSelectedInterface mListener) {
        this.arrayList = arrayList;
        this.mListener = mListener;
        this.mContext = mContext;
    }

    public interface OnListItemSelectedInterface {
        void onItemSelected(View v, int position);
    }

    //뷰 홀더 객체 생성
    @NonNull
    @Override
    public MyAddressAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.address_list, parent, false);
        MyAddressAdapter.CustomViewHolder holder = new MyAddressAdapter.CustomViewHolder(view);
        return holder;
    }

    //데이터를 뷰홀더에 바인딩
    @Override
    public void onBindViewHolder(@NonNull MyAddressAdapter.CustomViewHolder holder, int position) {
        holder.tv_dong.setText(arrayList.get(position).dongAddress);
        holder.tv_road.setText(arrayList.get(position).roadAddress);
        holder.tv_dong.setTag(position);
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
                    ((MainActivity)mContext).setCurAddress(getAdapterPosition());
                    ((MainActivity)mContext).replaceFragmentFull(new Home());
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    int position = getAdapterPosition();
                    ((MainActivity)mContext).removeAddress(position);
                    notifyItemRemoved(position);
                    return false;
                }
            });
        }

    }
}
