package com.example.viewpagerexample.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.viewpagerexample.ContactItem;
import com.example.viewpagerexample.R;
import com.example.viewpagerexample.adapters.ContactsAdapter;
import com.example.viewpagerexample.utils.ContactData;
import com.example.viewpagerexample.utils.ContactUtilsKt;

import java.util.ArrayList;
import java.util.List;

public class FragContacts extends Fragment {
    private View view;
    private RecyclerView recyclerView;
    static final int PERMISSIONS_REQUEST = 1004;



    //상태 저장하기
    public static FragContacts newInstance() {
        FragContacts fragContacts = new FragContacts();
        return fragContacts;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            if(ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),Manifest.permission.READ_CONTACTS)){
                Toast.makeText(getContext(),"Permission is required",Toast.LENGTH_SHORT).show();
                ActivityCompat.requestPermissions(getActivity(), new String[] {Manifest.permission.READ_CONTACTS},PERMISSIONS_REQUEST);
            }
            else{
                ActivityCompat.requestPermissions(getActivity(), new String[] {Manifest.permission.READ_CONTACTS}
                        ,PERMISSIONS_REQUEST);
            }
        }

        view = inflater.inflate(R.layout.frag_contacts, container, false);

        recyclerView = view.findViewById(R.id.rv_contacts);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

        RecyclerView.LayoutManager layoutManager = linearLayoutManager;
        recyclerView.setLayoutManager(layoutManager);



        ContactsAdapter adapter = new ContactsAdapter(getContext(), getContacts());

        recyclerView.setAdapter(adapter);

        return view;
    }

    private List<ContactItem> getContacts() {


        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            if(ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),Manifest.permission.READ_CONTACTS)){
                Toast.makeText(getContext(),"Permission is required",Toast.LENGTH_SHORT).show();
                ActivityCompat.requestPermissions(getActivity(), new String[] {Manifest.permission.READ_CONTACTS},PERMISSIONS_REQUEST);
            }
            else{
                ActivityCompat.requestPermissions(getActivity(), new String[] {Manifest.permission.READ_CONTACTS}
                        ,PERMISSIONS_REQUEST);
            }



        }

        List<ContactData> contactsList = ContactUtilsKt.retrieveAllContacts(getContext());

        List<ContactItem> list = new ArrayList<>();

        for (ContactData data : contactsList) {

            String number = "";
            String kotName = data.getName();
            List<String> kotNumber = data.getPhoneNumber();
            for (String str : kotNumber) {
                number += str;
            }

            Uri kotUri = data.getAvatar();

            Log.d("neverdie", "Name is " + kotName);
            Log.d("neverdie", "number is " + number);
            Log.d("neverdie", "Uri is " + kotUri);

            list.add(new ContactItem(kotName, number, kotUri));
        }

        return list;

    }

    public void OnCheckPermission(){
        if(ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),Manifest.permission.READ_CONTACTS)){
                Toast.makeText(getContext(),"Permission is required",Toast.LENGTH_SHORT).show();
                ActivityCompat.requestPermissions(getActivity(), new String[] {Manifest.permission.READ_CONTACTS},PERMISSIONS_REQUEST);
            }
            else{
                ActivityCompat.requestPermissions(getActivity(), new String[] {Manifest.permission.READ_CONTACTS}
                        ,PERMISSIONS_REQUEST);
            }

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults); //check
        switch (requestCode) {
            case PERMISSIONS_REQUEST:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getContext(), "앱 실행을 위한 권한이 설정 되었습니다", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Permission is retrieved", Toast.LENGTH_SHORT).show();
                }
                break;

        }
    }
}



//Test
        /**/
