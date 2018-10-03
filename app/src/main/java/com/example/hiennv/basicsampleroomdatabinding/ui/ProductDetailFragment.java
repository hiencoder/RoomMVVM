package com.example.hiennv.basicsampleroomdatabinding.ui;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hiennv.basicsampleroomdatabinding.R;
import com.example.hiennv.basicsampleroomdatabinding.adapter.CommentAdapter;
import com.example.hiennv.basicsampleroomdatabinding.callback.CommentClickListener;
import com.example.hiennv.basicsampleroomdatabinding.databinding.FragmentProductDetailBinding;
import com.example.hiennv.basicsampleroomdatabinding.model.Comment;
import com.example.hiennv.basicsampleroomdatabinding.model.CommentEntity;
import com.example.hiennv.basicsampleroomdatabinding.model.ProductEntity;
import com.example.hiennv.basicsampleroomdatabinding.viewmodel.ProductViewModel;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductDetailFragment extends Fragment implements CommentClickListener {
    public static final String TAG = ProductDetailFragment.class.getSimpleName();

    private FragmentProductDetailBinding binding;
    private CommentAdapter commentAdapter;
    private int productId;

    public static ProductDetailFragment newInstance(int productId) {
        ProductDetailFragment productDetailFragment = new ProductDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("product_id", productId);
        productDetailFragment.setArguments(bundle);
        return productDetailFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            productId = bundle.getInt("product_id");
        }
    }

    public ProductDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_product_detail, container, false);
        commentAdapter = new CommentAdapter(this);
        binding.rvComments.setAdapter(commentAdapter);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ProductViewModel.Factory factory = new ProductViewModel.Factory(getActivity().getApplication(), productId);
        ProductViewModel productViewModel = ViewModelProviders.of(this, factory).get(ProductViewModel.class);
        binding.setProductViewModel(productViewModel);
        subscribeToModel(productViewModel);
    }

    private void subscribeToModel(ProductViewModel productViewModel) {
        //Observer product
        productViewModel.getObservableProduct().observe(this, new Observer<ProductEntity>() {
            @Override
            public void onChanged(@Nullable ProductEntity productEntity) {
                productViewModel.setProduct(productEntity);
            }
        });

        //Observer comment
        productViewModel.getComments().observe(this, new Observer<List<CommentEntity>>() {
            @Override
            public void onChanged(@Nullable List<CommentEntity> commentEntities) {
                if (commentEntities != null){
                    binding.setIsLoading(false);
                    commentAdapter.setListComment(commentEntities);
                }else {
                    binding.setIsLoading(true);
                }
            }
        });
    }

    @Override
    public void onClick(Comment comment) {

    }
}
