package com.woowahan.woowahanfoods.Market.Fragment;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.sql.Date;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
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
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.DefaultAxisValueFormatter;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.renderer.YAxisRenderer;
import com.google.android.gms.common.util.ArrayUtils;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.naver.maps.map.MapView;
import com.richpath.RichPath;
import com.richpath.RichPathView;
import com.richpathanimator.RichPathAnimator;
import com.woowahan.woowahanfoods.DataModel.City;
import com.woowahan.woowahanfoods.DataModel.Population;
import com.woowahan.woowahanfoods.MainActivity;
import com.woowahan.woowahanfoods.R;

import java.util.ArrayList;

public class Market extends Fragment {
    Spinner spinner;
    private String[] chartPointColor = new String[]{"#FF0000", "#FF9100", "#FFE650", "#54BD54", "#52E4DC", "#46649B", "#C1AEEE", "#FFB2AF", "#D68642", "#b232b2"};
    private String[] chartLineColor = new String[]{"#FF0000", "#FF9100", "#FFE650", "#54BD54", "#52E4DC", "#46649B", "#C1AEEE", "#FFB2AF", "#D68642", "#b232b2"};
    private String chart2PointColor = "#72c0cc";
    private String chart2LineColor = "#72c0cc";
    private int selectedColor = 0xff008EDC;
    private LineChart lineChart;
    private LineChart lineChart2;

    final public ArrayList<City> cityArrayList = new ArrayList<City>();
    public HashMap<String, ArrayList<Population>> populations;
    public HashMap<String, HashMap<String, Float>> marketInfos;
    public String compareCityName;
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
    public int [] guList = new int[]{
            R.raw.gangbuk, R.raw.gangdong, R.raw.gangnam, R.raw.gemcheon, R.raw.guro,
            R.raw.dobong, R.raw.dongdaemun, R.raw.dongjak, R.raw.enpyung, R.raw.jongro,
            R.raw.jung, R.raw.jungrang, R.raw.kangseo, R.raw.kwanak, R.raw.kwangjin,
            R.raw.mapo, R.raw.nowon, R.raw.seocheo, R.raw.seodaemon, R.raw.seongbuk,
            R.raw.seongdong, R.raw.songpa, R.raw.yangcheon, R.raw.yongdengpo, R.raw.yongsan
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

    private void jsonParsing2(){
        try{

            InputStream ins = getResources().openRawResource(R.raw.market);
            Writer writer = new StringWriter();
            char[] buffer = new char[1024];
            try {
                Reader reader = new BufferedReader(new InputStreamReader(ins, "UTF-8"));
                int n;
                while ((n = reader.read(buffer)) != -1) {
                    writer.write(buffer, 0, n);
                }
            } catch (IOException e){
                e.printStackTrace();
            }
            finally {
                try {
                    ins.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            String jsonString = writer.toString();
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            marketInfos = (HashMap<String, HashMap<String, Float>>)gson.fromJson(jsonString, new TypeToken<HashMap<String, HashMap<String, Float>>>() {
            }.getType());
            marketInfos.put("선택해주세요.", new HashMap<String, Float>());



        }catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void jsonParsing(){
        try{

            InputStream ins = getResources().openRawResource(R.raw.jieun);
            Writer writer = new StringWriter();
            char[] buffer = new char[1024];
            try {
                Reader reader = new BufferedReader(new InputStreamReader(ins, "UTF-8"));
                int n;
                while ((n = reader.read(buffer)) != -1) {
                    writer.write(buffer, 0, n);
                }
            } catch (IOException e){
                e.printStackTrace();
            }
            finally {
                try {
                    ins.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            String jsonString = writer.toString();
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            populations=(HashMap<String, ArrayList<Population>>)gson.fromJson(jsonString, new TypeToken<HashMap<String, ArrayList<Population>>>() {
            }.getType());



        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jsonParsing(int idx){
        try{
            Region_List.clear();


            InputStream ins = getResources().openRawResource(guList[idx]);
            Writer writer = new StringWriter();
            char[] buffer = new char[1024];
            try {
                Reader reader = new BufferedReader(new InputStreamReader(ins, "UTF-8"));
                int n;
                while ((n = reader.read(buffer)) != -1) {
                    writer.write(buffer, 0, n);
                }
            } catch (IOException e){
                e.printStackTrace();
            }
            finally {
                try {
                    ins.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            String jsonString = writer.toString();
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            Region_List.clear();
            Region_List.addAll((List<Region>)gson.fromJson(jsonString, new TypeToken<List<Region>>() {
            }.getType()));

        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    private MapView mMapView;
    public List<Region> Region_List = new ArrayList<Region>();
    public String json_data = new String();

    public ArrayList<ILineDataSet> dataSets;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_market, container, false);
        view.setClickable(true);
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

        jsonParsing2();

        // spinner로 food category 구현
        spinner = view.findViewById(R.id.foodcategory_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.category, R.layout.spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(R.layout.spinner_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String [] food_type = new String[]{"선택해주세요.", "한식", "중식", "분식", "양식", "일식/수산물", "패스트푸드", "닭/오리요리", "별식/퓨전요리", "제과제빵떡케익"};
                List<Float> a = new ArrayList<Float>(marketInfos.get(food_type[position]).values());
                textView.setText(food_type[position]);
                if(position == 0){
                    for (String name : cityNames){
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            RichPathAnimator.animate(richPathView.findRichPathByName(name))
                                    .fillColor(Color.parseColor("#e6e6e6"))
                                    .start();
                        }
                    }
                    return;
                }
                Collections.sort(a);
                float range = a.get(a.size()-1) - a.get(0);
                for (String name : cityNames){
                    float per;
                    if (marketInfos.get(food_type[position]).containsKey(name.split("_")[0]))
                        per = (marketInfos.get(food_type[position]).get(name.split("_")[0]) - a.get(0) ) / range * 100.0f;
                    else
                        per = 50;
                    if (per < 10)
                        per = 10;
                    if (per >= 99)
                        per = 99;
                    Log.d("Market", "per: " + "#"+String.valueOf((int)per)+"3456");
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        RichPathAnimator.animate(richPathView.findRichPathByName(name))
                                .fillColor(Color.parseColor("#"+String.valueOf((int)per)+"123456"))
                                .start();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


//        ArrayAdapter arrayAdapter;
//        arrayAdapter = new ArrayAdapter<>(getContext(), R.layout.spinner_item, getActivity().getResources().getStringArray(R.array.category));
//        arrayAdapter.setDropDownViewResource(R.layout.spinner_item);
//        spinner.setAdapter(arrayAdapter);

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
        lineChart2 = view.findViewById(R.id.lineChart2);
        jsonParsing();
        richPathView.setOnPathClickListener(new RichPath.OnPathClickListener() {
            @Override
            public void onClick(RichPath richPath) {
                RichPath orgRichPath = richPath;
                String name = richPath.getName();
                Log.d("SampleMap", "name : " + name);
                int i=0;
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
                                    .fillColor(selectedColor)
                                    .start();
                            if(textView.getVisibility() == View.INVISIBLE)
                                textView.setVisibility(View.VISIBLE);
                            compareCityName = city.getName().split("_")[0];
                            textView.setText(compareCityName);
                            json_data = getJsonString(compareCityName);
                            Log.d("SampleMap2", "i : " + i);
                            jsonParsing(i);
                            draw_graph(Region_List);
                            draw_graph();
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
                    i++;
                }
                Log.d("SampleMap", "fifth");
            }
        });

        return view;
    }

    private void draw_graph(List<Region> region_list){
        Log.d("Market", "hello im in draw_graph");
        final ArrayList<String> xAxislabels = new ArrayList<String>();

        LineData data = new LineData();
        HashSet<String> dongHash = new HashSet<String>();
        for (int i =0; i < region_list.size(); i++) {
            if(Integer.parseInt(region_list.get(i).getDate()) > 20200906){
                String dongname = region_list.get(i).getRegion();
                dongHash.add(dongname);
                xAxislabels.add(region_list.get(i).getDate());
            }
        }

        int q = 0;

        for (String e : dongHash) {
            ArrayList<Entry> yentries = new ArrayList<>();
            int j = 0;
            for (int i =0; i < region_list.size(); i++) {
                if(Integer.parseInt(region_list.get(i).getDate()) > 20200906){
                    int yval = region_list.get(i).getValue();
                    String dongname = region_list.get(i).getRegion();
                    if (dongname.equals(e))
                        yentries.add(new Entry(j++, yval));
                }
            }
            LineDataSet dataset = new LineDataSet(yentries, e);
            data.addDataSet(dataset);
            dataset.setColors(android.R.color.black);
            dataset.setColor(Color.parseColor(chartLineColor[q]));
            dataset.setCircleColor(Color.parseColor(chartPointColor[q++]));
            dataset.setDrawCircles(false);
            dataset.setLineWidth(2);
//        dataset.setDrawFilled(true); //차트 아래 색 채우기
//            dataset.setFillColor(chartLineColor); //차트 아래 색 설정
        }


// 꾸미기
        lineChart.setBackgroundColor(Color.WHITE);
        lineChart.setPinchZoom(false);

//label
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM); //라벨링 아래에
        xAxis.setDrawGridLines(false);

//xAxis.setTextColor(Color.BLACK); //글씨색 설정
        YAxis yLAxis = lineChart.getAxisLeft();
//yLAxis.setTextColor(Color.BLACK); //글씨색 설정
        YAxis yRAxis = lineChart.getAxisRight();
        yRAxis.setDrawGridLines(false);
        yRAxis.setDrawLabels(false);
        yRAxis.setDrawAxisLine(false);
//yRAxis.setDrawGridLines(false);
        Description description = new Description();
        description.setText("");
        lineChart.setDescription(description);
//set data
        lineChart.setData(data);
        lineChart.invalidate();
    }

    private void draw_graph(){
        Log.d("compareCityName", "compareCityName"+compareCityName);
        ArrayList<Population> population = populations.get(compareCityName);

        final ArrayList<String> xAxislabels = new ArrayList<String>();
        LineData data = new LineData();
        int q = 0;

        ArrayList<Entry> yentries = new ArrayList<>();
        for (int i =0; i < population.size(); i++) {
            float yval = population.get(i).val;
            int xval = Integer.parseInt(population.get(i).date) % 10000;
            yentries.add(new Entry(i, yval));

        }
        LineDataSet dataset = new LineDataSet(yentries, compareCityName);

        dataset.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        dataset.setCubicIntensity(0.2f);
        dataset.setDrawFilled(true);
        dataset.setDrawCircles(false);
        dataset.setLineWidth(1.8f);
        dataset.setFillAlpha(100);
        dataset.setDrawHorizontalHighlightIndicator(false);


        data.addDataSet(dataset);
        dataset.setColors(android.R.color.black);
        dataset.setColor(0x696969);
        dataset.setDrawCircles(false);
        dataset.setLineWidth(2);
        dataset.setDrawFilled(true); //차트 아래 색 채우기
        dataset.setFillColor(0xC3DDF1);


// 꾸미기
        lineChart2.setDrawGridBackground(false);
        lineChart2.setMaxHighlightDistance(300);


        lineChart2.setBackgroundColor(Color.WHITE);
        lineChart2.setPinchZoom(false);

//label
        XAxis xAxis = lineChart2.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM); //라벨링 아래에
        xAxis.setDrawGridLines(false);

//xAxis.setTextColor(Color.BLACK); //글씨색 설정
        YAxis yLAxis = lineChart2.getAxisLeft();
        yLAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return String.valueOf(Math.round(value)) + " 명";
            }
        });
//yLAxis.setTextColor(Color.BLACK); //글씨색 설정
        YAxis yRAxis = lineChart2.getAxisRight();
        yRAxis.setDrawLabels(false);
        yRAxis.setDrawAxisLine(false);
        yRAxis.setDrawGridLines(false);
        Description description = new Description();
        description.setText("");
        lineChart2.setDescription(description);
//set data
        lineChart2.setData(data);
        lineChart2.invalidate();
    }
}





