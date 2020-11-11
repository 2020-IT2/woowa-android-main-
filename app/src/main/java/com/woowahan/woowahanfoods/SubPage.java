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
import com.woowahan.woowahanfoods.Dataframe.Restaurant;

import java.util.ArrayList;

public class SubPage extends Fragment implements RestaurantListAdapter.OnListItemSelectedInterface {
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

        list = new ArrayList<Restaurant>();
        Log.d("subPage debug", "list size : " + list.size());
        adapter = new RestaurantListAdapter(getContext(), list, this);

        recyclerView = (RecyclerView) view.findViewById(R.id.rv_alarm);

        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });



        return view;
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