package com.woowahan.woowahanfoods;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

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
import com.naver.maps.map.NaverMapOptions;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.Overlay;
import com.naver.maps.map.overlay.PolygonOverlay;
import com.naver.maps.map.overlay.PolylineOverlay;
import com.woowahan.woowahanfoods.Dataframe.CoordinateFrame;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {
    private BottomNavigationView navigation;
//    public UserJson user;
    Intent intent;
    Fragment statistic = new Statistic();
    Fragment home = new Home();
    Fragment market = new Market();


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

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}