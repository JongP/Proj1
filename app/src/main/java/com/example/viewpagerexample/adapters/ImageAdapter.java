package com.example.viewpagerexample.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.viewpagerexample.Image_show;
import com.example.viewpagerexample.R;
import com.example.viewpagerexample.Room.AppDataBase_gallery;
import com.example.viewpagerexample.Room.User_gallery;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {
    private ArrayList<Uri> mData = null ;
    private Context mContext = null ;
    AppDataBase_gallery db;



    // 생성자에서 데이터 리스트 객체, Context를 전달받음.
    public ImageAdapter(ArrayList<Uri> list, Context context) {
        mData = list ;
        mContext = context;
    }

    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView image;


        ViewHolder(View itemView) {
            super(itemView) ;

            // 뷰 객체에 대한 참조.
            image = itemView.findViewById(R.id.image);

        }

    }



    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    // LayoutInflater - XML에 정의된 Resource(자원) 들을 View의 형태로 반환.
    @Override
    public ImageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;    // context에서 LayoutInflater 객체를 얻는다.
        View view = inflater.inflate(R.layout.image_item, parent, false) ;	// 리사이클러뷰에 들어갈 아이템뷰의 레이아웃을 inflate.
        ImageAdapter.ViewHolder vh = new ImageAdapter.ViewHolder(view) ;

        return vh ;
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Uri image_uri = mData.get(position) ;

        Glide.with(mContext)
                .load(image_uri)
                .into(holder.image);



        db = AppDataBase_gallery.getInstance(mContext);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Image_show.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("image", image_uri.toString());

                Log.d("look", "DB에 있는가"+db.userDao().getMemo(image_uri.toString()));
                if (db.userDao().getMemo(image_uri.toString())==null){
                    User_gallery user = new User_gallery("", image_uri.toString());
                    db.userDao().insert(user);
                    Log.d("look", "made");

                }


                Log.d("look", "intent 전달"+image_uri);
                v.getContext().startActivity(intent);


            }
        });



    }



    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        return mData.size() ;
    }

}
