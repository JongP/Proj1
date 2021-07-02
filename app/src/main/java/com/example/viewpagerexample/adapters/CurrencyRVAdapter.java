package com.example.viewpagerexample.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.viewpagerexample.CurrencyModel;
import com.example.viewpagerexample.R;

import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class CurrencyRVAdapter extends RecyclerView.Adapter<CurrencyRVAdapter.ViewHolder> {

    private List<CurrencyModel> currencyModelArrayList = new ArrayList<CurrencyModel>();
    private Context context;
    private  static DecimalFormat df2 = new DecimalFormat("#.##");
    GetOneCoin interfaceCoin;

    public CurrencyRVAdapter(GetOneCoin interfaceCoin){
        this.interfaceCoin = interfaceCoin;
    }

    public CurrencyRVAdapter(List<CurrencyModel> currencyModelArrayList, Context context,GetOneCoin interfaceCoin) {
        this.currencyModelArrayList = currencyModelArrayList;
        this.context = context;
        this.interfaceCoin = interfaceCoin;
    }

    public void filterList(List<CurrencyModel> filteredList){
        currencyModelArrayList = filteredList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CurrencyRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.currency_item,parent,false);
        return new CurrencyRVAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull  CurrencyRVAdapter.ViewHolder holder, int position) {
        CurrencyModel currencyModel = currencyModelArrayList.get(position);
        holder.currencyNameTV.setText(currencyModel.getName());
        holder.symbolTV.setText(currencyModel.getSymbol());
        holder.rateTV.setText("$ "+df2.format(currencyModel.getPrice()));
    }

    @Override
    public int getItemCount() {
        return currencyModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView currencyNameTV, symbolTV, rateTV;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            currencyNameTV = itemView.findViewById(R.id.idTvCurrencyName);
            symbolTV = itemView.findViewById(R.id.idTVSymbol);
            rateTV = itemView.findViewById(R.id.idTVCurrencyRate);

            currencyNameTV.setOnClickListener(this);
            symbolTV.setOnClickListener(this);
            rateTV.setOnClickListener(this);
        }

        @Override
        public void onClick(View v){
            Log.d("AdapterPlace","check");
            Log.d("AdapterPlace",String.valueOf(getAdapterPosition()));
            Log.d("AdapterPlace",String.valueOf(getAbsoluteAdapterPosition()));
            //Log.d("AdapterPlace",String.valueOf(getBindingAdapter()));
            Log.d("AdapterPlace",String.valueOf(currencyModelArrayList));
            int i = 0;
            List<CurrencyModel> arrayList = null;
            i=getAbsoluteAdapterPosition();
            arrayList=currencyModelArrayList;
            try {
                interfaceCoin.clickCoin(i,arrayList);
            }catch (NullPointerException e){
                System.err.println("Null pointer exception");
            }
        }
    }

    public interface GetOneCoin{
        void clickCoin(int position, List<CurrencyModel> currencyModels);
    }


}
