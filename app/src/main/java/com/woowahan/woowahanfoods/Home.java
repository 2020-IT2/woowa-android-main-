package com.woowahan.woowahanfoods;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.woowahan.woowahanfoods.Adapter.viewPageAdapter;
import com.woowahan.woowahanfoods.Dataframe.FeedResult;
import com.woowahan.woowahanfoods.Dataframe.ImageData;
import com.woowahan.woowahanfoods.httpConnection.RetrofitAdapter;
import com.woowahan.woowahanfoods.httpConnection.RetrofitService;

import java.util.ArrayList;

import retrofit2.Call;

public class Home extends Fragment  {
    private static final int DP = 24;
    public String fields = "media_url";
    public String access_token = "IGQVJYVFRlT1VRMDg3QWRQQ3ZAOTGNlbGQ2VGE3YWVvYUNaLWF0MWJaZA1VvQjlwX3lPZAEF3cHVvZAXEzWlhzOHMwSG10SXpmX1lPYXlldGpYZAFhCZAS1aSVR6WTN1ci1oU2FyV2V6OXl3";
    public String sampleFeedID = "17922299383454246";
    public viewPageAdapter adapter;
    public viewPageAdapter adapter2;
    public ArrayList<ImageData> imageDataList;

    public ArrayList<ImageButton> img_btns = new ArrayList<ImageButton>();
    public int [] icon_nams = new int[]{
            R.id.ib_icon1, R.id.ib_icon2, R.id.ib_icon3, R.id.ib_icon4,
            R.id.ib_icon5, R.id.ib_icon6, R.id.ib_icon7, R.id.ib_icon8,
            R.id.ib_icon9, R.id.ib_icon10, R.id.ib_icon11, R.id.ib_icon12
    };

    private LinearLayout search, address;
    private TextView tv_homeaddress;
    private String homeaddress;

    private String road;

    public static Home newInstance(String road) {
        Bundle adr = new Bundle();
        adr.putString("road", road);
        Home home = new Home();
        home.setArguments(adr);
        return home;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        search = view.findViewById(R.id.search);
        address = view.findViewById(R.id.address);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).replaceFragmentFull(new Search());
            }
        });
        address.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).replaceFragmentFull(new Address());
            }
        });

        tv_homeaddress = view.findViewById(R.id.tv_homeaddress);
        Bundle bundle = getArguments();
        if(bundle!=null){
            this.homeaddress = bundle.getString("road");
            tv_homeaddress.setText(homeaddress);
        }

        for(int id=0; id<icon_nams.length; id++ ){
            ImageButton btn = (ImageButton)view.findViewById(icon_nams[id]);
            final int idx = id;

            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((MainActivity)getActivity()).replaceFragmentFull(RestaurantList.newInstance(idx));
                }
            });
            img_btns.add(btn);

        }

        imageDataList = new ArrayList<>();


        ViewPager viewPager = view.findViewById(R.id.viewPager);
        viewPager.setClipToPadding(false);
        ViewPager viewPager2 = view.findViewById(R.id.viewPager2);
        viewPager2.setClipToPadding(false);

//        float density = getResources().getDisplayMetrics().density;
//        int margin = (int) (DP * density);
//        viewPager.setPadding(margin, 0, margin, 0);
//        viewPager.setPageMargin(margin/2);
        adapter = new viewPageAdapter(getContext(), imageDataList);
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tab_lay);
        tabLayout.setupWithViewPager(viewPager, true);

        adapter2 = new viewPageAdapter(getContext(), imageDataList);
        viewPager2.setAdapter(adapter2);

        TabLayout tabLayout2 = (TabLayout) view.findViewById(R.id.tab_layout2);
        tabLayout2.setupWithViewPager(viewPager2, true);

        getPhotos();

        return view;
    }

    public void getPhotos(){
        // Retrofit 삽질 4시간 경험 한 후기
        // 1. Retrofit 의 Service는 BaseURL과는 연관이 없다.
        // 2. Retrofit은 여러개를 만들어서 사용해도 상관 없다.
        // 3. Retrofit의 Parameter는 반드시 URL encoded 되지 않은것으로 사용해야한다.
        // 4. Retrofit의 Parameter는 절대 getString(R.string.name) 으로 가지고 온것을 사용하면 안된다.
        RetrofitService service = RetrofitAdapter.getInstance("https://graph.instagram.com/", getContext());
        Call<FeedResult> call = service.getFeedDetails(sampleFeedID, fields, access_token);

        call.enqueue(new retrofit2.Callback<FeedResult>() {
            @Override
            public void onResponse(Call<FeedResult> call, retrofit2.Response<FeedResult> response) {
                if (response.isSuccessful()) {
                    Log.d("GridViewAdapter", "onResponse: Success " + response.body().data.size());
                    imageDataList.clear();
                    imageDataList.addAll(response.body().data);
                    adapter.refresh(imageDataList); //pass update list
                    adapter2.refresh(imageDataList); //pass update list
                } else {
                    Log.d("GridViewAdapter", "onResponse: Fail " + response.body());
                }
            }

            @Override
            public void onFailure(Call<FeedResult> call, Throwable t) {
                Toast.makeText(getContext(), "Please reloading", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
