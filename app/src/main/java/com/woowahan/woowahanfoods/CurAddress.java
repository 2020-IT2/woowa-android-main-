package com.woowahan.woowahanfoods;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.util.FusedLocationSource;

import java.io.IOException;
import java.util.List;


public class CurAddress extends Fragment implements OnMapReadyCallback {
    private TextView tv_address;
    private MapView mapView;
    private NaverMap naverMap;

    private androidx.appcompat.widget.Toolbar toolbar;
    private ActionBar actionbar;

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;
    private FusedLocationSource locationSource; //위치를 반환하는 구현체
    private ImageView img_btn;

    private double lat, lon;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cur_address, container, false);

        tv_address = view.findViewById(R.id.tv_address);

        //네이버 지도
        mapView = view.findViewById(R.id.map_view);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        locationSource =
                new FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE);

        ImageView img_btn = view.findViewById(R.id.back_btn);
        img_btn.bringToFront();
        img_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });


        return view;
    }

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        naverMap.setLocationSource(locationSource);
        naverMap.setLocationTrackingMode(LocationTrackingMode.Follow);

        naverMap.addOnLocationChangeListener(new NaverMap.OnLocationChangeListener() {
            @Override
            public void onLocationChange(@NonNull Location location) {
                // 위치가 변경되면 다음의 코드들이 수행된다.
                //좌표 받아
                //lat = location.getLatitude();
                //lon = location.getLongitude();
                if(getActivity()==null){
                    return;
                }
                final Geocoder geocoder = new Geocoder(getActivity());
                try {
                    List<Address> mResultList = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                    Log.d("Tag", "complete" + mResultList.get(0).getAddressLine(0));
                    String roadaddress = mResultList.get(0).getAddressLine(0);
                    tv_address.setText(roadaddress);
                    //Log.d("hello","mResultList.get(0).getAddressLine(0)"+mResultList.get(0).getAddressLine(0));
                    //Log.d("hello","mResultList.get(0).getAddressLine(0)"+mResultList.get(0).getAddressLine(1));
                    //Log.d("hello","mResultList.get(0).getAddressLine(0)"+mResultList.get(0).getAddressLine(2));
                } catch (IOException e){
                    e.printStackTrace();
                    Log.d("Tag", "주소변환 실패");
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(locationSource.onRequestPermissionsResult(
                requestCode, permissions, grantResults)){
            if(!locationSource.isActivated()) {//권한 거부됨
                naverMap.setLocationTrackingMode(LocationTrackingMode.None);
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
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

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        return super.onContextItemSelected(item);
    }



}