package com.example.viewpagerexample;

import android.content.Intent;
import android.graphics.Color;
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
    private TextView bal,tv_walletBalnce,tv_finalratio;
    private RecyclerView recyclerView;
    double curBal=0.0;
    double final_value=0.0;
    int total_quan =0;

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
        tv_finalratio = findViewById(R.id.tv_final_ratio);

        Intent intent =getIntent();
        List<User_wallet> user_walletList = db.userDao().getAll();



        adapter = new CoinAdapter(WalletActivity.this,coinList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(WalletActivity.this, RecyclerView.VERTICAL, false));

        final_value=0.0;
        total_quan =0;

        for(User_wallet user_wallet : user_walletList){
            if(user_wallet.getSym().equals("balance")) {
                curBal = user_wallet.getVal();
                continue;
            }
            String uSym = user_wallet.getSym();
            int uQuan = user_wallet.getQuan();
            if(uQuan==0) continue;

            double uValue = user_wallet.getVal();
            double current_price = intent.getDoubleExtra(uSym,0.0);
            double ratio = ((current_price-uValue)/uValue)*100.0;

            ratio = Math.round(ratio*100)/100.0;
            final_value += ratio*uQuan;
            total_quan+=uQuan;

            //Log.d("Saturday Morning", "Wallet: "+uSym+ " "+String.valueOf(current_price));

            CoinItem item = new CoinItem(uSym,uQuan, current_price,ratio);
            coinList.add(item);
            adapter.notifyDataSetChanged();
        }


        tv_walletBalnce.setText("$ "+String.valueOf(curBal));
        Log.d("Saturday Morning", "Wallet: "+String.valueOf(final_value) +" "+String.valueOf(curBal) );

        double bottomline_ratio = final_value/total_quan;
        bottomline_ratio = Math.round(bottomline_ratio*1000)/1000.0;
        if(bottomline_ratio>=0.0){
            tv_finalratio.setTextColor(Color.parseColor("#FF0000"));
            tv_finalratio.setText("+"+String.valueOf(bottomline_ratio)+" %");
        }else{
            tv_finalratio.setTextColor(Color.parseColor("#0000FF"));
            tv_finalratio.setText(String.valueOf(bottomline_ratio)+" %");
        }



    }
}