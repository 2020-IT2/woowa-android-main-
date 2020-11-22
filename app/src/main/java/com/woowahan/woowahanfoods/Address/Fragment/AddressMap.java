package com.woowahan.woowahanfoods.Address.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.geometry.Utmk;
import com.naver.maps.map.CameraPosition;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.Align;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.util.FusedLocationSource;
import com.naver.maps.map.util.MarkerIcons;
import com.woowahan.woowahanfoods.DataModel.MyAddress;
import com.woowahan.woowahanfoods.Dataframe.SearchResultJson;
import com.woowahan.woowahanfoods.Home.Fragment.Home;
import com.woowahan.woowahanfoods.MainActivity;
import com.woowahan.woowahanfoods.R;
import com.woowahan.woowahanfoods.httpConnection.RetrofitAdapter;
import com.woowahan.woowahanfoods.httpConnection.RetrofitService;

import retrofit2.Call;

public class AddressMap extends Fragment implements OnMapReadyCallback {
    public String jibun;
    public String road;
    public String admCd;
    public String rnMgtSn;
    public String udrtYn;
    public int buldMnnm;
    public int buldSlno;
    private TextView tv_jibun;
    private TextView tv_road;
    private Button btn_complete;
    private androidx.appcompat.widget.Toolbar toolbar;
    private ActionBar actionbar;
    private MapView mapView;

    final String key = "devU01TX0FVVEgyMDIwMTExMjAxNTE0MTExMDQwNDY=";
    final String resultType = "json";
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;
    private FusedLocationSource locationSource; //위치를 반환하는 구현체
    private static NaverMap naverMap;
    public double x;
    public double y;
    private double lat, lon;
    public AddressMap orgThis = this;


    public static AddressMap newInstance(String jibun, String road, String admCd, String rnMgtSn, String udrtYn, int buldMnnm, int buldSlno){
        Bundle adr = new Bundle();
        adr.putString("jibun", jibun);
        adr.putString("road", road);
        adr.putString("admCd", admCd);
        adr.putString("rnMgtSn", rnMgtSn);
        adr.putString("udrtYn", udrtYn);
        adr.putInt("buldMnnm", buldMnnm);
        adr.putInt("buldSlno", buldSlno);
        AddressMap addressMap = new AddressMap();
        addressMap.setArguments(adr);
        return addressMap;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_address_map, container, false);

        Context context = getActivity();
        SharedPreferences pref = context.getSharedPreferences("pref", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = pref.edit();

        setHasOptionsMenu(true);
        tv_jibun = view.findViewById(R.id.tv_jibun);
        tv_road = view.findViewById(R.id.tv_road);
        btn_complete = view.findViewById(R.id.btn_complete);

        btn_complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyAddress address = new MyAddress(jibun, road, lat, lon);
                ((MainActivity)getActivity()).user.myAddresses.add(0, address);
                ((MainActivity)getActivity()).user.curAddress = address;
                Gson gson = new Gson();
                String userJson = gson.toJson(((MainActivity)getActivity()).user);
                editor.putString("user", userJson);
                editor.commit();
                ((MainActivity)getActivity()).replaceFragmentFull(Home.newInstance(road));
            }
        });

        toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        actionbar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionbar.setDisplayShowCustomEnabled(true);
        actionbar.setDisplayShowTitleEnabled(false);//기본 제목을 없애줍니다.
        actionbar.setDisplayHomeAsUpEnabled(true);
        Bundle bundle = getArguments();
        if(bundle!=null){
            this.jibun = bundle.getString("jibun");
            this.road = bundle.getString("road");
            this.admCd = bundle.getString("admCd");
            this.rnMgtSn = bundle.getString("rnMgtSn");
            this.udrtYn = bundle.getString("udrtYn");
            this.buldMnnm = bundle.getInt("buldMnnm");
            this.buldSlno = bundle.getInt("buldSlno");
        }

        tv_jibun.setText(jibun);
        tv_road.setText(road);

        //네이버 지도
        mapView = (MapView)view.findViewById(R.id.map_view);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        locationSource =
                new FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE);
        search();
        return view;
    }

    public void search(){
        RetrofitAdapter rAdapter = new RetrofitAdapter();
        RetrofitService service = rAdapter.getInstance("http://www.juso.go.kr/", getContext());
        Call<SearchResultJson> call = service.convertor(key, admCd, rnMgtSn, udrtYn, buldMnnm, buldSlno, resultType);

        call.enqueue(new retrofit2.Callback<SearchResultJson>() {
            @Override
            public void onResponse(Call<SearchResultJson> call, retrofit2.Response<SearchResultJson> response) {
                if (response.isSuccessful()) {
                    SearchResultJson result = response.body();
                    if(result.results.juso == null){
                        return ;
                    }
                    Log.d("hello", "X" + result.results.juso.get(0).entX);
                    Log.d("hello", "Y" + result.results.juso.get(0).entY);

                    Utmk utmk = new Utmk(Double.valueOf(result.results.juso.get(0).entX), Double.valueOf(result.results.juso.get(0).entY));
                    LatLng latLng = utmk.toLatLng();
                    lat = latLng.latitude;
                    lon = latLng.longitude;
                    mapView.getMapAsync(orgThis);
                } else {
                    Log.d("Search", "onResponse: Success but parsing fails " + response.body());
                }
            }

            @Override
            public void onFailure(Call<SearchResultJson> call, Throwable t) {
                Log.d("Search", "onFailure: " + t.getMessage());
            }
        });
    }

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        naverMap.setCameraPosition(new CameraPosition(new LatLng(lat, lon), 15));
        Marker marker = new Marker();
        marker.setIcon(MarkerIcons.BLACK);
        marker.setIconTintColor(Color.RED);
        marker.setPosition(new LatLng(lat, lon));
        marker.setCaptionText("설정 주소");
        marker.setCaptionRequestedWidth(200);
        marker.setCaptionAlign(Align.Top);
        marker.setMap(naverMap);
        naverMap.addOnLocationChangeListener(new NaverMap.OnLocationChangeListener() {
            @Override
            public void onLocationChange(@NonNull Location location) {
                // 위치가 변경되면 다음의 코드들이 수행된다.
                //좌표 받아
                lat = location.getLatitude();
                lon = location.getLongitude();
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
}
