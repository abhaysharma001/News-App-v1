package com.superior.labs.hindbhashi;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

public class PagesAdapter extends FragmentStatePagerAdapter {

    ArrayList<Model> list;
    String image,que="";

    public PagesAdapter(FragmentManager fm, ArrayList<Model> list){
        super(fm);
        this.list =list;
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        NewsFragment fragment = new NewsFragment();
        que = list.get(position).getImage();

        Bundle bundle = new Bundle();
        bundle.putString("content",list.get(position).getSubtitle());
        bundle.putString("image",list.get(position).getImage());
        bundle.putString("heading",list.get(position).getTitle());
        bundle.putString("date",list.get(position).getDate());
        bundle.putString("position",String.valueOf(position));
        /*
        bundle.putString("content",list.get(position).getContent());
        bundle.putString("image",list.get(position).getImage());
        bundle.putString("heading",list.get(position).getHeading());*/
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return list.size();
    }

}
