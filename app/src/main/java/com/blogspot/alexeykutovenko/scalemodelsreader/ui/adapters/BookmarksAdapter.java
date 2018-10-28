package com.blogspot.alexeykutovenko.scalemodelsreader.ui.adapters;

import androidx.databinding.DataBindingUtil;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import com.blogspot.alexeykutovenko.scalemodelsreader.R;
import com.blogspot.alexeykutovenko.scalemodelsreader.databinding.ItemPostBinding;
//import com.blogspot.alexeykutovenko.scalemodelsreader.databinding.ItemPostBinding;
import com.blogspot.alexeykutovenko.scalemodelsreader.model.Post;
import com.blogspot.alexeykutovenko.scalemodelsreader.model.PostEntity;
import com.blogspot.alexeykutovenko.scalemodelsreader.ui.callbacks.PostClickCallback;
import com.blogspot.alexeykutovenko.scalemodelsreader.ui.callbacks.BookmarkClickCallback;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class BookmarksAdapter extends RecyclerView.Adapter<BookmarksAdapter.BookmarksViewHolder>
        implements Filterable {
    private List<? extends Post> mBookmarksList;
    private List<PostEntity> mFilteredBookmarks = new ArrayList<>();

    @Nullable
    private final PostClickCallback mPostClickCallback;
    private final BookmarkClickCallback mBookmarkClickCallback;


    public BookmarksAdapter(@Nullable PostClickCallback clickCallback,
                            @Nullable BookmarkClickCallback mBookmarkClickCallback) {
        this.mPostClickCallback = clickCallback;
        this.mBookmarkClickCallback = mBookmarkClickCallback;
        setHasStableIds(true);
    }

    public void setBookmarksList(final List<? extends Post> bookmarksList) {
        if (mBookmarksList == null) {
            mBookmarksList = bookmarksList;
            mFilteredBookmarks.addAll((Collection<? extends PostEntity>) mBookmarksList);
            notifyItemRangeInserted(0, bookmarksList.size());
        } else {
            // TODO: 02.10.2018 optimize differs
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return mFilteredBookmarks.size();
                }

                @Override
                public int getNewListSize() {
                    return bookmarksList.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return mFilteredBookmarks.get(oldItemPosition).getId() ==
                            bookmarksList.get(newItemPosition).getId();
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    Post newPost = bookmarksList.get(newItemPosition);
                    Post oldPost = mFilteredBookmarks.get(oldItemPosition);
                    return newPost.getId() == oldPost.getId()
                            && Objects.equals(newPost.getStoryid(), oldPost.getStoryid())
                            && Objects.equals(newPost.getTitle(), oldPost.getTitle())
                            && Objects.equals(newPost.getAuthor(), oldPost.getAuthor())
                            && Objects.equals(newPost.getThumbnailUrl(), oldPost.getThumbnailUrl())
                            && Arrays.equals(newPost.getImagesUrls(), oldPost.getImagesUrls())
                            && Objects.equals(newPost.getOriginalUrl(), oldPost.getOriginalUrl())
                            && Objects.equals(newPost.getDate(), oldPost.getDate())
                            && Objects.equals(newPost.getCategory(), oldPost.getCategory())
                            && Objects.equals(newPost.getDescription(), oldPost.getDescription())
                            && Objects.equals(newPost.getLastUpdate(), oldPost.getLastUpdate())
                            && Objects.equals(newPost.getIsBookmark(), oldPost.getIsBookmark());
                }
            });
            mFilteredBookmarks = (List<PostEntity>) bookmarksList;
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
        bookmarksViewHolder.binding.setPost(mFilteredBookmarks.get(position));
        bookmarksViewHolder.binding.executePendingBindings();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mFilteredBookmarks == null ? 0 : mFilteredBookmarks.size();
    }

    public List<PostEntity> getPosts() {
        return mFilteredBookmarks;
    }

    public void sortByTitle() {
        mFilteredBookmarks.sort(Comparator.comparing(PostEntity::getTitle));
        notifyItemRangeChanged(0, mFilteredBookmarks.size());
    }

    public void sortByDate() {
        mFilteredBookmarks.sort(Comparator.comparing(PostEntity::getDate).reversed());
        notifyItemRangeChanged(0, mFilteredBookmarks.size());
    }

    static class BookmarksViewHolder extends RecyclerView.ViewHolder {
        final ItemPostBinding binding;
        int position;

        BookmarksViewHolder(ItemPostBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            this.position = getAdapterPosition();
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                List<Post> filteredList = new ArrayList<>();
                if (charString.isEmpty()) {
                    filteredList = (List<Post>) mBookmarksList;
                } else {
                    for (Post row : mBookmarksList) {
                        if (row.getTitle().toLowerCase().contains(charString.toLowerCase())
                                || row.getAuthor().getName().toLowerCase()
                                .contains(charString.toLowerCase())) {
                            // TODO: 26.10.2018 other conditions
                            filteredList.add(row);
                        }
                    }

                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mFilteredBookmarks = (List<PostEntity>) filterResults.values;
                notifyDataSetChanged();
                // TODO: 26.10.2018 notifydatarange
            }
        };
    }

}