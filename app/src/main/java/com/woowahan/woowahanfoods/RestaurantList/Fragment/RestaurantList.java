package com.woowahan.woowahanfoods.RestaurantList.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.woowahan.woowahanfoods.RestaurantList.Adapter.PageAdapter;
import com.woowahan.woowahanfoods.R;

public class RestaurantList extends Fragment {
    TabLayout tabs;
    PageAdapter pageAdapter;
    ViewPager viewPage;
    public int menu;
    private androidx.appcompat.widget.Toolbar toolbar;
    private ActionBar actionbar;

    public static RestaurantList newInstance(int menu){
        Bundle adr = new Bundle();
        adr.putInt("menu", menu);
        RestaurantList restaurantList = new RestaurantList();
        restaurantList.setArguments(adr);
        return restaurantList;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_restaurant_list, container, false);
        setHasOptionsMenu(true);
        Bundle bundle = getArguments();
        if(bundle!=null){
            this.menu = bundle.getInt("menu");
        } else {
            this.menu = 0;
        }

        pageAdapter = new PageAdapter(getChildFragmentManager());

        // Set up the ViewPager with the sections adapter.
        viewPage = (ViewPager) view.findViewById(R.id.container);
        viewPage.setAdapter(pageAdapter);

        tabs = (TabLayout) view.findViewById(R.id.tabs);
        tabs.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white_gray));
        tabs.setupWithViewPager(viewPage);
        tabs.getTabAt( menu).select();

        toolbar = view.findViewById(R.id.toolbar2);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        actionbar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionbar.setDisplayShowCustomEnabled(true);
        actionbar.setDisplayShowTitleEnabled(false);//기본 제목을 없애줍니다.
        actionbar.setDisplayHomeAsUpEnabled(true);

        return view;
    }

    public void refresh() {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.detach(this).attach(this).commit();
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
