package com.blogspot.alexeykutovenko.scalemodelsreader.ui.adapters;

import androidx.databinding.DataBindingUtil;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.blogspot.alexeykutovenko.scalemodelsreader.R;
import com.blogspot.alexeykutovenko.scalemodelsreader.databinding.ItemPostBinding;
import com.blogspot.alexeykutovenko.scalemodelsreader.model.Post;
import com.blogspot.alexeykutovenko.scalemodelsreader.model.PostEntity;
import com.blogspot.alexeykutovenko.scalemodelsreader.ui.callbacks.BookmarkClickCallback;
import com.blogspot.alexeykutovenko.scalemodelsreader.ui.callbacks.PostClickCallback;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.PostViewHolder> {

    private List<? extends Post> mPostList;

    @Nullable
    private final PostClickCallback mPostClickCallback;
    private final BookmarkClickCallback mBookmarkClickCallback;

    public NewsAdapter(@Nullable PostClickCallback clickCallback, BookmarkClickCallback bookmarkClickCallback) {
        mPostClickCallback = clickCallback;
        mBookmarkClickCallback = bookmarkClickCallback;
    }

    // For non-paging variant
    public void setPostList(final List<? extends Post> postList) {
        if (mPostList == null) {
            mPostList = postList;
            notifyItemRangeInserted(0, postList.size());
        } else {
            // TODO: 02.10.2018 optimize differs

            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return mPostList.size();
                }

                @Override
                public int getNewListSize() {
                    return postList.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return mPostList.get(oldItemPosition).getId() ==
                            postList.get(newItemPosition).getId();
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    Post newPost = postList.get(newItemPosition);
                    Post oldPost = mPostList.get(oldItemPosition);
                    return newPost.getId() == oldPost.getId()
                            && Objects.equals(newPost.getStoryid(), oldPost.getStoryid())
                            && Objects.equals(newPost.getTitle(), oldPost.getTitle())
                            && Objects.equals(newPost.getAuthor(), oldPost.getAuthor())
                            && Objects.equals(newPost.getThumbnailUrl(), oldPost.getThumbnailUrl())
                            && Objects.equals(newPost.getDate(), oldPost.getDate())
                            && Objects.equals(newPost.getIsBookmark(), oldPost.getIsBookmark());
                }
            });
            mPostList = postList;
            result.dispatchUpdatesTo(this);
        }
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        ItemPostBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.item_post,
                        viewGroup, false);
        binding.setCallback(mPostClickCallback);
        binding.setBookmarkCallback(mBookmarkClickCallback);
        return new PostViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        holder.binding.setPost(mPostList.get(position));
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mPostList == null ? 0 : mPostList.size();
    }

    static class PostViewHolder extends RecyclerView.ViewHolder {

        final ItemPostBinding binding;

        PostViewHolder(ItemPostBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public void getCategory() {
//        List<? extends Post> result = mPostList.stream();
//
//
//        mPostList.sort(Comparator.comparing(PostEntity::getDate).reversed());
//        notifyItemRangeChanged(0, mPostList.size());
//
//
//        List<String> result = lines.stream()                // convert list to stream
//                .filter(line -> !"mkyong".equals(line))     // we dont like mkyong
//                .collect(Collectors.toList());
    }
}