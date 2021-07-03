package com.example.viewpagerexample.Room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDao_gallery {

    @Insert
    void insert(User_gallery user);

    @Update
    void update(User_gallery user);




    @Query("UPDATE Memo SET memo =:t WHERE uri_id = :p")
    void update(String t, String p);

    @Query("SELECT * FROM Memo WHERE uri_id =:p")
    User_gallery getMemo(String p);



    @Delete

    void delete(User_gallery user);

    @Query("SELECT * FROM  Memo")
    List<User_gallery> getAll();

    @Query("DELETE FROM Memo")
    void deleteAll();

    @Query("SELECT COUNT(*) as cnt FROM Memo")
    int getDataCount();
}
