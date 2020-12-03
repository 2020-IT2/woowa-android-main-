package com.woowahan.woowahanfoods.Home.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.woowahan.woowahanfoods.DataModel.Feed;
import com.woowahan.woowahanfoods.DataModel.Restaurant;
import com.woowahan.woowahanfoods.R;

import java.util.ArrayList;

public class viewPageAdapter extends PagerAdapter {
    private Context mContext;
    private ArrayList<Feed> imageList;

    public viewPageAdapter(Context context, ArrayList<Feed> imageList)
    {
        this.mContext = context;
        this.imageList = imageList;
    }

    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.main_food_view_page, null);

        ImageView imageView = view.findViewById(R.id.imageView);
        TextView hashTag = view.findViewById(R.id.tv_hashtag);

        MultiTransformation multiOption = new MultiTransformation(new CenterCrop(), new RoundedCorners(45));
        Glide.with(mContext).load(imageList.get(position).mediaURL).apply(RequestOptions.bitmapTransform(multiOption)).into(imageView);
        hashTag.setText(imageList.get(position).hashtag);
        container.addView(view);

        return view;
    }

    @Override
    public int getCount() {
        return imageList.size();
    }

    public void refresh(ArrayList<Feed> items)
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