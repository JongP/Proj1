package com.example.viewpagerexample;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.bumptech.glide.Glide;
import com.example.viewpagerexample.Room.AppDataBase_gallery;
import com.example.viewpagerexample.Room.User_gallery;
import com.example.viewpagerexample.Room.User_wallet;

import java.io.IOException;
import java.util.List;

public class Image_show extends Activity {

    private ImageView img;
    private EditText cmt;
    private Button btn;

    PopupWindow popUp;
    boolean click = true;
    Bitmap bitmap;

    AppDataBase_gallery db;
    String uri_string;

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_image_show);

        popUp = new PopupWindow(this);

        img = findViewById(R.id.image_big);
        cmt = findViewById(R.id.comment);
        btn = findViewById(R.id.save_memo);



        try {
            uri_string = getIntent().getStringExtra("image");
            Log.d("look", "string"+uri_string);
            Uri uri = Uri.parse(uri_string);
            bitmap = ImageDecoder.decodeBitmap(ImageDecoder.createSource(getContentResolver(), uri));
            img.setImageBitmap(bitmap);

        } catch (IOException e) {
            e.printStackTrace();
        }

        db = AppDataBase_gallery.getInstance(this);
        List<User_gallery> user_galleryList = db.userDao().getAll();


        Log.d("look", "bitmap came");

        for(User_gallery user_gallery : user_galleryList){
            if(user_gallery.getUri().equals(uri_string)){
                cmt.setText(user_gallery.getMemo());

                break;
            }
        }

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String memo = cmt.getText().toString();
                db.userDao().update(memo, uri_string);
            }
        });









        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();

        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;

        layoutParams.dimAmount = 0.7f;

        getWindow().setAttributes(layoutParams);



    }
}