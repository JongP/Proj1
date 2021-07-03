package com.example.viewpagerexample.adapters;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.viewpagerexample.CoinItem;
import com.example.viewpagerexample.R;

import java.util.List;

public class CoinAdapter extends RecyclerView.Adapter<CoinAdapter.ViewHolder> {

    private Context mcontext;
    private LayoutInflater inflater;
    private List<CoinItem> mListContacts;

    public CoinAdapter(Context context, List<CoinItem> mListContacts) {
        this.mcontext = context;
        this.mListContacts = mListContacts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("ViewHolder","here");
        inflater = LayoutInflater.from(mcontext);
        View view = inflater.inflate(R.layout.coin_layout,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        TextView coin_name, coin_num,coin_price, coin_ratio;

        coin_name = holder.coin_name;
        coin_num = holder.coin_num;
        coin_price =holder.coin_price;
        coin_ratio = holder.coin_ratio;

        CoinItem coinItem =mListContacts.get(position);
        double ratio = coinItem.getRatio();


        coin_name.setText(coinItem.getName());
        coin_num.setText("Quan: "+String.valueOf(coinItem.getMany()));
        coin_price.setText("$ "+ String.valueOf(coinItem.getPrice()));

        if(ratio>=0.0){
            coin_ratio.setTextColor(Color.parseColor("#FF0000"));
        }else {
            coin_ratio.setTextColor(Color.parseColor("#0000FF"));
        }
        coin_ratio.setText(String.valueOf(ratio)+" %");


    }

    @Override
    public int getItemCount() {
        return mListContacts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView coin_name, coin_num,coin_price, coin_ratio;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            coin_name = itemView.findViewById(R.id.coin_name);
            coin_num = itemView.findViewById(R.id.coin_number);
            coin_price = itemView.findViewById(R.id.coin_price);
            coin_ratio =itemView.findViewById(R.id.coin_ratio);


        }
    }


}
