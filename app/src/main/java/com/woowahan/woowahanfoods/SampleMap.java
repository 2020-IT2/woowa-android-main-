package com.woowahan.woowahanfoods;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.richpath.RichPath;
import com.richpath.RichPathView;
import com.richpathanimator.RichPathAnimator;

import java.util.ArrayList;
import java.util.List;

public class SampleMap extends Fragment {
    final public ArrayList<City> cityArrayList = new ArrayList<City>();
    public CardView cardView;
    public LinearLayout linearLayout;
    public String [] cityNames = new String[]{
            "강서구_map", "양천구_map", "구로구_map", "금천구_map", "관악구_map",
            "서초구_map", "강남구_map", "송파구_map", "강동구_map", "광진구_map",
            "중랑구_map", "노원구_map", "도봉구_map", "강북구_map", "성북구_map",
            "종로구_map", "은평구_map", "마포구_map", "영등포구_map", "동작구_map",
            "용산구_map", "성동구_map", "동대문구_map", "서대문구_map", "중구_map"
    };

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sample_layout, container, false);
        final RichPathView richPathView = view.findViewById(R.id.rich_map);
// or by index

        for(String cityName : cityNames){
            cityArrayList.add(new City(cityName));
        }

        cardView = view.findViewById(R.id.cv_best_food);
        linearLayout = view.findViewById(R.id.cv_under_card);

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
                        if(orgRichPath.getFillColor() == Color.WHITE){
                            cardView.animate()
                                    .alpha(1.0f)
                                    .setDuration(300);
                            linearLayout.animate()
                                    .translationY(cardView.getHeight())
                                    .setDuration(150);
                            Log.d("SampleMap", "2nd IF");
                            RichPathAnimator.animate(orgRichPath)
                                    .fillColor(Color.BLACK)
                                    .start();

                            richPath = richPathView.findRichPathByName(city.name.split("_")[0]);
                            RichPathAnimator.animate(richPath)
                                    .fillColor(Color.WHITE)
                                    .start();
                        }
                        else{
                            cardView.animate()
                                    .alpha(0.0f)
                                    .setDuration(300);

                            linearLayout.animate()
                                    .translationY(cardView.getHeight() * (-1))
                                    .setDuration(300);
                            RichPathAnimator.animate(orgRichPath)
                                    .fillColor(Color.WHITE)
                                    .start();
                            richPath = richPathView.findRichPathByName(city.name.split("_")[0]);
                            RichPathAnimator.animate(richPath)
                                    .fillColor(Color.BLACK)
                                    .start();
                        }
                    }else {
                        richPath = richPathView.findRichPathByName(city.getName());
                        RichPathAnimator.animate(richPath)
                                .fillColor(Color.WHITE)
                                .start();

                        richPath = richPathView.findRichPathByName(city.getName().split("_")[0]);
                        RichPathAnimator.animate(richPath)
                                .fillColor(Color.BLACK)
                                .start();
                    }
                }
                Log.d("SampleMap", "fifth");
            }
        });

        return view;

    }

}
