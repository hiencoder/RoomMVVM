package com.example.hiennv.basicsampleroomdatabinding.ui;


import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.hiennv.basicsampleroomdatabinding.R;
import com.example.hiennv.basicsampleroomdatabinding.adapter.ProductAdapter;
import com.example.hiennv.basicsampleroomdatabinding.callback.ProductClickListener;
import com.example.hiennv.basicsampleroomdatabinding.databinding.FragmentProductListBinding;
import com.example.hiennv.basicsampleroomdatabinding.model.Product;
import com.example.hiennv.basicsampleroomdatabinding.model.ProductEntity;
import com.example.hiennv.basicsampleroomdatabinding.viewmodel.ProductListViewModel;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProductListFragment extends Fragment implements ProductClickListener{
    public static final String TAG = ProductListFragment.class.getSimpleName();
    private ProductAdapter productAdapter;
    private FragmentProductListBinding binding;

    public static ProductListFragment newInstance(){
        ProductListFragment fragment = new ProductListFragment();

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public ProductListFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_product_list,container,false);
        productAdapter = new ProductAdapter(this::onClick);
        binding.rvProducts.setAdapter(productAdapter);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ProductListViewModel viewModel = ViewModelProviders.of(this).get(ProductListViewModel.class);
        subscribeUi(viewModel);
    }

    private void subscribeUi(ProductListViewModel viewModel) {
        //Cap nhat ui khi data thay doi
        viewModel.getObservableProduct().observe(this, new Observer<List<ProductEntity>>() {
            @Override
            public void onChanged(@Nullable List<ProductEntity> productEntities) {
                if (productEntities != null){
                    binding.setIsLoading(false);
                    productAdapter.setProducts(productEntities);
                }else {
                    binding.setIsLoading(true);
                }
                binding.executePendingBindings();
            }
        });
    }

    @Override
    public void onClick(Product product) {
        if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)){
            ((MainActivity)getActivity()).showProduct(product);
        }
    }
}
