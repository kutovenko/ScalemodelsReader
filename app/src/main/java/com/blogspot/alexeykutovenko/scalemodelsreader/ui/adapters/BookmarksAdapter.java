package com.blogspot.alexeykutovenko.scalemodelsreader.ui.adapters;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.blogspot.alexeykutovenko.scalemodelsreader.R;
import com.blogspot.alexeykutovenko.scalemodelsreader.databinding.ItemPostBinding;
import com.blogspot.alexeykutovenko.scalemodelsreader.model.Post;
import com.blogspot.alexeykutovenko.scalemodelsreader.ui.callbacks.PostClickCallback;
import com.blogspot.alexeykutovenko.scalemodelsreader.ui.callbacks.BookmarkClickCallback;

import java.util.List;
import java.util.Objects;

public class BookmarksAdapter extends RecyclerView.Adapter<BookmarksAdapter.BookmarksViewHolder> {

    private List<? extends Post> mPostList;
    @Nullable
    private final PostClickCallback mPostClickCallback;
    private final BookmarkClickCallback mBookmarkClickCallback;

    public BookmarksAdapter(@Nullable PostClickCallback clickCallback,
                            @Nullable BookmarkClickCallback mBookmarkClickCallback) {
        this.mPostClickCallback = clickCallback;
        this.mBookmarkClickCallback = mBookmarkClickCallback;
        setHasStableIds(true);
    }

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
                            && Objects.equals(newPost.getImagesUrls(), oldPost.getImagesUrls())
                            && Objects.equals(newPost.getOriginalUrl(), oldPost.getOriginalUrl())
                            && Objects.equals(newPost.getDate(), oldPost.getDate())
                            && Objects.equals(newPost.getCategory(), oldPost.getCategory())
                            && Objects.equals(newPost.getDescription(), oldPost.getDescription())
                            && Objects.equals(newPost.getLastUpdate(), oldPost.getLastUpdate())
                            && Objects.equals(newPost.getIsBookmark(), oldPost.getIsBookmark());
                }
            });
            mPostList = postList;
            result.dispatchUpdatesTo(this);
        }
    }

    @NonNull
    @Override
    public BookmarksViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup,
                                                  int position) {
        ItemPostBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.item_post,
                        viewGroup, false);
        binding.setCallback(mPostClickCallback);
        binding.setBookmarkCallback(mBookmarkClickCallback);
        return new BookmarksViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BookmarksViewHolder bookmarksViewHolder,
                                 int position) {
        bookmarksViewHolder.binding.setPost(mPostList.get(position));
        bookmarksViewHolder.binding.executePendingBindings();
    }

    @Override
    public long getItemId(int position) {
        return position;
//        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {
        return mPostList == null ? 0 : mPostList.size();
    }



    static class BookmarksViewHolder extends RecyclerView.ViewHolder {

        final ItemPostBinding binding;
        int position;//

        BookmarksViewHolder(ItemPostBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            this.position = getAdapterPosition();//
        }
    }
}