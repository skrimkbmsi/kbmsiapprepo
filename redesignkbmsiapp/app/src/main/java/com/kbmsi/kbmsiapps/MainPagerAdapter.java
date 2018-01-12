package com.kbmsi.kbmsiapps;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by GIGAMOLE on 8/18/16.
 */
public class MainPagerAdapter extends FragmentStatePagerAdapter {

    private final static int COUNT = 3;

    private final static int HORIZONTAL = 0;
    private final static int TWO_WAY = 2;
    private final static int kehadiranDosen = 1;
    private final static int infoFilkom= 3;
    private final static int tes = 2;

    public MainPagerAdapter(final FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(final int position) {
        switch (position) {
            case TWO_WAY:
                return new HorizontalPagerFragment();
            case kehadiranDosen:
                return new KehadiranDosenFragment();
//            case kehadiranDosen:
//                return new HorizontalPagerFragment();
            default:
                return new RecentPostFragment();
        }
    }

    @Override
    public int getCount() {
        return COUNT;
    }
}
