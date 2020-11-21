package com.woowahan.woowahanfoods;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.woowahan.woowahanfoods.Adapter.GridViewAdapter;
import com.woowahan.woowahanfoods.Adapter.viewPageAdapter;
import com.woowahan.woowahanfoods.Dataframe.FeedResult;
import com.woowahan.woowahanfoods.Dataframe.Restaurant;
import com.woowahan.woowahanfoods.httpConnection.RetrofitAdapter;
import com.woowahan.woowahanfoods.httpConnection.RetrofitService;

import java.util.ArrayList;

import retrofit2.Call;

public class ImageViewer extends Fragment {
    // gridView
    public ArrayList<Restaurant> imgList;
    public GridViewAdapter adapter;
    public ProgressBar progressBar;
    private androidx.appcompat.widget.Toolbar toolbar;
    private ActionBar actionbar;
    public String sampleFeedID = "17922299383454246";
    public String fields = "id,media_type,caption,media_url";
    public String access_token = "IGQVJYVFRlT1VRMDg3QWRQQ3ZAOTGNlbGQ2VGE3YWVvYUNaLWF0MWJaZA1VvQjlwX3lPZAEF3cHVvZAXEzWlhzOHMwSG10SXpmX1lPYXlldGpYZAFhCZAS1aSVR6WTN1ci1oU2FyV2V6OXl3";
    // FeedView
    public viewPageAdapter pageAdapter;
    public Restaurant imageData;
    public LinearLayout linear_view;
    public boolean togle = false;
    public ArrayList<Restaurant> imageDataList;

    public static ImageViewer newInstance(Restaurant restaurant) {
        ImageViewer fragment = new ImageViewer();
        Bundle args = new Bundle();
        Gson gson = new Gson();
        args.putString("restaurant", gson.toJson(restaurant));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image_viewer, container, false);
        view.setClickable(true);

        // 호출
        Bundle bundle = getArguments();
        if(bundle!=null){
            Gson gson = new Gson();
            this.imageData = gson.fromJson(bundle.getString("restaurant"), Restaurant.class);
        }

        // 애니메이션
        final LinearLayout linearLayout = view.findViewById(R.id.lin_visible);
        final TextView textView = view.findViewById(R.id.location);
        linear_view = view.findViewById(R.id.linear_view);
        linear_view.setClickable(true);
        linear_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (togle){
                    linearLayout.setVisibility(View.VISIBLE);
                    linearLayout.animate().alpha(1.0f).setDuration(999);
                    togle = false;
                } else {
                    linearLayout.setVisibility(View.GONE);
                    linearLayout.animate().alpha(0.0f);
                    togle = true;
                }
            }
        });


        // grid view for recommendation
        progressBar = view.findViewById(R.id.profileProgressBar);
        imgList = new ArrayList<>();
        adapter = new GridViewAdapter(
                getActivity().getApplicationContext(),
                R.layout.grid_view_item,
                imgList);
        GridView gv = (GridView)view.findViewById(R.id.gridView);
        gv.setAdapter(adapter);  // 커스텀 아답타를 GridView 에 적용
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                ((MainActivity)getActivity()).replaceFragmentFull(ImageViewer.newInstance(imgList.get(position)));

            }
        });

        // image viewer
        imageDataList = new ArrayList<>();
        ViewPager viewPager = view.findViewById(R.id.viewPager);
        viewPager.setClickable(true);
        viewPager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (togle){
                    linearLayout.setVisibility(View.VISIBLE);
                    linearLayout.animate().alpha(1.0f).setDuration(999);
                    togle = false;
                } else {
                    linearLayout.setVisibility(View.GONE);
                    linearLayout.animate().alpha(0.0f);
                    togle = true;
                }
            }
        });
        viewPager.setClipToPadding(false);
        pageAdapter = new viewPageAdapter(getContext(), imageDataList);
        viewPager.setAdapter(pageAdapter);

        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager, true);

        getFeed();
        getPhotos();

        return view;
    }

    public void getFeed(){
        // Retrofit 삽질 4시간 경험 한 후기
        // 1. Retrofit 의 Service는 BaseURL과는 연관이 없다.
        // 2. Retrofit은 여러개를 만들어서 사용해도 상관 없다.
        // 3. Retrofit의 Parameter는 반드시 URL encoded 되지 않은것으로 사용해야한다.
        // 4. Retrofit의 Parameter는 절대 getString(R.string.name) 으로 가지고 온것을 사용하면 안된다.
        RetrofitService service = RetrofitAdapter.getInstance("https://graph.instagram.com/", getContext());
        Call<FeedResult> call = service.getFeeds(fields, access_token);

        call.enqueue(new retrofit2.Callback<FeedResult>() {
            @Override
            public void onResponse(Call<FeedResult> call, retrofit2.Response<FeedResult> response) {
                if (response.isSuccessful()) {
                    Log.d("GridViewAdapter", "onResponse: Success " + response.body().data.size());
                    imgList.clear();
                    imgList.addAll(response.body().data);
                    pageAdapter.refresh(imgList); //pass update list
                    progressBar.setVisibility(View.GONE);
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
