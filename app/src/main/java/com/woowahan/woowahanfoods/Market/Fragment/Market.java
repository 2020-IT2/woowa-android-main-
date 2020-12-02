package com.woowahan.woowahanfoods.Market.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import java.util.List;
import java.io.*;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
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
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.android.material.tabs.TabLayout;
import com.naver.maps.map.MapView;
import com.richpath.RichPath;
import com.richpath.RichPathView;
import com.richpathanimator.RichPathAnimator;
import com.woowahan.woowahanfoods.DataModel.City;
import com.woowahan.woowahanfoods.MainActivity;
import com.woowahan.woowahanfoods.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Market extends Fragment {
    Spinner spinner;
    private int chartLineColor = 0xff9e9fae;
    private int chartPointColor = 0xff9e9fae;
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
    public static final int blue = 0xff090090;
    public static final int black = 0xff000000;
    public static final int white = 0xFFFFFFFF;
    private final static String TAG = MainActivity.class.getSimpleName();

    private String getJsonString(String region){
        String json = "";

        try {
            InputStream is = getActivity().getResources().openRawResource(R.raw.gangnam);
                    //getAssets().open("gangnam.json");
            int fileSize = is.available();

            byte[] buffer = new byte[fileSize];
            is.read(buffer);
            is.close();

            json = new String(buffer, "UTF-8");
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }

        return json;
    }
    private void jsonParsing(String json){
        try{
            Region_List.clear();
            JSONObject jsonObject = new JSONObject(json);

            JSONArray valueArray = jsonObject.getJSONArray("values");

            for(int i=0; i<valueArray.length(); i++)
            {
                JSONObject valueObject = valueArray.getJSONObject(i);

                Region region = new Region();

                region.setDate(valueObject.getInt("result__data__period"));
                region.setValue(valueObject.getInt("result__data_value"));
                region.setRegion(valueObject.getString("result__title"));

                Region_List.add(region);
            }
        }catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private MapView mMapView;
    public List<Region> Region_List = new ArrayList<Region>();
    public String json_data = new String();

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
        spinner = view.findViewById(R.id.foodcategory_spinner);
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item,);


        ArrayAdapter monthAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.category, android.R.layout.simple_spinner_dropdown_item);
        monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(monthAdapter);

        market_card.setOnClickListener(new CardView.OnClickListener() {
            @Override
            public void onClick(View v) {
                market_text.setBackgroundColor(blue);
                market_text.setTextColor(white);

                people_text.setBackgroundColor(whitegray);
                people_text.setTextColor(black);

                market_frame.setVisibility(View.VISIBLE);
                market_frame.animate().alpha(1.0f).setDuration(500);
                people_frame.animate().alpha(0.0f).setDuration(500);
                people_frame.setVisibility(View.INVISIBLE);
            }
        });

        market_text.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View v) {
                market_text.setBackgroundColor(blue);
                market_text.setTextColor(white);

                people_text.setBackgroundColor(whitegray);
                people_text.setTextColor(black);

                market_frame.setVisibility(View.VISIBLE);
                market_frame.animate().alpha(1.0f).setDuration(500);
                people_frame.animate().alpha(0.0f).setDuration(500);
                people_frame.setVisibility(View.INVISIBLE);
            }
        });

        people_card.setOnClickListener(new CardView.OnClickListener() {
            @Override
            public void onClick(View v) {
                people_text.setBackgroundColor(blue);
                people_text.setTextColor(white);

                market_text.setBackgroundColor(whitegray);
                market_text.setTextColor(black);

                market_frame.animate().alpha(0.0f).setDuration(500);
                market_frame.setVisibility(View.INVISIBLE);
                people_frame.setVisibility(View.VISIBLE);
                people_frame.animate().alpha(1.0f).setDuration(500);
            }
        });
        people_text.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View v) {
                people_text.setBackgroundColor(blue);
                people_text.setTextColor(white);

                market_text.setBackgroundColor(whitegray);
                market_text.setTextColor(black);
                people_frame.setVisibility(View.VISIBLE);
                people_frame.animate().alpha(1.0f).setDuration(500);
                market_frame.animate().alpha(0.0f).setDuration(500);
                market_frame.setVisibility(View.INVISIBLE);

            }
        });
        lineChart = view.findViewById(R.id.lineChart);

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
                            json_data = getJsonString(city.getName().split("_")[0]);
                            jsonParsing(json_data);
                            draw_graph(Region_List);
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



        return view;
    }

    private void draw_graph(List<Region> region_list){
        ArrayList<Entry> entries = new ArrayList<>();
        for (int i =0; i < region_list.size(); i++) {
            int xval = region_list.get(i).getDate();
            Log.d("SampleMap2", "xval : " + xval);
            int yval = region_list.get(i).getValue();
            Log.d("SampleMap2", "yval : " + yval);
            entries.add(new Entry(xval, yval));
        }
        for (int i = 0; i < 10; i++) {

        }
        LineDataSet set1;
        set1 = new LineDataSet(entries, "DataSet 1");

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1); //add the data sets

        //create a data object with the data sets
        LineData data = new LineData(dataSets);

        //꾸미기
        lineChart.setBackgroundColor(Color.WHITE);
        set1.setColor(chartLineColor);
        set1.setCircleColor(chartPointColor);
        set1.setLineWidth(2);
        //set1.setDrawFilled(true); //차트 아래 색 채우기
        set1.setFillColor(chartLineColor); //차트 아래 색 설정

        //label
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM); //라벨링 아래에
        //xAxis.setTextColor(Color.BLACK); //글씨색 설정

        YAxis yLAxis = lineChart.getAxisLeft();
        //yLAxis.setTextColor(Color.BLACK); //글씨색 설정

        YAxis yRAxis = lineChart.getAxisRight();
        yRAxis.setDrawLabels(false);
        yRAxis.setDrawAxisLine(false);
        //yRAxis.setDrawGridLines(false);

        Description description = new Description();
        description.setText("");
        lineChart.setDescription(description);


        //set data
        lineChart.setData(data);


    }

}
