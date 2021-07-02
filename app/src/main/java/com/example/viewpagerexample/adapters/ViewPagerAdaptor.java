package com.example.viewpagerexample.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.viewpagerexample.fragment.FragContacts;
import com.example.viewpagerexample.fragment.FragDoge;
import com.example.viewpagerexample.fragment.FragGallery;

public class ViewPagerAdaptor extends FragmentStateAdapter {


    public ViewPagerAdaptor(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch (position){
            case 0:
                return FragContacts.newInstance();
            case 1:
                return FragGallery.newInstance();
            case 2:
                return  FragDoge.newInstance();
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }




}
