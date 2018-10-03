package com.example.hiennv.basicsampleroomdatabinding.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.support.annotation.NonNull;

import com.example.hiennv.basicsampleroomdatabinding.app.MyApplication;
import com.example.hiennv.basicsampleroomdatabinding.model.ProductEntity;

import java.util.List;

public class ProductListViewModel extends AndroidViewModel {
    private final MediatorLiveData<List<ProductEntity>> observableProduct;

    public ProductListViewModel(@NonNull Application application) {
        super(application);
        observableProduct = new MediatorLiveData<>();
        //Set data default null,
        observableProduct.setValue(null);

        LiveData<List<ProductEntity>> products = ((MyApplication) application).getDataRepository().getProducts();
        observableProduct.addSource(products, observableProduct::setValue);
    }

    public MediatorLiveData<List<ProductEntity>> getObservableProduct() {
        return observableProduct;
    }
}
