package com.example.viewpagerexample.fragment;

import android.content.ClipData;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.viewpagerexample.R;
import com.example.viewpagerexample.adapters.ImageAdapter;
import com.example.viewpagerexample.adapters.RecyclerViewDecoration;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Map;

public class FragGallery extends Fragment {
    private View view;
    private Button add;
    private Button remove;

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


    //상태 저장하기
    public static FragGallery newInstance() {
        FragGallery fragGallery = new FragGallery();
        return fragGallery;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_gallery, container, false);

        initialized(view);

        add = (Button) view.findViewById(R.id.getGallery);
        remove = (Button)view.findViewById(R.id.remove);
        recyclerView = view.findViewById(R.id.recyclerView);

        sharePref = getActivity().getSharedPreferences(SHARE_NAME, Context.MODE_PRIVATE);
        editor = sharePref.edit();



        Map<String, ?> totalValue = sharePref.getAll();
        cnt = sharePref.getInt("Count",0 );
        Log.d("count", "갯수:"+cnt);

        try {
            for(int i=0;i<cnt;i++){
                String imgpath = getActivity().getCacheDir() + "/" + i;   // 내부 저장소에 저장되어 있는 이미지 경로
                Bitmap bm = BitmapFactory.decodeFile(imgpath);
                Uri uri_set = getImageUri(getContext(), bm);
                uriList.add(uri_set);
            }
        } catch (Exception e) {
            Toast.makeText(getContext(), "파일 로드 실패", Toast.LENGTH_SHORT).show();
        }

        adapter = new ImageAdapter(uriList, getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4));
        //recyclerView.addItemDecoration(new RecyclerViewDecoration(5, 5));

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
            }
        });

        return view;
    }

    public void initialized(View view){



    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) { // 갤러리
        super.onActivityResult(requestCode, resultCode, data);
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
                            updateData(cnt);
                        adapter = new ImageAdapter(uriList, getContext());
                        recyclerView.setAdapter(adapter);   // 리사이클러뷰에 어댑터 세팅
                       recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4));     // 리사이클러뷰 수평 스크롤 적용
                       // recyclerView.addItemDecoration(new RecyclerViewDecoration(10, 10));



                    }
                } else if (uri != null) {
                    ContentResolver resolver = getActivity().getContentResolver();

                    uriList.add(uri);

                    try {
                        InputStream instream = resolver.openInputStream(uri);
                        Bitmap imgBitmap = BitmapFactory.decodeStream(instream);

                        instream.close();   // 스트림 닫아주기
                        saveBitmapToJpeg(imgBitmap);    // 내부 저장소에 저장
                        updateData(cnt);

                    } catch (Exception e) {
                        Toast.makeText(getContext(), "파일 불러오기 실패", Toast.LENGTH_SHORT).show();
                    }

                    adapter = new ImageAdapter(uriList, getContext());
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new GridLayoutManager(getContext(),4));
                    //recyclerView.addItemDecoration(new RecyclerViewDecoration(10, 10));

                }
            }
        }
    }


    public void saveBitmapToJpeg (Bitmap bitmap){   // 선택한 이미지 내부 저장소에 저장
        File tempFile = new File(getActivity().getCacheDir(), cnt.toString());
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
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
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
