package com.example.viewpagerexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.example.viewpagerexample.adapters.ViewPagerAdaptor;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {

    private FragmentStateAdapter fragmentPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //뷰페이저 세팅
        ViewPager2 viewPager = findViewById(R.id.viewPager);
        fragmentPagerAdapter = new ViewPagerAdaptor(getSupportFragmentManager(),getLifecycle());

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        viewPager.setAdapter(fragmentPagerAdapter);
        //new TabLayoutMediator(tabLayout, viewPager, ((tab, position) -> tab.setText("OBJECT "+(position +1))) ).attach();
        new TabLayoutMediator(tabLayout, viewPager, ((tab, position) -> {
                switch(position) {
                    case 0:
                        tab.setText("Contacts");
                        break;
                    case 1:
                        tab.setText("Photos");
                        break;
                    case 2:
                        tab.setText("Gazzza");
                        break;
                    default:
                        tab.setText("null");
                        break;
                }
            }
        ) ).attach();


    }
}