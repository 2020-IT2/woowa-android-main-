package com.woowahan.woowahanfoods.FeedViewer.Fragment;

import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraPosition;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.Align;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.util.MarkerIcons;
import com.woowahan.woowahanfoods.Address.Fragment.AddressMap;
import com.woowahan.woowahanfoods.R;

public class RestaurantMap extends Fragment implements OnMapReadyCallback {
    public String name;
    public String address;
    public float lat;
    public float lon;

    private androidx.appcompat.widget.Toolbar toolbar;
    private ActionBar actionbar;

    public TextView tv_jibun;
    public TextView tv_road;
    private MapView mapView;

    public static RestaurantMap newInstance(String name,String address, float lat, float lon) {
        Bundle adr = new Bundle();
        adr.putString("name", name);
        adr.putString("address", address);
        adr.putFloat("lat", lat);
        adr.putFloat("lon", lon);
        RestaurantMap restaurantMap = new RestaurantMap();
        restaurantMap.setArguments(adr);
        return restaurantMap;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_address_map, container, false);

        Bundle bundle = getArguments();
        if (bundle != null) {
            this.name = bundle.getString("name");
            this.address = bundle.getString("address");
            this.lat = bundle.getFloat("lat");
            this.lon = bundle.getFloat("lon");
        }

        setHasOptionsMenu(true);
        toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        actionbar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionbar.setDisplayShowCustomEnabled(true);
        actionbar.setDisplayShowTitleEnabled(false);//기본 제목을 없애줍니다.
        actionbar.setDisplayHomeAsUpEnabled(true);

        Button btn = view.findViewById(R.id.btn_complete);
        tv_jibun = view.findViewById(R.id.tv_jibun);
        tv_road = view.findViewById(R.id.tv_road);

        btn.setText("확인 완료");
        tv_jibun.setText(this.address);
        tv_road.setText("road");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        //네이버 지도
        mapView = (MapView) view.findViewById(R.id.map_view);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        return view;
    }

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        naverMap.setCameraPosition(new CameraPosition(new LatLng(lat, lon), 15));
        Marker marker = new Marker();
        marker.setIcon(MarkerIcons.BLACK);
        marker.setIconTintColor(Color.RED);
        marker.setPosition(new LatLng(lat, lon));
        marker.setCaptionText(this.name);
        marker.setCaptionRequestedWidth(200);
        marker.setCaptionAlign(Align.Top);
        marker.setMap(naverMap);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //select back button
                getActivity().onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
