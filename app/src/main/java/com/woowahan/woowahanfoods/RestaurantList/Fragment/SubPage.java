package com.woowahan.woowahanfoods.RestaurantList.Fragment;

import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.woowahan.woowahanfoods.FeedViewer.Fragment.FeedViewer;
import com.woowahan.woowahanfoods.Home.Dataframe.RandomRecommendResponse;
import com.woowahan.woowahanfoods.MainActivity;
import com.woowahan.woowahanfoods.R;
import com.woowahan.woowahanfoods.RestaurantList.Adapter.RestaurantListAdapter;
import com.woowahan.woowahanfoods.DataModel.Restaurant;
import com.woowahan.woowahanfoods.RestaurantList.Dataframe.RestaurantListResponse;
import com.woowahan.woowahanfoods.Utils.TextUtils;
import com.woowahan.woowahanfoods.httpConnection.RetrofitAdapter;
import com.woowahan.woowahanfoods.httpConnection.RetrofitService;

import java.lang.reflect.Type;
import java.util.ArrayList;

import retrofit2.Call;

public class SubPage extends Fragment implements RestaurantListAdapter.OnListItemSelectedInterface {

    public String fields = "media_url";
    public String access_token = "IGQVJYVFRlT1VRMDg3QWRQQ3ZAOTGNlbGQ2VGE3YWVvYUNaLWF0MWJaZA1VvQjlwX3lPZAEF3cHVvZAXEzWlhzOHMwSG10SXpmX1lPYXlldGpYZAFhCZAS1aSVR6WTN1ci1oU2FyV2V6OXl3";
    public String sampleFeedID = "17922299383454246";
    int currentPage = 1;
    final int countPerPage = 15;
    public ArrayList<Restaurant> imageDataList;
    public ArrayList<Restaurant> franchiseList;
    public ArrayList<Restaurant> nonfranchiseList;
    public Location loc;

    private static final String ARG_SECTION_NUMBER = "section_number";

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private ArrayList<Restaurant> list;
    private RecyclerView.LayoutManager layoutManager;
    private androidx.appcompat.widget.Toolbar toolbar;
    private ActionBar actionbar;
    public int toggle = 0;


    public SubPage() {
    }

    public static SubPage newInstance(String sectionNumber) {
        SubPage fragment = new SubPage();
        Bundle args = new Bundle();
        Gson gson = new Gson();
        args.putString(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.restaurant_list_tab_sub, container, false);
        view.setClickable(true);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imageDataList = new ArrayList<>();
        franchiseList = new ArrayList<>();
        nonfranchiseList = new ArrayList<>();

        list = new ArrayList<Restaurant>();
        list.add(new Restaurant("고요남", "치킨가게",  "수원시 원천구 원천대로", 123, 456, 33.1, 127.1, "https://scontent-ssn1-1.cdninstagram.com/v/t51.29350-15/123833652_1408714295988521_1955747731663463592_n.jpg?_nc_cat=100&ccb=2&_nc_sid=8ae9d6&_nc_ohc=CRy-YtpUAOsAX8P_Nsx&_nc_ht=scontent-ssn1-1.cdninstagram.com&oh=6c44e4df042e6b21c24baa81f5b6f523&oe=5FD2ADE6"));
        list.add(new Restaurant("엉터리생고기", "치킨가게",  "수원시 원천구 원천대로", 123, 456, 33.1, 127.1, "https://scontent-ssn1-1.cdninstagram.com/v/t51.29350-15/123833652_1408714295988521_1955747731663463592_n.jpg?_nc_cat=100&ccb=2&_nc_sid=8ae9d6&_nc_ohc=CRy-YtpUAOsAX8P_Nsx&_nc_ht=scontent-ssn1-1.cdninstagram.com&oh=6c44e4df042e6b21c24baa81f5b6f523&oe=5FD2ADE6"));
        list.add(new Restaurant("제임스치즈등갈비", "치킨가게",  "수원시 원천구 원천대로", 123, 456, 33.1, 127.1, "https://scontent-ssn1-1.cdninstagram.com/v/t51.29350-15/123833652_1408714295988521_1955747731663463592_n.jpg?_nc_cat=100&ccb=2&_nc_sid=8ae9d6&_nc_ohc=CRy-YtpUAOsAX8P_Nsx&_nc_ht=scontent-ssn1-1.cdninstagram.com&oh=6c44e4df042e6b21c24baa81f5b6f523&oe=5FD2ADE6"));
        loc = new Location("my Location");
        loc.setLongitude(33.999999);
        loc.setLongitude(127.999999);

        Log.d("subPage debug", "list size : " + list.size());
        adapter = new RestaurantListAdapter(loc, getContext(), imageDataList, this);

        recyclerView = (RecyclerView) view.findViewById(R.id.rv_alarm);

        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(adapter);

        getRestaurantList();

//        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            private int recyclerVisiblePosition;
//            @Override
//            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//
//                if (newState != RecyclerView.SCROLL_STATE_IDLE) {
//                    return;
//                }
//
//                getNewPosition(recyclerView);
//            }
//
//            @Override
//            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//
//            }
//
//            private void getNewPosition(@NonNull final RecyclerView recyclerView) {
//                final LinearLayoutManager layoutManager = ((LinearLayoutManager)recyclerView.getLayoutManager());
//                if (layoutManager != null) {
//                    int curPosition = recyclerView.getAdapter().getItemCount() - 1;
//                    int recyclerVisiblePosition = ((LinearLayoutManager)recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
//
//                    if((recyclerVisiblePosition >= 10) && (curPosition>=recyclerVisiblePosition-3)){
//                        Log.d("d", "error");
//                        search();
//                    }
//                }
//            }
//
//        });

    }

    public void getRestaurantList() {
        RetrofitService service = RetrofitAdapter.getInstance(getActivity());
        Call<RestaurantListResponse> call = service.getRestaurantList("한식");

        call.enqueue(new retrofit2.Callback<RestaurantListResponse>() {
            @Override
            public void onResponse(Call<RestaurantListResponse> call, retrofit2.Response<RestaurantListResponse> response) {
                if (response.isSuccessful()) {
                    response.body().checkError(getContext());
                    imageDataList.clear();
                    franchiseList.clear();
                    nonfranchiseList.clear();
                    franchiseList.addAll(response.body().body.franchise);
                    nonfranchiseList.addAll(response.body().body.non_franchise);
                    imageDataList.addAll(nonfranchiseList);
                    adapter.notifyDataSetChanged();
                    toggle = 0;
                    Log.d("GridViewAdapter", "onResponse: Succeess " + response.body());
                } else {
                    Log.d("GridViewAdapter", "onResponse: Fail " + response.body());
                }
            }

            @Override
            public void onFailure(Call<RestaurantListResponse> call, Throwable t) {
                Log.d("GridViewAdapter", "onResponse: Fail ");
                Toast.makeText(getContext(), "Please reloading", Toast.LENGTH_SHORT).show();
            }
        });
    }



    public void search(){
//        RetrofitAdapter rAdapter = new RetrofitAdapter();
////        RetrofitService service = rAdapter.getInstance("http://www.juso.go.kr/", getContext());
////        final String query = this.query;
////        Call<SearchResultJson> call = service.searchAddress(key, currentPage, countPerPage, query, resultType);
//        // 우선 동그라미 이미지가 잘 띄워 지는지 전체적인 View가 어떻게 나오는지 확인한다.
//        // 그리고 자신의 위치와 음식점과의 위치를 계산한다.
//        RetrofitService service = RetrofitAdapter.getInstance("https://graph.instagram.com/", getContext());
//        Call<FeedResult> call = service.getFeedDetails(sampleFeedID, fields, access_token);
//
//        call.enqueue(new retrofit2.Callback<SearchResultJson>() {
//            @Override
//            public void onResponse(Call<SearchResultJson> call, retrofit2.Response<SearchResultJson> response) {
//                if (response.isSuccessful()) {
//                    SearchResultJson result = response.body();
//                    if(result.results.juso == null){
//                        return ;
//                    }
//                    currentPage++;
//                    imageDataList.addAll(result.results.juso);
//                    adapter.notifyDataSetChanged();
//                } else {
//                    Log.d("Search", "onResponse: Success but parsing fails " + response.body());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<SearchResultJson> call, Throwable t) {
//                Log.d("Search", "onFailure: " + t.getMessage());
//            }
//        });
    }


    @Override
    public void onResume() {
        Log.d("subPage debug", "list size : " + list.size());
        adapter.notifyDataSetChanged();
        Log.d("subPage debug", "list size changed : " + list.size());
        super.onResume();
    }

    @Override
    public void onItemSelected(View v, int position) {
        ((MainActivity)getActivity()).replaceFragmentFull(FeedViewer.newInstance(1, imageDataList.get(position).restaurantName));
    }

    public void toggle(){
        Log.d("SubPageDebug", "hello this is toggle button");
        if (toggle==0){
            Log.d("SubPageDebug", "hello this is toggle to 0");
            imageDataList.clear();
            imageDataList.addAll(franchiseList);
            adapter.notifyDataSetChanged();
            toggle = 1;
        } else {
            Log.d("SubPageDebug", "hello this is toggle to 1");
            imageDataList.clear();
            imageDataList.addAll(nonfranchiseList);
            adapter.notifyDataSetChanged();
            toggle = 0;
        }
    }

}