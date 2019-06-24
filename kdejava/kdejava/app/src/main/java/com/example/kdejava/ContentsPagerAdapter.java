package com.example.kdejava;
// Fragment와 Viewpager를 연결해주는 Adapter

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class ContentsPagerAdapter extends FragmentStatePagerAdapter {
    // 생성자를 통해서 Fragment의 관리를 도와주는 FragmentManager와
    // 페이지의 개수를 탭의 개수와 맞춰주기 위해 pageCount를 받아오도록 하였습니다.
    private int mPageCount;
    public ContentsPagerAdapter(FragmentManager fm, int pageCount) {
        super(fm);
        this.mPageCount = pageCount;

    }

    @Override
    public Fragment getItem(int position) {// position에 해당하는 Fragment를 반환합니다.
        switch (position) {
            case 0:
                ManagementFragment fragment1 = new ManagementFragment();
                return fragment1;

            case 1:
                SelectmodeFragment fragment2 = new SelectmodeFragment();
                return fragment2;

            case 2:
                SellingFragment fragment3 = new SellingFragment();
                return fragment3;

            case 3:
                CheckFragment fragment4 = new CheckFragment();
                return fragment4;
            /*
            case 4:
                TabFragment5 fragment5 = new TabFragment5();
                return fragment5;
                */
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return mPageCount; //page의 개수를 반환합니다.
    }

}