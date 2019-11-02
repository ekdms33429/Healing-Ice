package com.example.kdejava;
<<<<<<< HEAD
=======
// Fragment와 Viewpager를 연결해주는 Adapter
>>>>>>> 706198c7c6cbc69281001edfeca32ba8a54a40b9

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class ContentsPagerAdapter extends FragmentStatePagerAdapter {
<<<<<<< HEAD

=======
    // 생성자를 통해서 Fragment의 관리를 도와주는 FragmentManager와
    // 페이지의 개수를 탭의 개수와 맞춰주기 위해 pageCount를 받아오도록 하였습니다.
>>>>>>> 706198c7c6cbc69281001edfeca32ba8a54a40b9
    private int mPageCount;
    public ContentsPagerAdapter(FragmentManager fm, int pageCount) {
        super(fm);
        this.mPageCount = pageCount;

    }

    @Override
<<<<<<< HEAD
    public Fragment getItem(int position) {

=======
    public Fragment getItem(int position) {// position에 해당하는 Fragment를 반환합니다.
>>>>>>> 706198c7c6cbc69281001edfeca32ba8a54a40b9
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
<<<<<<< HEAD

=======
            /*
            case 4:
                TabFragment5 fragment5 = new TabFragment5();
                return fragment5;
                */
>>>>>>> 706198c7c6cbc69281001edfeca32ba8a54a40b9
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
<<<<<<< HEAD
        return mPageCount;
=======
        return mPageCount; //page의 개수를 반환합니다.
>>>>>>> 706198c7c6cbc69281001edfeca32ba8a54a40b9
    }

}