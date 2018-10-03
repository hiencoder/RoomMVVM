package com.example.hiennv.basicsampleroomdatabinding.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hiennv.basicsampleroomdatabinding.R;
import com.example.hiennv.basicsampleroomdatabinding.callback.CommentClickListener;
import com.example.hiennv.basicsampleroomdatabinding.databinding.CommentItemBinding;
import com.example.hiennv.basicsampleroomdatabinding.model.Comment;
import com.example.hiennv.basicsampleroomdatabinding.model.CommentEntity;

import java.util.List;
import java.util.Objects;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentHolder> {
    private List<? extends Comment> comments;
    private CommentClickListener commentClickListener;

    public CommentAdapter(CommentClickListener commentClickListener) {
        this.commentClickListener = commentClickListener;
    }

    @NonNull
    @Override
    public CommentHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        CommentItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.comment_item,parent,false);
        binding.setCallback(commentClickListener);
        return new CommentHolder(binding);
    }

    public void setListComment(List<? extends Comment> list) {
        if (comments == null){
            comments = list;
            notifyItemRangeInserted(0,list.size());
        }else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return comments.size();
                }

                @Override
                public int getNewListSize() {
                    return list.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return comments.get(oldItemPosition).getId() == list.get(newItemPosition).getId();
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    Comment oldComment = comments.get(oldItemPosition);
                    Comment newComment = list.get(newItemPosition);
                    return oldComment.getId() == newComment.getId()
                            && Objects.equals(oldComment.getText(),newComment.getText())
                            && Objects.equals(oldComment.getPostedAt(),newComment.getPostedAt())
                            && oldComment.getProductId() == newComment.getProductId();
                }
            });
            comments = list;
            result.dispatchUpdatesTo(this);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull CommentHolder commentHolder, int position) {
        commentHolder.binding.setComment(comments.get(position));
        commentHolder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return (comments == null) ? 0 : comments.size();
    }

    public class CommentHolder extends RecyclerView.ViewHolder {
        CommentItemBinding binding;

        public CommentHolder(@NonNull CommentItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
