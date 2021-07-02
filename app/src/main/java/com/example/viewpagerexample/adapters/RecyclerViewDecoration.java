package com.example.viewpagerexample.adapters;

import android.content.Context;
import android.graphics.Rect;
import android.util.TypedValue;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Type;

public class RecyclerViewDecoration extends RecyclerView.ItemDecoration {

    private final int divWidth;
    private final int divHeight;


    public RecyclerViewDecoration(Context context, int divWidth, int divHeight)
    {
        this.divWidth = dpToPx(context, divWidth);
        this.divHeight = dpToPx(context, divHeight);
    }

    private int dpToPx(Context context, int dp){
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics()
        );
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state)
    {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.right = divWidth;
        outRect.bottom=divHeight;
    }
}