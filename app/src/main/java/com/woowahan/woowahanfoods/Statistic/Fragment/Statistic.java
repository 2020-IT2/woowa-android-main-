package com.woowahan.woowahanfoods.Statistic.Fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.naver.maps.map.MapView;

import com.richpath.RichPath;
import com.richpath.RichPathView;
import com.richpathanimator.RichPathAnimator;
import com.woowahan.woowahanfoods.DataModel.City;
import com.woowahan.woowahanfoods.MainActivity;
import com.woowahan.woowahanfoods.R;

import java.util.ArrayList;

public class Statistic extends Fragment {
    final public ArrayList<City> cityArrayList = new ArrayList<City>();
    public String [] cityNames = new String[]{
            "강서구_map", "양천구_map", "구로구_map", "금천구_map", "관악구_map",
            "서초구_map", "강남구_map", "송파구_map", "강동구_map", "광진구_map",
            "중랑구_map", "노원구_map", "도봉구_map", "강북구_map", "성북구_map",
            "종로구_map", "은평구_map", "마포구_map", "영등포구_map", "동작구_map",
            "용산구_map", "성동구_map", "동대문구_map", "서대문구_map", "중구_map"
    };
    public static final int whitegray = 0xFFE6E6E6;
    public static final int white = Color.WHITE;
    public static final int black = Color.BLACK;
    public static final int selected = 0xff090090;
    private final static String TAG = MainActivity.class.getSimpleName();

    private MapView mMapView;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statistic, container, false);
        final RichPathView richPathView = view.findViewById(R.id.rich_map);
        final LinearLayout linearLayout = view.findViewById(R.id.lin_visible);
        final TextView textView = view.findViewById(R.id.location);
        for(String cityName : cityNames){
            cityArrayList.add(new City(cityName));
        }

        richPathView.setOnPathClickListener(new RichPath.OnPathClickListener() {
            @Override
            public void onClick(RichPath richPath) {
                RichPath orgRichPath = richPath;
                String name = richPath.getName();
                Log.d("SampleMap", "name : " + name);
                for(City city : cityArrayList){
                    if(name.equals(city.getName().split("_")[0])) {
                        name = city.getName();
                        orgRichPath = richPathView.findRichPathByName(name);
                        Log.d("SampleMap2", "name : " + name);
                    }
                    if (name.equals(city.getName())) {
                        Log.d("SampleMap", "1st IF");
                        if(orgRichPath.getFillColor() == whitegray){
                            Log.d("SampleMap", "2nd IF");
                            RichPathAnimator.animate(orgRichPath)
                                    .fillColor(selected)
                                    .start();
                            linearLayout.setVisibility(View.VISIBLE);
                            linearLayout.animate().alpha(1.0f).setDuration(999);
                            textView.setText(city.getName().split("_")[0]);
                            richPath = richPathView.findRichPathByName(city.name.split("_")[0]);
                            RichPathAnimator.animate(richPath)
                                    .fillColor(white)
                                    .start();
                        }
                        else{


                            RichPathAnimator.animate(orgRichPath)
                                    .fillColor(whitegray)
                                    .start();
                            richPath = richPathView.findRichPathByName(city.name.split("_")[0]);
                            RichPathAnimator.animate(richPath)
                                    .fillColor(black)
                                    .start();
                            linearLayout.setVisibility(View.GONE);
                            linearLayout.animate().alpha(0.0f);
                        }
                    }else {
                        richPath = richPathView.findRichPathByName(city.getName());
                        RichPathAnimator.animate(richPath)
                                .fillColor(whitegray)
                                .start();

                        richPath = richPathView.findRichPathByName(city.getName().split("_")[0]);
                        RichPathAnimator.animate(richPath)
                                .fillColor(black)
                                .start();
                    }
                }
                Log.d("SampleMap", "fifth");
            }
        });

        return view;


    }

}
