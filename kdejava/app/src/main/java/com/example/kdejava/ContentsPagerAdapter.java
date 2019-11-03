package com.example.kdejava;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class ContentsPagerAdapter extends FragmentStatePagerAdapter {

    private int mPageCount;
    public ContentsPagerAdapter(FragmentManager fm, int pageCount) {
        super(fm);
        this.mPageCount = pageCount;

    }

    @Override
    public Fragment getItem(int position) {

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

            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return mPageCount;
    }

}