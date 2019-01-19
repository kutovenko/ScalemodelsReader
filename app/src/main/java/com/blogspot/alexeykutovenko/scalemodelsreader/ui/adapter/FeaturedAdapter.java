package com.blogspot.alexeykutovenko.scalemodelsreader.ui.adapter;

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
import com.blogspot.alexeykutovenko.scalemodelsreader.ui.callback.PostClickCallback;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class FeaturedAdapter extends RecyclerView.Adapter<FeaturedAdapter.FeaturedViewHolder> {
    private List<? extends Post> featuredList;
    @Nullable
    private final PostClickCallback featuredClickCallback;

    public FeaturedAdapter(@Nullable PostClickCallback clickCallback) {
        featuredClickCallback = clickCallback;
    }

    public void setFeaturedList(final List<? extends Post> featuredList) {
        if (this.featuredList == null) {
            this.featuredList = featuredList;
            notifyItemRangeInserted(0, featuredList.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return FeaturedAdapter.this.featuredList.size();
                }

                @Override
                public int getNewListSize() {
                    return featuredList.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return FeaturedAdapter.this.featuredList.get(oldItemPosition).getId() ==
                            featuredList.get(newItemPosition).getId();
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    Post newFeatured = featuredList.get(newItemPosition);
                    Post oldFeatured = FeaturedAdapter.this.featuredList.get(oldItemPosition);
                    return newFeatured.getId() == oldFeatured.getId()
                            && Objects.equals(newFeatured.getStoryid(), oldFeatured.getStoryid())
                            && Objects.equals(newFeatured.getTitle(), oldFeatured.getTitle())
                            && Objects.equals(newFeatured.getAuthor(), oldFeatured.getAuthor())
                            && Objects.equals(newFeatured.getThumbnailUrl(), oldFeatured.getThumbnailUrl())
                            && Arrays.equals(newFeatured.getImagesUrls(), oldFeatured.getImagesUrls())
                            && Objects.equals(newFeatured.getOriginalUrl(), oldFeatured.getOriginalUrl())
                            && Objects.equals(newFeatured.getDate(), oldFeatured.getDate())
                            && Objects.equals(newFeatured.getDescription(), oldFeatured.getDescription())
                            && Objects.equals(newFeatured.getLastUpdate(), oldFeatured.getLastUpdate())
                            && Objects.equals(newFeatured.getIsBookmark(), oldFeatured.getIsBookmark());
                }
            });
            this.featuredList = featuredList;
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
        binding.setCallback(featuredClickCallback);
        return new FeaturedAdapter.FeaturedViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull FeaturedAdapter.FeaturedViewHolder featuredViewHolder,
                                 int position) {
        featuredViewHolder.binding.setPost(featuredList.get(position));
        featuredViewHolder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return featuredList == null ? 0 : featuredList.size();
    }


    static class FeaturedViewHolder extends RecyclerView.ViewHolder {

        final ItemFeaturedBinding binding;

        FeaturedViewHolder(ItemFeaturedBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}