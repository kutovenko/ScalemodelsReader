package com.blogspot.alexeykutovenko.scalemodelsreader.ui.adapters;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.blogspot.alexeykutovenko.scalemodelsreader.R;
import com.blogspot.alexeykutovenko.scalemodelsreader.databinding.ItemFeaturedBinding;
import com.blogspot.alexeykutovenko.scalemodelsreader.model.Featured;
import com.blogspot.alexeykutovenko.scalemodelsreader.ui.callbacks.FeaturedClickCallback;

import java.util.List;
import java.util.Objects;

public class FeaturedAdapter extends RecyclerView.Adapter<FeaturedAdapter.FeaturedViewHolder> {

    List<? extends Featured> mFeaturedList;
    @Nullable
    private final FeaturedClickCallback mFeaturedClickCallback;

    public FeaturedAdapter(@Nullable FeaturedClickCallback clickCallback) {
        mFeaturedClickCallback = clickCallback;
    }

    public void setFeaturedList(final List<? extends Featured> featuredList) {
        if (mFeaturedList == null) {
            mFeaturedList = featuredList;
            notifyItemRangeInserted(0, featuredList.size());
        } else {
            // TODO: 02.10.2018 optimize differs

            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return mFeaturedList.size();
                }

                @Override
                public int getNewListSize() {
                    return featuredList.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return mFeaturedList.get(oldItemPosition).getId() ==
                            featuredList.get(newItemPosition).getId();
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    Featured newFeatured = featuredList.get(newItemPosition);
                    Featured oldFeatured = mFeaturedList.get(oldItemPosition);
                    return newFeatured.getId() == oldFeatured.getId()
                            && Objects.equals(newFeatured.getOnlineId(), oldFeatured.getOnlineId())
                            && Objects.equals(newFeatured.getTitle(), oldFeatured.getTitle())
                            && Objects.equals(newFeatured.getAuthor(), oldFeatured.getAuthor())
                            && Objects.equals(newFeatured.getThumbnailUrl(), oldFeatured.getThumbnailUrl())
                            && Objects.equals(newFeatured.getImagesUrls(), oldFeatured.getImagesUrls())
                            && Objects.equals(newFeatured.getOriginalUrl(), oldFeatured.getOriginalUrl())
                            && Objects.equals(newFeatured.getDate(), oldFeatured.getDate())
                            && Objects.equals(newFeatured.getCategoryId(), oldFeatured.getCategoryId())
                            && Objects.equals(newFeatured.getCategoryName(), oldFeatured.getCategoryName())
                            && Objects.equals(newFeatured.getDescription(), oldFeatured.getDescription())
                            && Objects.equals(newFeatured.getLastUpdateDate(), oldFeatured.getLastUpdateDate())
                            && Objects.equals(newFeatured.getIsFavorite(), oldFeatured.getIsFavorite());
                }
            });
            mFeaturedList = featuredList;
            result.dispatchUpdatesTo(this);
        }
    }

    @NonNull
    @Override
    public FeaturedAdapter.FeaturedViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup,
                                                                   int position) {
        ItemFeaturedBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.item_featured,
                        viewGroup, false);
        binding.setCallback(mFeaturedClickCallback);
        // TODO: 30.09.2018 binding
        return new FeaturedAdapter.FeaturedViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull FeaturedAdapter.FeaturedViewHolder featuredViewHolder,
                                 int position) {
        featuredViewHolder.binding.setFeatured(mFeaturedList.get(position));///////////
        featuredViewHolder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mFeaturedList == null ? 0 : mFeaturedList.size();
    }


    static class FeaturedViewHolder extends RecyclerView.ViewHolder {

        final ItemFeaturedBinding binding;

        FeaturedViewHolder(ItemFeaturedBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}

