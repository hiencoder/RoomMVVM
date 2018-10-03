package com.example.hiennv.basicsampleroomdatabinding.ui;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.hiennv.basicsampleroomdatabinding.R;
import com.example.hiennv.basicsampleroomdatabinding.model.Product;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            ProductListFragment productListFragment = ProductListFragment.newInstance();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.add(R.id.fl_main, productListFragment, ProductListFragment.TAG).commit();
        }
    }

    /**
     *
     * @param product
     */
    public void showProduct(Product product) {
        ProductDetailFragment productDetailFragment = ProductDetailFragment.newInstance(product.getId());
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.fl_main, productDetailFragment, null).commit();
    }
}
