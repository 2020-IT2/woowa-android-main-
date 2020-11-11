package com.woowahan.woowahanfoods;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.woowahan.woowahanfoods.Dataframe.Restaurant;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class FeedViewer extends Fragment {
    public String TAG = "FeedViewer Fragment Debug";
    private androidx.appcompat.widget.Toolbar toolbar;
    private ActionBar actionbar;
    public Restaurant restaurant;

    public static FeedViewer newInstance(Restaurant restaurant){
        Bundle adr = new Bundle();
        Gson gson = new Gson();
        String json = gson.toJson(restaurant);
        adr.putString("restaurant", json);
        FeedViewer feedViewer = new FeedViewer();
        feedViewer.setArguments(adr);
        return feedViewer;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feed_viewer, container, false);
        setHasOptionsMenu(true);
        Bundle bundle = getArguments();
        if(bundle!=null){
            Gson gson = new Gson();
            this.restaurant = gson.fromJson(bundle.getString("query"), Restaurant.class);
        }
        toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        actionbar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionbar.setDisplayShowCustomEnabled(true);
        actionbar.setDisplayShowTitleEnabled(false);//기본 제목을 없애줍니다.
        actionbar.setDisplayHomeAsUpEnabled(true);

        return view;
    }
}
