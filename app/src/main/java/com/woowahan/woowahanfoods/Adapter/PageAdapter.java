package com.woowahan.woowahanfoods.Adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.woowahan.woowahanfoods.SubPage;

public class PageAdapter extends FragmentStatePagerAdapter {

    String [] tabNameList;

    public PageAdapter(FragmentManager fm) {
        super(fm);
        // Tab 기본 설정
        tabNameList = new String [] {"한식", "양식", "패스트푸드", "일식", "디저트", "치킨", "피자", "중식", "도시락", "찜/탕", "김밥", "아시안"};
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        return SubPage.newInstance(tabNameList[position]);
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 12;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabNameList[position];
    }

}