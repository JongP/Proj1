package com.example.viewpagerexample.Room;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Wallet")
public class User_wallet {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "symbol") //컬럼명 변수명과 다르게 사용 가능
    private String sym;

    @ColumnInfo(name = "quantity") //컬럼명 변수명과 다르게 사용 가능
    private int quan;

    @ColumnInfo(name = "value") //컬럼명 변수명과 다르게 사용 가능
    private Double val;

    public User_wallet(String sym, int quan, Double val) {
        this.sym = sym;
        this.quan = quan;
        this.val = val;
    }


    public String getSym() {
        return sym;
    }

    public void setSym(String sym) {
        this.sym = sym;
    }

    public int getQuan() {
        return quan;
    }

    public void setQuan(int quan) {
        this.quan = quan;
    }

    public Double getVal() {
        return val;
    }

    public void setVal(Double val) {
        this.val = val;
    }
}

