package com.woowahan.woowahanfoods;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.android.material.tabs.TabLayout;
import com.naver.maps.map.MapView;
import com.richpath.RichPath;
import com.richpath.RichPathView;
import com.richpathanimator.RichPathAnimator;

import java.util.ArrayList;

import im.dacer.androidcharts.LineView;

public class Market extends Fragment {
    private LineView lineView;
    private LineChart lineChart;
    final public ArrayList<City> cityArrayList = new ArrayList<City>();
    public CardView cardView;
    public TabLayout tabLayout;
    public LinearLayout linearLayout;
    private TabLayout mTabLayout;
    public CardView market_card;
    public CardView people_card;
    public TextView market_text;
    public TextView people_text;
    public String [] cityNames = new String[]{
            "강서구_map", "양천구_map", "구로구_map", "금천구_map", "관악구_map",
            "서초구_map", "강남구_map", "송파구_map", "강동구_map", "광진구_map",
            "중랑구_map", "노원구_map", "도봉구_map", "강북구_map", "성북구_map",
            "종로구_map", "은평구_map", "마포구_map", "영등포구_map", "동작구_map",
            "용산구_map", "성동구_map", "동대문구_map", "서대문구_map", "중구_map"
    };
    public static final int whitegray = 0xFFE6E6E6;
    private final static String TAG = MainActivity.class.getSimpleName();

    private MapView mMapView;


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_market, container, false);
        final RichPathView richPathView = view.findViewById(R.id.market_map);
        final TextView textView = view.findViewById(R.id.locations);
        market_card = view.findViewById(R.id.market_card);
        people_card = view.findViewById(R.id.people_card);
        market_text = view.findViewById(R.id.market_text);
        people_text = view.findViewById(R.id.people_text);
        final FrameLayout market_frame = view.findViewById(R.id.market_size);
        final FrameLayout people_frame = view.findViewById(R.id.people);
        textView.bringToFront();
        for(String cityName : cityNames){
            cityArrayList.add(new City(cityName));
        }
        ImageView button = view.findViewById(R.id.btn);

        market_card.setOnClickListener(new CardView.OnClickListener() {
            @Override
            public void onClick(View v) {
                market_text.setBackgroundColor(0);
                market_text.setTextColor(0xFFFFFFFF);

                people_text.setBackgroundColor(0xFFFFFFFF);
                people_text.setTextColor(0);

                market_frame.setVisibility(View.VISIBLE);
                people_frame.setVisibility(View.INVISIBLE);
            }
        });

        market_text.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View v) {
                market_text.setBackgroundColor(0);
                market_text.setTextColor(0xFFFFFFFF);

                people_text.setBackgroundColor(0xFFFFFFFF);
                people_text.setTextColor(0);

                market_frame.setVisibility(View.VISIBLE);
                people_frame.setVisibility(View.INVISIBLE);
            }
        });

        people_card.setOnClickListener(new CardView.OnClickListener() {
            @Override
            public void onClick(View v) {
                people_text.setBackgroundColor(0);
                people_text.setTextColor(0xFFFFFFFF);

                market_text.setBackgroundColor(0xFFFFFFFF);
                market_text.setTextColor(0);
                market_frame.setVisibility(View.INVISIBLE);
                people_frame.setVisibility(View.VISIBLE);
            }
        });
        people_text.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View v) {
                people_text.setBackgroundColor(0);
                people_text.setTextColor(0xFFFFFFFF);

                market_text.setBackgroundColor(0xFFFFFFFF);
                market_text.setTextColor(0);
                market_frame.setVisibility(View.INVISIBLE);
                people_frame.setVisibility(View.VISIBLE);
            }
        });


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
                            RichPathAnimator.animate(orgRichPath)
                                    .fillColor(0xff090090)
                                    .start();
                            if(textView.getVisibility() == View.INVISIBLE)
                                textView.setVisibility(View.VISIBLE);
                            textView.setText(city.getName().split("_")[0]);
                            richPath = richPathView.findRichPathByName(city.name.split("_")[0]);
                            RichPathAnimator.animate(richPath)
                                    .fillColor(Color.WHITE)
                                    .start();
                        }
                        else{
                            RichPathAnimator.animate(orgRichPath)
                                    .fillColor(whitegray)
                                    .start();
                            richPath = richPathView.findRichPathByName(city.name.split("_")[0]);
                            RichPathAnimator.animate(richPath)
                                    .fillColor(Color.BLACK)
                                    .start();

                            textView.setVisibility(View.INVISIBLE);
                        }
                    }else {
                        richPath = richPathView.findRichPathByName(city.getName());
                        RichPathAnimator.animate(richPath)
                                .fillColor(whitegray)
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
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).replaceFragmentFull(new SampleMap());
            }
        });

        //lineView.setBottomTextList();
        //lineView.setDataList();



        ArrayList<Entry> entry_chart = new ArrayList<>();


        lineChart = view.findViewById(R.id.lineChart);
        LineData chartData = new LineData();

        entry_chart.add(new Entry(2, 3));
        entry_chart.add(new Entry(5, 6));
        entry_chart.add(new Entry(3, 4));

        LineDataSet lineDataSet = new LineDataSet(entry_chart, "방문자수");
        lineDataSet.setLineWidth(4);
        lineDataSet.setCircleRadius(6);
        lineDataSet.setCircleColor(Color.parseColor("#FFA1B4DC"));
        //lineDataSet.setCircleHoleColor(Color.BLUE);
        lineDataSet.setColor(Color.parseColor("#FFA1B4DC"));
        lineDataSet.setDrawCircleHole(true);
        lineDataSet.setDrawCircles(true);
        lineDataSet.setDrawHorizontalHighlightIndicator(false);
        lineDataSet.setDrawHighlightIndicators(false);
        lineDataSet.setDrawValues(false);

        chartData.addDataSet(lineDataSet);

        lineChart.setData(chartData);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextColor(Color.BLACK);
        xAxis.setDrawGridLines(false);
        //xAxis.enableGridDashedLine(5, 24, 0);


        YAxis yLAxis = lineChart.getAxisLeft();
        yLAxis.setTextColor(Color.BLACK);
        //yLAxis.setDrawGridLines(true);
        yLAxis.enableGridDashedLine(8, 24, 0);


        YAxis yRAxis = lineChart.getAxisRight();
        yRAxis.setDrawLabels(false);
        yRAxis.setDrawAxisLine(false);
        yRAxis.setDrawGridLines(false);

        Description description = new Description();
        description.setText("");

        lineChart.setDescription(description);


        lineChart.invalidate();


        return view;
    }

}
