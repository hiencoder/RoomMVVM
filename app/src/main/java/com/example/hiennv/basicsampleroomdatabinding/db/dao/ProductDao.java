package com.example.hiennv.basicsampleroomdatabinding.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.hiennv.basicsampleroomdatabinding.model.ProductEntity;

import java.util.List;

@Dao
public interface ProductDao {
    //Lay danh sach product
    @Query("SELECT * FROM products")
    LiveData<List<ProductEntity>> getAllProduct();

    //Them danh sach product vao bang
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<ProductEntity> list);

    //Lay product theo id
    @Query("SELECT * FROM products WHERE id = :productId")
    LiveData<ProductEntity> getProductById(int productId);

}
