package com.example.viewpagerexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import com.example.viewpagerexample.adapters.ViewPagerAdaptor;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity {

    private FragmentStateAdapter fragmentPagerAdapter;
    int nCurrentPermission =0;
    static final int PERMISSIONS_REQUEST = 1004;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        OnCheckPermission();

        //뷰페이저 세팅
        ViewPager2 viewPager = findViewById(R.id.viewPager);
        fragmentPagerAdapter = new ViewPagerAdaptor(getSupportFragmentManager(),getLifecycle());

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        viewPager.setAdapter(fragmentPagerAdapter);
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


        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED
                &&ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                &&ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED){

        }


    }

    @Override
    protected void onStart() {
        super.onStart();
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED
                &&ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                &&ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED){
            //뷰페이저 세팅
            ViewPager2 viewPager = findViewById(R.id.viewPager);
            fragmentPagerAdapter = new ViewPagerAdaptor(getSupportFragmentManager(),getLifecycle());

            TabLayout tabLayout = findViewById(R.id.tab_layout);
            viewPager.setAdapter(fragmentPagerAdapter);
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

    public void OnCheckPermission(){
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED
        ||ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
        ||ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_CONTACTS)
            ||ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_EXTERNAL_STORAGE)
            ||ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.CALL_PHONE)){
                Toast.makeText(this,"Permission is required",Toast.LENGTH_SHORT).show();
                ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_CONTACTS,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.CALL_PHONE}
                        ,PERMISSIONS_REQUEST);
            }
            else{
                ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_CONTACTS,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.CALL_PHONE}
                        ,PERMISSIONS_REQUEST);
            }

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults); //check
        switch (requestCode) {
            case PERMISSIONS_REQUEST:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "앱 실행을 위한 권한이 설정 되었습니다", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Permission is retrieved", Toast.LENGTH_SHORT).show();
                }
                break;

        }
    }
}