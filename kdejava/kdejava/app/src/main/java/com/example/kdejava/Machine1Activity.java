package com.example.kdejava;


import android.content.Context;

import android.support.design.widget.TabLayout;

import android.support.v4.view.ViewPager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

<<<<<<< HEAD

import android.view.LayoutInflater;

=======
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
>>>>>>> 706198c7c6cbc69281001edfeca32ba8a54a40b9
import android.view.View;
import android.widget.TextView;


public class Machine1Activity extends AppCompatActivity {
    private Context mContext;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private ContentsPagerAdapter mContentPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_machine1);
        mContext = getApplicationContext();
        mTabLayout = (TabLayout) findViewById(R.id.layout_tab);

        mTabLayout.addTab(mTabLayout.newTab().setCustomView(createTabView("기기관리")));
        mTabLayout.addTab(mTabLayout.newTab().setCustomView(createTabView("모드선택")));
        mTabLayout.addTab(mTabLayout.newTab().setCustomView(createTabView("판매량")));
        mTabLayout.addTab(mTabLayout.newTab().setCustomView(createTabView("기기점검")));
<<<<<<< HEAD
=======
        //mTabLayout.addTab(mTabLayout.newTab().setCustomView(createTabView("뭐할까")));
>>>>>>> 706198c7c6cbc69281001edfeca32ba8a54a40b9


        mViewPager = (ViewPager) findViewById(R.id.pager_content);

        ContentsPagerAdapter mContentsPagerAdapter = new ContentsPagerAdapter(

                getSupportFragmentManager(), mTabLayout.getTabCount());

        mViewPager.setAdapter(mContentsPagerAdapter);
        mViewPager.addOnPageChangeListener(

                new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
<<<<<<< HEAD
=======

>>>>>>> 706198c7c6cbc69281001edfeca32ba8a54a40b9
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private View createTabView(String tabName) {
        View tabView = LayoutInflater.from(mContext).inflate(R.layout.custom, null);
        TextView txt_name = (TextView) tabView.findViewById(R.id.txt_name);
        txt_name.setText(tabName);
        return tabView;

    }

<<<<<<< HEAD
}
=======
}
>>>>>>> 706198c7c6cbc69281001edfeca32ba8a54a40b9
