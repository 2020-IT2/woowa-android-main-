package com.woowahan.woowahanfoods.Market.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.richpath.RichPathView;
import com.woowahan.woowahanfoods.DataModel.City;
import com.woowahan.woowahanfoods.R;

import java.util.ArrayList;

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


        return view;

    }

}
