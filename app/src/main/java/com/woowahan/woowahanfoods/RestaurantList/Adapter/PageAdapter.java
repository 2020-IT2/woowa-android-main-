package com.woowahan.woowahanfoods.RestaurantList.Adapter;

import android.util.Log;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.woowahan.woowahanfoods.RestaurantList.Fragment.SubPage;

import java.util.ArrayList;

public class PageAdapter extends FragmentStateAdapter {

    String[] tabNameList;
    public ArrayList<Fragment> fragments;
    public Fragment fragment;

    public PageAdapter(FragmentActivity fm) {
        super(fm);
        // Tab 기본 설정
        tabNameList = new String[]{"한식", "양식", "패스트푸드", "일식/수산물", "제과제빵떡케익", "닭/오리요리", "패스트푸드", "중식", "분식", "한식", "분식", "별식/퓨전요리"};

        fragments = new ArrayList<>();
        for(String tabName : tabNameList){
            fragments.add(SubPage.newInstance(tabName));
        }
//        tabNameList = new String [] {"알림", "쪽지"};
    }

    @Override
    public Fragment createFragment(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
//        if (position == 0)
//        fragments.add();
        Log.d("PageAdapter", "fragment list position: " + position);
        return fragments.get(position);
//        else
//            return SubPage2.newInstance();
    }


    @Override
    public int getItemCount() {
        // Show 3 total pages.
        return tabNameList.length;
    }

}