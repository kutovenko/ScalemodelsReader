package com.blogspot.alexeykutovenko.scalemodelsreader.ui.adapters;

import androidx.databinding.DataBindingUtil;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.blogspot.alexeykutovenko.scalemodelsreader.R;
import com.blogspot.alexeykutovenko.scalemodelsreader.databinding.ItemFeaturedBinding;
import com.blogspot.alexeykutovenko.scalemodelsreader.model.Post;
import com.blogspot.alexeykutovenko.scalemodelsreader.ui.callbacks.PostClickCallback;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class FeaturedAdapter extends RecyclerView.Adapter<FeaturedAdapter.FeaturedViewHolder> {

    List<? extends Post> mFeaturedList;
    @Nullable
    private final PostClickCallback mFeaturedClickCallback;

    public FeaturedAdapter(@Nullable PostClickCallback clickCallback) {
        mFeaturedClickCallback = clickCallback;
    }

    public void setFeaturedList(final List<? extends Post> featuredList) {
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
                    Post newFeatured = featuredList.get(newItemPosition);
                    Post oldFeatured = mFeaturedList.get(oldItemPosition);
                    return newFeatured.getId() == oldFeatured.getId()
                            && Objects.equals(newFeatured.getStoryid(), oldFeatured.getStoryid())
                            && Objects.equals(newFeatured.getTitle(), oldFeatured.getTitle())
                            && Objects.equals(newFeatured.getAuthor(), oldFeatured.getAuthor())
                            && Objects.equals(newFeatured.getThumbnailUrl(), oldFeatured.getThumbnailUrl())
                            && Arrays.equals(newFeatured.getImagesUrls(), oldFeatured.getImagesUrls())
                            && Objects.equals(newFeatured.getOriginalUrl(), oldFeatured.getOriginalUrl())
                            && Objects.equals(newFeatured.getDate(), oldFeatured.getDate())
//                            && Objects.equals(newFeatured.getCategory(), oldFeatured.getCategory())
                            && Objects.equals(newFeatured.getDescription(), oldFeatured.getDescription())
                            && Objects.equals(newFeatured.getLastUpdate(), oldFeatured.getLastUpdate())
                            && Objects.equals(newFeatured.getIsBookmark(), oldFeatured.getIsBookmark());
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
        featuredViewHolder.binding.setPost(mFeaturedList.get(position));///////////
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