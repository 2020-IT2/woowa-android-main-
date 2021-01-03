package com.woowahan.woowahanfoods.Statistic.Fragment;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.naver.maps.map.MapView;

import com.richpath.RichPath;
import com.richpath.RichPathView;
import com.richpathanimator.RichPathAnimator;
import com.woowahan.woowahanfoods.DataModel.City;
import com.woowahan.woowahanfoods.Dataframe.DiningCodeBest3;
import com.woowahan.woowahanfoods.MainActivity;
import com.woowahan.woowahanfoods.Market.Fragment.Region;
import com.woowahan.woowahanfoods.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    // 서울시 & 구별 Best3
    public TextView tv_seoul_top1;
    public TextView tv_seoul_top2;
    public TextView tv_seoul_top3;
    public ImageView iv_gu_top1;
    public ImageView iv_gu_top2;
    public ImageView iv_gu_top3;
    public TextView tv_gu_top1;
    public TextView tv_gu_top2;
    public TextView tv_gu_top3;
    public String[] imgUrl = new String[3];
    public String[] rank = new String[3];
    public String[] restaurantName = new String[3];
    public int i = 0;



    private MapView mMapView;

    // Json 파일
    public Map<String, List<DiningCodeBest3>> Best3_List;


    private String getJsonString(String region){
        String json = "";

        try {
            InputStream is = getActivity().getResources().openRawResource(R.raw.diningcode_gu_byul_data);
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
        try {
            if(Best3_List == null){
                InputStream ins = getResources().openRawResource(R.raw.diningcode_gu_byul_data);
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

                Best3_List=(Map<String, List<DiningCodeBest3>>)gson.fromJson(jsonString, new TypeToken<Map<String, List<DiningCodeBest3>>>() {
                }.getType());
            }

            if(json.equals("서울시")) {
                List<DiningCodeBest3> subJsonObject = Best3_List.get("서울시");
                for (int i = 0; i < 3; i++) {
                    restaurantName[i] = subJsonObject.get(i).getName();
                }
            }else {
                List<DiningCodeBest3> subJsonObject = Best3_List.get(json);
                for (int i = 0; i < 3; i++) {
                    imgUrl[i] = subJsonObject.get(i).getImgUrl();
                    rank[i] = subJsonObject.get(i).getRank();
                    restaurantName[i] = subJsonObject.get(i).getName();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_statistic, container, false);
        final RichPathView richPathView = view.findViewById(R.id.rich_map);
        final LinearLayout linearLayout = view.findViewById(R.id.lin_visible);
        final TextView textView = view.findViewById(R.id.location);
        for(String cityName : cityNames){
            cityArrayList.add(new City(cityName));
        }

        // 서울시 BEST3
        tv_seoul_top1 = view.findViewById(R.id.tv_seoul_top1);
        tv_seoul_top2 = view.findViewById(R.id.tv_seoul_top2);
        tv_seoul_top3 = view.findViewById(R.id.tv_seoul_top3);
        jsonParsing("서울시");
        tv_seoul_top1.setText(restaurantName[0]);
        tv_seoul_top2.setText(restaurantName[1]);
        tv_seoul_top3.setText(restaurantName[2]);

        // 구별 BEST3
        iv_gu_top1 = view.findViewById(R.id.iv_gu_top1);
        iv_gu_top2 = view.findViewById(R.id.iv_gu_top2);
        iv_gu_top3 = view.findViewById(R.id.iv_gu_top3);
        tv_gu_top1 = view.findViewById(R.id.tv_gu_top1);
        tv_gu_top2 = view.findViewById(R.id.tv_gu_top2);
        tv_gu_top3 = view.findViewById(R.id.tv_gu_top3);

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
                    if (name.equals(city.getName())) { //선택한 구
                        Log.d("SampleMap", "1st IF");
                        if(orgRichPath.getFillColor() == whitegray){ //선택하면 색 변경
                            Log.d("SampleMap", "2nd IF");
                            RichPathAnimator.animate(orgRichPath)
                                    .fillColor(selected)
                                    .start();
                            linearLayout.setVisibility(View.VISIBLE); //레이아웃 표현
                            linearLayout.animate().alpha(1.0f).setDuration(999);
                            String selectedCityName = city.getName().split("_")[0];
                            textView.setText(selectedCityName); //글자 표현
                            jsonParsing(selectedCityName);
                            MultiTransformation multiOption = new MultiTransformation(new CenterCrop(), new RoundedCorners(45));
                            Glide.with(getContext()).load(imgUrl[0]).apply(RequestOptions.bitmapTransform(multiOption)).into(iv_gu_top1);
                            Glide.with(getContext()).load(imgUrl[1]).apply(RequestOptions.bitmapTransform(multiOption)).into(iv_gu_top2);
                            Glide.with(getContext()).load(imgUrl[2]).apply(RequestOptions.bitmapTransform(multiOption)).into(iv_gu_top3);

                            tv_gu_top1.setText(restaurantName[0]);
                            tv_gu_top2.setText(restaurantName[1]);
                            tv_gu_top3.setText(restaurantName[2]);
                            richPath = richPathView.findRichPathByName(city.name.split("_")[0]);
                            RichPathAnimator.animate(richPath)
                                    .fillColor(white) //글자는 하얗게
                                    .start();
                        }
                        else{ //선택했는데 파란색 -> 닫아줌
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
                    }else { //다른 지역 선택
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
