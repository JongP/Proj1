package com.example.viewpagerexample;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.viewpagerexample.Room.AppDataBase_wallet;
import com.example.viewpagerexample.Room.User_wallet;
import com.example.viewpagerexample.adapters.CoinAdapter;

import java.util.ArrayList;
import java.util.List;


public class WalletActivity extends AppCompatActivity {


    private AppDataBase_wallet db;
    private TextView bal,tv_walletBalnce;
    private RecyclerView recyclerView;
    double curBal=0.0;

    ArrayList<CoinItem> coinList = new ArrayList<>();
    CoinAdapter adapter;  // 리사이클러뷰에 적용시킬 어댑터



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);
        bal = findViewById(R.id.user_balance);

        db = AppDataBase_wallet.getInstance(this);
        recyclerView = findViewById(R.id.recyclerView_wallet);
        tv_walletBalnce =findViewById(R.id.tv_walletBalnce);

        Intent intent =getIntent();
        List<User_wallet> user_walletList = db.userDao().getAll();


        for(User_wallet user_wallet : user_walletList){
            if(user_wallet.getSym().equals("balance")) {
                curBal = user_wallet.getVal();
                continue;
            }
            String uSym = user_wallet.getSym();
            int uQuan = user_wallet.getQuan();
            double uValue = user_wallet.getVal();
            double current_price = intent.getDoubleExtra(uSym,0.0);
            double ratio = (uValue-current_price)/uValue;
            ratio = Math.round(ratio*1000)/1000.0;

            //Log.d("Saturday Morning", "Wallet: "+uSym+ " "+String.valueOf(current_price));

            CoinItem item = new CoinItem(uSym,uQuan, current_price,ratio);
            coinList.add(item);
        }


        tv_walletBalnce.setText("$ "+String.valueOf(curBal));

        adapter = new CoinAdapter(WalletActivity.this,coinList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(WalletActivity.this, RecyclerView.HORIZONTAL, false));

    }
}