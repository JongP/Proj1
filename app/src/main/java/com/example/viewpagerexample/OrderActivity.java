package com.example.viewpagerexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.viewpagerexample.Room.AppDataBase_wallet;
import com.example.viewpagerexample.Room.User_wallet;

public class OrderActivity extends AppCompatActivity {

    private TextView tv_symbol,tv_name, tv_price, tv_quantity,tv_total,tv_coinown,tv_coinbalance;
    private Button btn_minus,btn_plus, btn_sell, btn_buy;
    private int quantity,count;
    Double totalPrice, balance;
    private AppDataBase_wallet db;
    private SoundPool soundPool = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);


        tv_symbol = findViewById(R.id.tv_coinsymbol);
        tv_name = findViewById(R.id.tv_coinname);
        tv_price = findViewById(R.id.tv_coinrate);
        tv_quantity = findViewById(R.id.tv_qunatity);
        tv_total = findViewById(R.id.tv_coin_totalprice);
        btn_plus = findViewById(R.id.coin_btn_plus);
        btn_minus = findViewById(R.id.coin_btn_minus);
        btn_buy = findViewById(R.id.btn_buy);
        btn_sell = findViewById(R.id.btn_sell);
        tv_coinown = findViewById(R.id.tv_coinown);
        tv_coinbalance =findViewById(R.id.tv_coinbalance);
        quantity = 0;
        db = AppDataBase_wallet.getInstance(this);


        Intent intent = getIntent();
        String symbol = intent.getStringExtra("symbol");
        String name = intent.getStringExtra("name");
        Double price = intent.getDoubleExtra("price",0);



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
               soundPool = new SoundPool.Builder().build();
        } else {
            soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        }
        int soundId1 = soundPool.load(getApplicationContext(), R.raw.uii, 1);
        int soundId2 =soundPool.load(getApplicationContext(), R.raw.mars, 1);



        //have to check
        count = db.userDao().getQuantity(symbol);

        try {
            db.userDao().insert(new User_wallet(symbol,0,0.0));
        }catch (Exception e){
        }

        balance = db.userDao().getValue("balance");

        tv_coinown.setText("quantity: "+String.valueOf(count));
        tv_coinbalance.setText("$ "+String.valueOf(balance));

        tv_symbol.setText(symbol);
        tv_name.setText(name);
        tv_price.setText("$ "+String.valueOf(price));




        btn_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(quantity <10){
                    quantity++;
                    tv_quantity.setText(String.valueOf(quantity));
                    totalPrice = Math.round(quantity*price*100)/100.0;
                    tv_total.setText("$ "+String.valueOf(totalPrice));
                }
                else{
                    Toast.makeText(getApplicationContext(),"풀매수/풀매도는 정신 건강에 해롭다.",Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(quantity >0){
                    quantity--;
                    tv_quantity.setText(String.valueOf(quantity));
                    totalPrice = Math.round(quantity*price*100)/100.0;
                    tv_total.setText("$ "+String.valueOf(totalPrice));
                }else{
                    Toast.makeText(getApplicationContext(),"no under zero",Toast.LENGTH_SHORT).show();
                }

            }
        });

        btn_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(quantity==0) return;

                if(totalPrice>balance){
                    Toast.makeText(getApplicationContext(),"Not enough cash, go back to work.",Toast.LENGTH_SHORT).show();
                }else{
                    double my_value = db.userDao().getValue(symbol);
                    my_value=(my_value*count+totalPrice)/(count+quantity);
                    my_value=Math.round(my_value*100)/100.0;

                    balance = Math.round((balance-totalPrice)*100)/100.0;
                    count+=quantity;

                    if(symbol.equals("DOGE")){
                        int play = soundPool.play(soundId2, 1.0f, 1.0f, 1, 0,1.0f);
                    }


                    db.userDao().update(count,my_value,symbol);
                    db.userDao().update(0,balance,"balance");

                    Toast.makeText(getApplicationContext(),"We're heading to Mars.",Toast.LENGTH_SHORT).show();
                    tv_coinbalance.setText("$ "+String.valueOf(balance));
                    tv_coinown.setText("quantity: "+String.valueOf(count));
                }
            }
        });

        btn_sell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(quantity==0) return;
                if(quantity>count){
                    Toast.makeText(getApplicationContext(),"Not enough coins, buy some.",Toast.LENGTH_SHORT).show();
                }
                else{
                    double my_value = db.userDao().getValue(symbol);

                    if(symbol.equals("DOGE")){
                        int play = soundPool.play(soundId1, 1.0f, 1.0f, 1, 0,1.0f);
                    }

                    balance = Math.round((balance+totalPrice)*100)/100.0;
                    count = count-quantity;

                    db.userDao().update(count,my_value,symbol);
                    db.userDao().update(0,balance,"balance");

                    Toast.makeText(getApplicationContext(),"Bye",Toast.LENGTH_SHORT).show();
                    tv_coinbalance.setText("$ "+String.valueOf(balance));
                    tv_coinown.setText("quantity: "+String.valueOf(count));
                }
            }
        });



    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(soundPool !=null){
            soundPool.release();
            soundPool=null;
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_order, container, false);
    }
}