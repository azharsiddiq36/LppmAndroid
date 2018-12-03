package com.example.azhar.lppm.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.azhar.lppm.Fragment.BelumSetujuFragment;
import com.example.azhar.lppm.Fragment.SetujuFragment;
import com.example.azhar.lppm.Fragment.TerbaruFragment;
import com.example.azhar.lppm.Fragment.UrutkanFragment;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private int number_tabs;

    public ViewPagerAdapter(FragmentManager fm, int number_tabs) {
        super(fm);
        this.number_tabs = number_tabs;
    }

    //Mengembalikan Fragment yang terkait dengan posisi tertentu
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new TerbaruFragment();
            case 1:
                return new UrutkanFragment();
            case 2:
                return new SetujuFragment();
            case 3:
                return new BelumSetujuFragment();
            default:
                return null;
        }
    }

    //Mengembalikan jumlah tampilan yang tersedia.
    @Override
    public int getCount() {
        return number_tabs;
    }
}