package com.woowahan.woowahanfoods.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.woowahan.woowahanfoods.Dataframe.Restaurant;
import com.woowahan.woowahanfoods.R;

import java.util.ArrayList;

public class viewPageAdapter extends PagerAdapter {
    private Context mContext;
    private ArrayList<Restaurant> imageList;

    public viewPageAdapter(Context context, ArrayList<Restaurant> imageList)
    {
        this.mContext = context;
        this.imageList = imageList;
    }

    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.view_page, null);

        ImageView imageView = view.findViewById(R.id.imageView);
        Glide.with(mContext).load(imageList.get(position).media_url).into(imageView);

        container.addView(view);

        return view;
    }

    @Override
    public int getCount() {
        return imageList.size();
    }

    public void refresh(ArrayList<Restaurant> items)
    {
        this.imageList = items;
        notifyDataSetChanged();
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return (view == (View)o);
    }
}