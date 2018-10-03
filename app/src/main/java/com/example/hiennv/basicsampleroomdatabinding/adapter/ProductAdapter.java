package com.example.hiennv.basicsampleroomdatabinding.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hiennv.basicsampleroomdatabinding.R;
import com.example.hiennv.basicsampleroomdatabinding.callback.ProductClickListener;
import com.example.hiennv.basicsampleroomdatabinding.databinding.ProductItemBinding;
import com.example.hiennv.basicsampleroomdatabinding.model.Product;

import java.util.List;
import java.util.Objects;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductHolder> {
    private List<? extends Product> products;
    private ProductClickListener productClickListener;

    public ProductAdapter(ProductClickListener productClickListener) {
        this.productClickListener = productClickListener;
    }

    /**
     * @param list
     */
    public void setProducts(List<? extends Product> list) {
        if (products == null) {
            products = list;
            notifyItemRangeInserted(0, list.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return products.size();
                }

                @Override
                public int getNewListSize() {
                    return list.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return products.get(oldItemPosition).getId() == list.get(newItemPosition).getId();
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    Product oldProduct = products.get(oldItemPosition);
                    Product newProduct = list.get(newItemPosition);

                    return newProduct.getId() == oldProduct.getId()
                            && Objects.equals(newProduct.getDescription(), oldProduct.getDescription())
                            && Objects.equals(newProduct.getName(), oldProduct.getName())
                            && newProduct.getPrice() == oldProduct.getPrice();
                }
            });
            products = list;
            result.dispatchUpdatesTo(this);
        }
    }

    @NonNull
    @Override
    public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ProductItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),R.layout.product_item,parent,false);
        binding.setCallback(productClickListener);
        return new ProductHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductHolder holder, int position) {
        holder.binding.setProduct(products.get(position));
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return products == null ? 0 : products.size();
    }

    public class ProductHolder extends RecyclerView.ViewHolder {
        ProductItemBinding binding;

        public ProductHolder(ProductItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
