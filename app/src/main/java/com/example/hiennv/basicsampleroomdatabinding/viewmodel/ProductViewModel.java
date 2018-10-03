package com.example.hiennv.basicsampleroomdatabinding.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.example.hiennv.basicsampleroomdatabinding.app.MyApplication;
import com.example.hiennv.basicsampleroomdatabinding.db.DataRepository;
import com.example.hiennv.basicsampleroomdatabinding.model.CommentEntity;
import com.example.hiennv.basicsampleroomdatabinding.model.ProductEntity;

import java.util.List;

public class ProductViewModel extends AndroidViewModel{
    public static final String TAG = ProductViewModel.class.getSimpleName();
    private LiveData<ProductEntity> observableProduct;
    public ObservableField<ProductEntity> product = new ObservableField<>();
    private int productId;
    private final LiveData<List<CommentEntity>> observableComment;

    public ProductViewModel(@NonNull Application application, DataRepository dataRepository,int productId) {
        super(application);
        this.productId = productId;
        observableComment = dataRepository.getCommentByProduct(this.productId);
        observableProduct = dataRepository.getProductById(this.productId);
    }

    public LiveData<List<CommentEntity>> getComments() {
        return observableComment;
    }

    public LiveData<ProductEntity> getObservableProduct() {
        return observableProduct;
    }

    public void setProduct(ProductEntity productEntity){
        product.set(productEntity);
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory{
        private final Application application;
        private final int productId;
        private final DataRepository dataRepository;

        public Factory(Application application, int productId) {
            this.application = application;
            this.productId = productId;
            this.dataRepository = ((MyApplication)application).getDataRepository();
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new ProductViewModel(application,dataRepository,productId);
        }
    }
}
