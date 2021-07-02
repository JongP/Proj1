package com.example.viewpagerexample;

import android.net.Uri;

public class ContactItem {

    private String contactName, contactNum;
    private Uri contactThumbnail;

    public ContactItem(String contactName, String contactNum, Uri contactThumbnail) {
        this.contactName = contactName;
        this.contactNum = contactNum;
        this.contactThumbnail = contactThumbnail;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactNum() {
        return contactNum;
    }

    public void setContactNum(String contactNum) {
        this.contactNum = contactNum;
    }

    public Uri getContactThumbnail() {
        return contactThumbnail;
    }

    public void setContactThumbnail(Uri contactThumbnail) {
        this.contactThumbnail = contactThumbnail;
    }
}
