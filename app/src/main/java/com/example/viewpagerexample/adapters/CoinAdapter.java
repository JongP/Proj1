package com.example.viewpagerexample.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
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

        TextView coin_name, coin_num;

        coin_name = holder.coin_name;
        coin_num = holder.coin_num;

        coin_name.setText(mListContacts.get(position).getName());
        coin_num.setText(String.valueOf(mListContacts.get(position).getMany()));


    }

    @Override
    public int getItemCount() {
        return mListContacts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView coin_name, coin_num;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            coin_name = itemView.findViewById(R.id.coin_name);
            coin_num = itemView.findViewById(R.id.coin_number);


        }
    }


}
