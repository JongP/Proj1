package com.example.viewpagerexample.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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


    //상태 저장하기
    public static FragContacts newInstance() {
        FragContacts fragContacts = new FragContacts();
        return fragContacts;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_contacts, container, false);

        recyclerView = view.findViewById(R.id.rv_contacts);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

        RecyclerView.LayoutManager layoutManager = linearLayoutManager;
        recyclerView.setLayoutManager(layoutManager);


        ContactsAdapter adapter = new ContactsAdapter(getContext(), getContacts());

        recyclerView.setAdapter(adapter);

        /*Intent test_intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:010-5367-7162"));
        startActivity(test_intent);*/

        return view;
    }

    private List<ContactItem> getContacts() {


        @SuppressLint("MissingPermission") List<ContactData> contactsList = ContactUtilsKt.retrieveAllContacts(getContext());

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
}



//Test
        /**/
