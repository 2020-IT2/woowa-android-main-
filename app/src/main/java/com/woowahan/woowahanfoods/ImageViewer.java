package com.woowahan.woowahanfoods;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.richpath.RichPath;
import com.richpath.RichPathView;
import com.richpathanimator.RichPathAnimator;
import com.woowahan.woowahanfoods.Dataframe.ImageData;
import com.woowahan.woowahanfoods.Dataframe.Restaurant;

public class ImageViewer extends Fragment {

    public Restaurant imageData;
    public LinearLayout linear_view;
    public boolean togle = false;

    public static SubPage newInstance(Restaurant restaurant) {
        SubPage fragment = new SubPage();
        Bundle args = new Bundle();
        Gson gson = new Gson();
        args.putString("restaurant", gson.toJson(restaurant));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image_viewer, container, false);

        view.setClickable(true);

        Bundle bundle = getArguments();

        if(bundle!=null){
            Gson gson = new Gson();
            this.imageData = gson.fromJson(bundle.getString("restaurant"), Restaurant.class);
        }

        final LinearLayout linearLayout = view.findViewById(R.id.lin_visible);
        final TextView textView = view.findViewById(R.id.location);
        linear_view = view.findViewById(R.id.linear_view);

        linear_view.setClickable(true);
        linear_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (togle){
                    linearLayout.setVisibility(View.VISIBLE);
                    linearLayout.animate().alpha(1.0f).setDuration(999);
                    togle = false;
                } else {
                    linearLayout.setVisibility(View.GONE);
                    linearLayout.animate().alpha(0.0f);
                    togle = true;
                }
            }
        });




        return view;
    }


    

}
