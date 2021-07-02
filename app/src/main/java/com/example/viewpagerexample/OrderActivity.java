package com.example.viewpagerexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class OrderActivity extends AppCompatActivity {

    private TextView tv_symbol,tv_name, tv_price, tv_quantity,tv_total;
    private Button btn_minus,btn_plus, btn_sell, btn_buy;
    private int quantity;
    Double totalPrice;

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
        quantity = 0;


        Intent intent = getIntent();
        String symbol = intent.getStringExtra("symbol");
        String name = intent.getStringExtra("name");
        Double price = intent.getDoubleExtra("price",0);

        tv_symbol.setText(symbol);
        tv_name.setText(name);
        tv_price.setText("$ "+String.valueOf(price));


        btn_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(quantity <10){
                    quantity++;
                    tv_quantity.setText(String.valueOf(quantity));
                    totalPrice = quantity*price;
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
                    totalPrice = quantity*price;
                    tv_total.setText("$ "+String.valueOf(totalPrice));
                }else{
                    Toast.makeText(getApplicationContext(),"no under zero",Toast.LENGTH_SHORT).show();
                }

            }
        });





    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_order, container, false);
    }
}