package com.example.viewpagerexample.fragment;

import android.app.Activity;
import android.content.ClipData;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.exifinterface.media.ExifInterface;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.viewpagerexample.BuildConfig;
import com.example.viewpagerexample.Image_show;
import com.example.viewpagerexample.R;
import com.example.viewpagerexample.Room.AppDataBase_gallery;
import com.example.viewpagerexample.adapters.ImageAdapter;
import com.example.viewpagerexample.adapters.RecyclerViewDecoration;
import com.example.viewpagerexample.gallery_camera;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;


public class FragGallery extends Fragment {

    private View view;
    private Button add;
    private Button remove;
    private FloatingActionButton cam;

    private ImageView img1;
    private ImageView img2;
    private Integer cnt=0;
    String cnt_s;
    String image_num = "count";
    static private String SHARE_NAME = "SHARE_PREF";
    static SharedPreferences sharePref = null;
    static SharedPreferences.Editor editor = null;

    private static final String TAG = "MultiImageActivity";
    ArrayList<Uri> uriList = new ArrayList<>();     // 이미지의 uri를 담을 ArrayList 객체

    RecyclerView recyclerView;  // 이미지를 보여줄 리사이클러뷰
    ImageAdapter adapter;  // 리사이클러뷰에 적용시킬 어댑터
    GridLayoutManager gridLayoutManager;

    AppDataBase_gallery db;

    File file;

    private SwipeRefreshLayout swipe;

    private String mCurrentPhotoPath;



    //상태 저장하기
    public static FragGallery newInstance() {
        FragGallery fragGallery = new FragGallery();
        return fragGallery;
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_gallery, container, false);


        add = (Button) view.findViewById(R.id.getGallery);
        remove = (Button)view.findViewById(R.id.remove);
        recyclerView = view.findViewById(R.id.recyclerView);
        cam = view.findViewById(R.id.getCamera);

        sharePref = getActivity().getSharedPreferences(SHARE_NAME, Context.MODE_PRIVATE);
        editor = sharePref.edit();
        gridLayoutManager = new GridLayoutManager(getContext(), 4);
        db = AppDataBase_gallery.getInstance(getContext());

        swipe = view.findViewById(R.id.swipelayout);

        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Map<String, ?> totalValue = sharePref.getAll();
                cnt = sharePref.getInt("Count",0 );
                Log.d("count", "갯수:"+cnt);
                Toast.makeText(getContext(), "갯수:"+cnt, Toast.LENGTH_SHORT).show();

                if(cnt==0){
                    uriList.clear();
                }

                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(gridLayoutManager);

                swipe.setRefreshing(false);
            }
        });


        Map<String, ?> totalValue = sharePref.getAll();
        cnt = sharePref.getInt("Count",0 );
        Log.d("count", "갯수:"+cnt);
        Toast.makeText(getContext(), "갯수:"+cnt, Toast.LENGTH_SHORT).show();


        try {
            for(int i=0;i<cnt;i++){
                Log.d("look", "for 성공");
                String imgpath = getActivity().getCacheDir() + "/" + i;   // 내부 저장소에 저장되어 있는 이미지 경로
                Log.d("look", "impath 성공"+imgpath);
                Bitmap bm = BitmapFactory.decodeFile(imgpath);
                Log.d("look", "비트맵성공"+bm);

                ExifInterface ei = new ExifInterface(imgpath);
                int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                        ExifInterface.ORIENTATION_UNDEFINED);

//                            //사진해상도가 너무 높으면 비트맵으로 로딩
                            BitmapFactory.Options options = new BitmapFactory.Options();
                            options.inSampleSize = 8; //8분의 1크기로 비트맵 객체 생성
                            Bitmap bitmap = BitmapFactory.decodeFile(imgpath, options);

                Bitmap rotatedBitmap = null;
                switch (orientation) {

                    case ExifInterface.ORIENTATION_ROTATE_90:
                        rotatedBitmap = rotateImage(bm, 90);
                        break;

                    case ExifInterface.ORIENTATION_ROTATE_180:
                        rotatedBitmap = rotateImage(bm, 180);
                        break;

                    case ExifInterface.ORIENTATION_ROTATE_270:
                        rotatedBitmap = rotateImage(bm, 270);
                        break;

                    case ExifInterface.ORIENTATION_NORMAL:
                    default:
                        rotatedBitmap = bm;
                }



                Uri uri_set = getImageUri(getContext(), rotatedBitmap);
                Log.d("look", "uri 성공");

                uriList.add(uri_set);
            }
        } catch (Exception e) {
            Toast.makeText(getContext(), "파일 로드 실패", Toast.LENGTH_SHORT).show();
        }

        adapter = new ImageAdapter(uriList, getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.addItemDecoration(new RecyclerViewDecoration(getContext(), 3,3));

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 100);
            }
        });

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                removeData();
                db.userDao().deleteAll();
            }
        });

        file = new File(getActivity().getCacheDir(), cnt.toString());


        cam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), gallery_camera.class);
                startActivity(intent);
            }
        });





        return view;
    }



    public void capture(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        //intent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(getContext(),BuildConfig.APPLICATION_ID+".fileprovider",file));
        //Log.d("look", "파일경로"+file);
        startActivityForResult(intent, 101);
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) { // 갤러리
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("look", "액티비티 결과 옴");

        if (requestCode == 100) {
            if (resultCode == getActivity().RESULT_OK) {
                //ClipData 또는 Uri를 가져온다
                Uri uri = data.getData();
                ClipData clipData = data.getClipData();

                //이미지 URI 를 이용하여 이미지뷰에 순서대로 세팅한다.
                if (clipData != null) {

                    if(clipData.getItemCount() > 20) {   // 선택한 이미지가 11장 이상인 경우
                        Toast.makeText(getContext(), "사진은 10장까지 선택 가능합니다.", Toast.LENGTH_LONG).show();
                    }

                    else {
                        ContentResolver resolver = getActivity().getContentResolver();
                        for (int i = 0; i < clipData.getItemCount(); i++) {

                            Uri uri_re = clipData.getItemAt(i).getUri();

                            try {
                                uriList.add(uri_re);  //uri를 list에 담는다.
                                InputStream instream = resolver.openInputStream(uri_re);
                                Bitmap imgBitmap = BitmapFactory.decodeStream(instream);

                                instream.close();   // 스트림 닫아주기
                                saveBitmapToJpeg(imgBitmap);    // 내부 저장소에 저장
                            } catch (Exception e) {
                                Toast.makeText(getContext(), "파일 불러오기 실패", Toast.LENGTH_SHORT).show();
                            }

                        }




                    }
                } else if (uri != null) {
                    ContentResolver resolver = getActivity().getContentResolver();

                    uriList.add(uri);

                    try {
                        InputStream instream = resolver.openInputStream(uri);
                        Bitmap imgBitmap = BitmapFactory.decodeStream(instream);

                        instream.close();   // 스트림 닫아주기
                        saveBitmapToJpeg(imgBitmap);    // 내부 저장소에 저장

                    } catch (Exception e) {
                        Toast.makeText(getContext(), "파일 불러오기 실패", Toast.LENGTH_SHORT).show();
                    }


                }
            }
        }


        updateData(cnt);
        adapter = new ImageAdapter(uriList, getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.addItemDecoration(new RecyclerViewDecoration(getContext(), 3,3));
    }


    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }

    public void saveBitmapToJpeg (Bitmap bitmap){   // 선택한 이미지 내부 저장소에 저장
        File tempFile = new File(getActivity().getCacheDir(), cnt.toString());
        Log.d("look", "파일경로"+tempFile);

        // 파일 경로와 이름 넣기
        try {
            tempFile.createNewFile();   // 자동으로 빈 파일을 생성하기
            FileOutputStream out = new FileOutputStream(tempFile);  // 파일을 쓸 수 있는 스트림을 준비하기


            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);   // compress 함수를 사용해 스트림에 비트맵을 저장하기
            out.close();    // 스트림 닫아주기
            cnt++;
        } catch (Exception e) {
            Toast.makeText(getContext(), "파일 저장 실패", Toast.LENGTH_SHORT).show();
        }
    }



    private Uri getImageUri(Context context, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        Log.d("look", "compress 성공");
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), inImage, "Title", null);
        Log.d("look", "insert 성공"+path);
        Uri i = Uri.parse(path);
        Log.d("look", "parse 성공");
        return i;
    }


    public void updateData(int cnt){
        editor.putInt("Count", cnt);
        editor.apply();
    }

    public void removeData(){
        editor.putInt("Count", 0);
        editor.apply();
    }



}
