package com.superior.labs.hindbhashi;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;


public class HomePagesAdapter extends FragmentStatePagerAdapter {

    int numberOfTabs;

    public HomePagesAdapter(FragmentManager fm, int NumberOfTabs)
    {
        super(fm);
        this.numberOfTabs = NumberOfTabs;

    }



    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:

                return new HomeNewsFragment();
            case 1:

                return new CategoryFragment();
            default:
                return null;
        }


    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }
}
