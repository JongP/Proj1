package com.example.viewpagerexample.adapters;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.viewpagerexample.ContactItem;
import com.example.viewpagerexample.JoonActivity;
import com.example.viewpagerexample.R;
import com.example.viewpagerexample.utils.ContactData;

import java.util.List;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder> {

    private Context mcontext;
    private LayoutInflater inflater;
    private List<ContactItem> mListContacts;

    public ContactsAdapter(Context context, List<ContactItem> mListContacts) {
        this.mcontext = context;
        this.mListContacts = mListContacts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("ViewHolder","here");
        inflater = LayoutInflater.from(mcontext);
        View view = inflater.inflate(R.layout.contact_item_list,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ContactsAdapter.ViewHolder holder, int position) {

        ImageView contact_thumbnail;
        TextView contact_name, contact_num;

        contact_thumbnail = holder.contact_thumbnail;
        contact_name = holder.contact_name;
        contact_num = holder.contact_num;

        contact_name.setText(mListContacts.get(position).getContactName());
        contact_num.setText(mListContacts.get(position).getContactNum());

        Uri thumbnail = mListContacts.get(position).getContactThumbnail();
        if(thumbnail!=null){
            contact_thumbnail.setImageURI(thumbnail);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pNum = "tel:"+mListContacts.get(position).getContactNum();
                Intent test_intent = new Intent(Intent.ACTION_DIAL, Uri.parse(pNum));
                mcontext.startActivity(test_intent);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                if(mListContacts.get(position).getContactName().equals("최준")){
                    Intent joon_intent = new Intent(mcontext, JoonActivity.class);
                    mcontext.startActivity(joon_intent);
                    return true;

                }


                String pNum = "tel:"+mListContacts.get(position).getContactNum();
                Intent test_intent = new Intent(Intent.ACTION_CALL, Uri.parse(pNum));
                mcontext.startActivity(test_intent);
                return true;
            }
        });


    }

    @Override
    public int getItemCount() {
        if(mListContacts==null){
            return 0;
        }
        return mListContacts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView contact_thumbnail;
        TextView contact_name, contact_num;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            contact_thumbnail = itemView.findViewById(R.id.iv_contactprofile);
            contact_name = itemView.findViewById(R.id.tv_conatactname);
            contact_num = itemView.findViewById(R.id.tv_conatactnum);


        }
    }


}
