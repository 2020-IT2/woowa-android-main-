package com.woowahan.woowahanfoods;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraPosition;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.Overlay;
import com.naver.maps.map.overlay.PolygonOverlay;
import com.woowahan.woowahanfoods.Dataframe.CoordinateFrame;
import com.woowahan.woowahanfoods.DataModel.MyAddress;
import com.woowahan.woowahanfoods.Dataframe.User;
import com.woowahan.woowahanfoods.Home.Fragment.Home;
import com.woowahan.woowahanfoods.Market.Fragment.Market;
import com.woowahan.woowahanfoods.Statistic.Fragment.Statistic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {
    private BottomNavigationView navigation;
    Intent intent;
    Fragment statistic = new Statistic();
    Fragment home = new Home();
    Fragment market = new Market();
    public User user;
    public ArrayList<User> userData;


    private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.item_home:
                    replaceFragment(home);
                    return true;
                case R.id.item_statistic:
                    replaceFragment(statistic);
                    return true;
                case R.id.item_market:
                    replaceFragment(market);
                    return true;
            }
            return false;
        }
    };

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        naverMap.setCameraPosition(new CameraPosition(new LatLng(37.57152, 126.97714), 10));
        InputStream ins = getResources().openRawResource(R.raw.hello);
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

        ArrayList<com.woowahan.woowahanfoods.Dataframe.CoordinateFrame> result;
        result = gson.fromJson(jsonString, new TypeToken<ArrayList<com.woowahan.woowahanfoods.Dataframe.CoordinateFrame>>() {
        }.getType());

        for (com.woowahan.woowahanfoods.Dataframe.CoordinateFrame area : result) {
            PolygonOverlay polygonOverlay = new PolygonOverlay();
            List<LatLng> list = new ArrayList<LatLng>();
            Log.d("map", "area: " + area.name);
            for (CoordinateFrame.Coordinate coor : area.coor){
                list.add(new LatLng(coor.x, coor.y));
            }
            polygonOverlay.setCoords(list);
            polygonOverlay.setColor(Color.GREEN);
            polygonOverlay.setOutlineWidth(5);
            polygonOverlay.setOutlineColor(Color.RED);
            polygonOverlay.setMap(naverMap);
            polygonOverlay.setOnClickListener(new Overlay.OnClickListener() {
                @Override
                public boolean onClick(@NonNull Overlay overlay) {
                    Log.d("map", "hello");
                    return true;
                }
            });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //load 유저 info
        SharedPreferences sharedPreferences = getSharedPreferences("pref", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String userJson = sharedPreferences.getString("user", "");

        if(userJson.equals("")){
            user = new User();
            ArrayList<MyAddress> myaddresses= new ArrayList<>();
            MyAddress myAddress = new MyAddress("위치를 입력해주세요.", "", 0, 0);
            user.myAddresses = myaddresses;
            user.myAddresses.add(0, myAddress);
            user.curAddress = myAddress;
            user.curDong = "중구";

        } else {
            user = gson.fromJson(userJson, User.class);
            if(user.myAddresses.size()==0) {
                MyAddress myAddress = new MyAddress("위치를 입력해주세요.", "", 0, 0);
                user.myAddresses.add(0, myAddress);
                user.curAddress = myAddress;
                user.curDong = "중구";
            } else {
                user.curDong = user.curAddress.gu;
//                Log.d("MainActivity", "lat : " + user.myAddresses.get(0).latitude);
//                Log.d("MainActivity", "lon : " + user.myAddresses.get(0).longitude);
            }
        }

        View view = getWindow().getDecorView();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (view != null) {
                // 23 버전 이상일 때 상태바 하얀 색상에 회색 아이콘 색상을 설정
                view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                getWindow().setStatusBarColor(Color.parseColor("#f2f2f2"));
            }
        }else if (Build.VERSION.SDK_INT >= 21) {
            // 21 버전 이상일 때
            getWindow().setStatusBarColor(Color.BLACK);
        }
        setContentView(R.layout.main_activity_layout);
        navigation = findViewById(R.id.nav_bar);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.item_home);
    }


    public void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_main, fragment);
        FragmentManager manager = getSupportFragmentManager();
        manager.popBackStackImmediate();
        transaction.commit();
    }

    public void replaceFragmentFull(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void removeAddress(int position){
        SharedPreferences pref = getSharedPreferences("pref", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = pref.edit();
        user.myAddresses.remove(position);
        Gson gson = new Gson();
        String userJson = gson.toJson(user);
        editor.putString("user", userJson);
        editor.commit();
    }

    public void setCurAddress(int position){
        SharedPreferences pref = getSharedPreferences("pref", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = pref.edit();
        user.curAddress = user.myAddresses.get(position);
        user.curDong = user.curAddress.gu;
        Gson gson = new Gson();
        String userJson = gson.toJson(user);
        editor.putString("user", userJson);
        editor.commit();
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}