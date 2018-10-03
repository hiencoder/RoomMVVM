package com.example.hiennv.basicsampleroomdatabinding.db;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;

import com.example.hiennv.basicsampleroomdatabinding.model.CommentEntity;
import com.example.hiennv.basicsampleroomdatabinding.model.ProductEntity;

import java.util.List;

public class DataRepository {
    private static DataRepository sDataRepository;
    private final MyDatabase myDatabase;

    private MediatorLiveData<List<ProductEntity>> observableProducts;

    public DataRepository(MyDatabase myDatabase) {
        this.myDatabase = myDatabase;

        observableProducts = new MediatorLiveData<>();
        observableProducts.addSource(myDatabase.productDao().getAllProduct(),productEntities -> {
            if (myDatabase.getDatabaseCreated().getValue() != null){
                //Neu database da duoc tao
                observableProducts.postValue(productEntities);
            }
        });
    }

    public static DataRepository getInstance(MyDatabase myDatabase){
        if (sDataRepository == null){
            synchronized (DataRepository.class){
                if (sDataRepository == null){
                    sDataRepository = new DataRepository(myDatabase);
                }
            }
        }
        return sDataRepository;
    }

    //Lay toan bo product
    public LiveData<List<ProductEntity>> getProducts(){
        return observableProducts;
    }

    //Lay product theo id
    public LiveData<ProductEntity> getProductById(int productId){
        return myDatabase.productDao().getProductById(productId);
    }

    //Lay danh sach comment theo productId
    public LiveData<List<CommentEntity>> getCommentByProduct(int productId){
        return myDatabase.commentDao().loadCommentByProduct(productId);
    }


}
