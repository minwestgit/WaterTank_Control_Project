package com.example.minseo.myapplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class LevelPagerAdapter extends FragmentPagerAdapter {

    int tabcount;


    public LevelPagerAdapter(FragmentManager fm, int numberOfTabs) {
        super(fm);
        this.tabcount = numberOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                Level1Fragment tab1 = new Level1Fragment();
                return tab1;
            case 1:
                Level2Fragment tab2 = new Level2Fragment();
                return tab2;
            case 2:
                Level3Fragment tab3 = new Level3Fragment();
                return tab3;
            case 3:
                Level4Fragment tab4 = new Level4Fragment();
                return tab4;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabcount;
    }
}
