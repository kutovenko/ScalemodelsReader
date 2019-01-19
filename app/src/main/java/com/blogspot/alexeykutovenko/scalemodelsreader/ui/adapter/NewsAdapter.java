package com.blogspot.alexeykutovenko.scalemodelsreader.ui.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.blogspot.alexeykutovenko.scalemodelsreader.R;
import com.blogspot.alexeykutovenko.scalemodelsreader.databinding.ItemPostBinding;
import com.blogspot.alexeykutovenko.scalemodelsreader.model.Post;
import com.blogspot.alexeykutovenko.scalemodelsreader.ui.callback.PostClickCallback;

import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.PostViewHolder> {

    private List<? extends Post> news;
    @Nullable
    private final PostClickCallback postClickCallback;

    public NewsAdapter(@Nullable PostClickCallback clickCallback) {
        this.postClickCallback = clickCallback;
    }

    // For non-paging variant
    public void setPostList(final List<? extends Post> postList) {
        if (news == null) {
            news = postList;
            notifyItemRangeInserted(0, postList.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return news.size();
                }

                @Override
                public int getNewListSize() {
                    return postList.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return news.get(oldItemPosition).getStoryid()
                            .equals(postList.get(newItemPosition).getStoryid());
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    Post newPost = postList.get(newItemPosition);
                    Post oldPost = news.get(oldItemPosition);
                    return newPost.getId() == oldPost.getId()
                            && Objects.equals(newPost.getStoryid(), oldPost.getStoryid())
                            && Objects.equals(newPost.getTitle(), oldPost.getTitle())
                            && Objects.equals(newPost.getAuthor(), oldPost.getAuthor())
                            && Objects.equals(newPost.getThumbnailUrl(), oldPost.getThumbnailUrl())
                            && Objects.equals(newPost.getDate(), oldPost.getDate())
                            && Objects.equals(newPost.getIsBookmark(), oldPost.getIsBookmark());
                }
            });
            news = postList;
            result.dispatchUpdatesTo(this);
        }
    }


    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        ItemPostBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.item_post,
                        viewGroup, false);
        binding.setCallback(postClickCallback);
        return new PostViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        holder.binding.setPost(news.get(position));
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return news == null ? 0 : news.size();
    }


    static class PostViewHolder extends RecyclerView.ViewHolder {

        final ItemPostBinding binding;

        PostViewHolder(ItemPostBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}