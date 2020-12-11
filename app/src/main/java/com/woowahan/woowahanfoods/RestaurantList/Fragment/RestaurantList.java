package com.woowahan.woowahanfoods.RestaurantList.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.woowahan.woowahanfoods.RestaurantList.Adapter.PageAdapter;
import com.woowahan.woowahanfoods.R;

public class RestaurantList extends Fragment {
    TabLayout tabs;
    PageAdapter pageAdapter;
    ViewPager2 viewPage;
    public String [] tabNameList = new String[]{"한식", "양식", "패스트푸드", "일식", "디저트", "치킨", "피자", "중식", "도시락", "찜/탕", "김밥", "아시안"};
    public int menu;
    public TextView tvFranchiseToggle;
    private androidx.appcompat.widget.Toolbar toolbar;
    private ActionBar actionbar;
    private TabLayout tabLayout;
    public int toggle = 1;

    public static RestaurantList newInstance(int menu){
        Bundle adr = new Bundle();
        adr.putInt("menu", menu);
        RestaurantList restaurantList = new RestaurantList();
        restaurantList.setArguments(adr);
        return restaurantList;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_restaurant_list, container, false);
        view.setClickable(true);
        setHasOptionsMenu(true);
        Bundle bundle = getArguments();
        if(bundle!=null){
            this.menu = bundle.getInt("menu");
        } else {
            this.menu = 0;
        }

        // connect to textView
        tvFranchiseToggle = view.findViewById(R.id.tv_toggle);
        tvFranchiseToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = pageAdapter.fragments.get(viewPage.getCurrentItem());
                ((SubPage)fragment).toggle();
                int toggle = ((SubPage)fragment).toggle;
                if (toggle == 0){
                    tvFranchiseToggle.setText("비프렌차이즈");
                } else {
                    tvFranchiseToggle.setText("프렌차이즈");
                }
//                pageAdapter.notifyDataSetChanged();
            }
        });

        pageAdapter = new PageAdapter(getActivity());

        // Set up the ViewPager with the sections adapter.
        viewPage = (ViewPager2) view.findViewById(R.id.container);
        viewPage.setAdapter(pageAdapter);

        tabs = (TabLayout) view.findViewById(R.id.tabs);
        new TabLayoutMediator(tabs, viewPage,
                new TabLayoutMediator.TabConfigurationStrategy() {
                    @Override
                    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                        Log.d("RestaurantList", "OnConfigureTab");
                        tab.setText(tabNameList[position]);
                    }
                }
        ).attach();
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                Log.d("RestaurantList", "onTabSelected: " + position);
                Fragment fragment = pageAdapter.fragments.get(position);
                int toggle = ((SubPage)fragment).toggle;
                if (toggle == 0){
                    tvFranchiseToggle.setText("비프렌차이즈");
                } else {
                    tvFranchiseToggle.setText("프렌차이즈");
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        tabs.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white_gray));
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
