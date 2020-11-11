package com.woowahan .woowahanfoods;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.woowahan.woowahanfoods.Dataframe.SearchResultJson;
import com.woowahan.woowahanfoods.httpConnection.RetrofitAdapter;
import com.woowahan.woowahanfoods.httpConnection.RetrofitService;

import retrofit2.Call;

public class Address extends Fragment {
    private Button btn_curlocation;
    private ImageView iv_cancel;
    private ImageView iv_search;
    private RecyclerView recyclerView;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_address, container, false);

        btn_curlocation = (Button)view.findViewById(R.id.btn_curlocation);
        btn_curlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).replaceFragmentFull(new CurAddress());
            }
        });

        iv_cancel = (ImageView)view.findViewById(R.id.iv_cancel);
        iv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getFragmentManager().popBackStack();
            }
        });

        //주소 검색
        final String key = "devU01TX0FVVEgyMDIwMTExMTE1MjY1NzExMDQwMjc=";
        final int currentPage = 1;
        final int countPerPage = 10;
        final String keyword = "삼평동";
        final String resultType = "json";


        iv_search = (ImageView)view.findViewById(R.id.iv_search);
        iv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RetrofitAdapter rAdapter = new RetrofitAdapter();
                RetrofitService service = rAdapter.getInstance("http://www.juso.go.kr/", getContext());
                Call<SearchResultJson> call = service.searchAddress(key, currentPage, countPerPage, keyword, resultType);

                call.enqueue(new retrofit2.Callback<SearchResultJson>() {
                    @Override
                    public void onResponse(Call<SearchResultJson> call, retrofit2.Response<SearchResultJson> response) {
                        if (response.isSuccessful()) {
                            SearchResultJson result = response.body();
                            Log.d("Search", "onResponse: Success " + result.results.juso.get(0).roadAddr);
                            Log.d("Search", "onResponse: Success " + result.results.juso.get(1).roadAddr);
                            Log.d("Search", "onResponse: Success " + result.results.juso.get(2).roadAddr);
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
        });

        //리사이클러뷰
        recyclerView = (RecyclerView)view.findViewById(R.id.rv);

        return view;
    }
}