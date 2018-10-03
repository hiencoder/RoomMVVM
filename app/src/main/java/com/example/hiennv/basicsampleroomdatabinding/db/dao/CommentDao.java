package com.example.hiennv.basicsampleroomdatabinding.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.hiennv.basicsampleroomdatabinding.model.CommentEntity;

import java.util.List;

@Dao
public interface CommentDao {
    //Load danh sach comment
    @Query("SELECT * FROM comments")
    LiveData<List<CommentEntity>> getAllComment();

    //Load danh sach comment cua product
    @Query("SELECT * FROM comments WHERE productId = :productId")
    LiveData<List<CommentEntity>> loadCommentByProduct(int productId);

    @Query("SELECT * FROM comments WHERE productId = :productId")
    List<CommentEntity> loadCommentsSync(int productId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<CommentEntity> comments);

}
