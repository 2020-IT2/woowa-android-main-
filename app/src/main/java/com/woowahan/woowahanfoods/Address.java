package com.woowahan .woowahanfoods;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.woowahan.woowahanfoods.Dataframe.SearchResultJson;
import com.woowahan.woowahanfoods.httpConnection.RetrofitAdapter;
import com.woowahan.woowahanfoods.httpConnection.RetrofitService;

import java.util.ArrayList;

import retrofit2.Call;

public class Address extends Fragment {
    private Button btn_curlocation;
    private ImageView iv_cancel;
    private ImageView iv_search;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_address, container, false);

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

        final String key = "devU01TX0FVVEgyMDIwMTExMTE1MjY1NzExMDQwMjc=";
        final int currentPage = 1;
        final int countPerPage = 10;
        final String keyword = "판교";
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
                            Log.d("Search", "onResponse: Success " + result.results.juso.get(0).detBdNmList);
                            Log.d("Search", "onResponse: Success " + result.results.juso.get(0).engAddr);
                            Log.d("Search", "onResponse: Success " + result.results.juso.get(0).rn);
                            Log.d("Search", "onResponse: Success " + result.results.juso.get(0).emdNm);
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


        return view;
    }
}