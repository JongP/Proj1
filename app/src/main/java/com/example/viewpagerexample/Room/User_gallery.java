package com.example.viewpagerexample.Room;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Memo")
public class User_gallery {


    @NonNull
    @ColumnInfo(name = "memo") //컬럼명 변수명과 다르게 사용 가능
    private String memo;

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "uri_id") //컬럼명 변수명과 다르게 사용 가능
    private String uri;

    public User_gallery(@NonNull String memo, String uri) {
        this.memo = memo;
        this.uri = uri;
    }

    @NonNull
    public String getMemo() {
        return memo;
    }

    public void setMemo(@NonNull String memo) {
        this.memo = memo;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}

