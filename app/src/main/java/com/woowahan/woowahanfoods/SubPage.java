package com.woowahan.woowahanfoods;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.woowahan.woowahanfoods.Adapter.RestaurantListAdapter;
import com.woowahan.woowahanfoods.Dataframe.FeedResult;
import com.woowahan.woowahanfoods.Dataframe.ImageData;
import com.woowahan.woowahanfoods.Dataframe.Restaurant;
import com.woowahan.woowahanfoods.Dataframe.SearchResultJson;
import com.woowahan.woowahanfoods.httpConnection.RetrofitAdapter;
import com.woowahan.woowahanfoods.httpConnection.RetrofitService;

import java.util.ArrayList;

import retrofit2.Call;

public class SubPage extends Fragment implements RestaurantListAdapter.OnListItemSelectedInterface {

    public String fields = "media_url";
    public String access_token = "IGQVJYVFRlT1VRMDg3QWRQQ3ZAOTGNlbGQ2VGE3YWVvYUNaLWF0MWJaZA1VvQjlwX3lPZAEF3cHVvZAXEzWlhzOHMwSG10SXpmX1lPYXlldGpYZAFhCZAS1aSVR6WTN1ci1oU2FyV2V6OXl3";
    public String sampleFeedID = "17922299383454246";
    int currentPage = 1;
    final int countPerPage = 15;
    public ArrayList<ImageData> imageDataList;

    private static final String ARG_SECTION_NUMBER = "section_number";

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private ArrayList<Restaurant> list;
    private RecyclerView.LayoutManager layoutManager;
    private androidx.appcompat.widget.Toolbar toolbar;
    private ActionBar actionbar;

    public SubPage() {
    }

    public static SubPage newInstance(String sectionNumber) {
        SubPage fragment = new SubPage();
        Bundle args = new Bundle();
        args.putString(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.restaurant_list_tab_sub, container, false);

        view.setClickable(true);

        imageDataList = new ArrayList<>();

        list = new ArrayList<Restaurant>();
        list.add(new Restaurant("한결이의 치킨 가게", "1분거리"));
        list.add(new Restaurant("둘이 먹다 둘이 죽어도 모르는 삼겹살집", "5분거리"));
        list.add(new Restaurant("지은이의 팥빙수 집", "5분거리"));
        list.add(new Restaurant("선철이가 하는 설빙", "10분거리"));

        Log.d("subPage debug", "list size : " + list.size());
        adapter = new RestaurantListAdapter(getContext(), list, this);

        recyclerView = (RecyclerView) view.findViewById(R.id.rv_alarm);

        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            private int recyclerVisiblePosition;
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState != RecyclerView.SCROLL_STATE_IDLE) {
                    return;
                }

                getNewPosition(recyclerView);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

            }

            private void getNewPosition(@NonNull final RecyclerView recyclerView) {
                final LinearLayoutManager layoutManager = ((LinearLayoutManager)recyclerView.getLayoutManager());
                if (layoutManager != null) {
                    int curPosition = recyclerView.getAdapter().getItemCount() - 1;
                    int recyclerVisiblePosition = ((LinearLayoutManager)recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();

                    if((recyclerVisiblePosition >= 10) && (curPosition>=recyclerVisiblePosition-3)){
                        Log.d("d", "error");
                        search();
                    }
                }
            }

        });



        return view;
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
    }


}