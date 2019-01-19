package com.blogspot.alexeykutovenko.scalemodelsreader.ui.adapter;

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
import com.blogspot.alexeykutovenko.scalemodelsreader.ui.callback.PostClickCallback;
import com.blogspot.alexeykutovenko.scalemodelsreader.ui.callback.BookmarkClickCallback;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class BookmarksAdapter extends RecyclerView.Adapter<BookmarksAdapter.BookmarksViewHolder>
        implements Filterable {
    private List<? extends Post> bookmarks;
    private List<PostEntity> filteredBookmarks = new ArrayList<>();

    @Nullable
    private final PostClickCallback postClickCallback;
    private final BookmarkClickCallback bookmarkClickCallback;


    public BookmarksAdapter(@Nullable PostClickCallback clickCallback,
                            @Nullable BookmarkClickCallback bookmarkClickCallback) {
        this.postClickCallback = clickCallback;
        this.bookmarkClickCallback = bookmarkClickCallback;
        setHasStableIds(true);
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


    @NonNull
    @Override
    public BookmarksViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup,
                                                  int position) {
        ItemPostBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.item_post,
                        viewGroup, false);
        binding.setCallback(postClickCallback);
        binding.setBookmarkCallback(bookmarkClickCallback);
        return new BookmarksViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BookmarksViewHolder bookmarksViewHolder,
                                 int position) {
        bookmarksViewHolder.binding.setPost(filteredBookmarks.get(position));
        bookmarksViewHolder.binding.executePendingBindings();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return filteredBookmarks == null ? 0 : filteredBookmarks.size();
    }


    public void setBookmarks(final List<? extends Post> bookmarks) {
        if (this.bookmarks == null) {
            this.bookmarks = bookmarks;
            filteredBookmarks.addAll((Collection<? extends PostEntity>) this.bookmarks);
            notifyItemRangeInserted(0, bookmarks.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return filteredBookmarks.size();
                }

                @Override
                public int getNewListSize() {
                    return bookmarks.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return filteredBookmarks.get(oldItemPosition).getId() ==
                            bookmarks.get(newItemPosition).getId();
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    Post newPost = bookmarks.get(newItemPosition);
                    Post oldPost = filteredBookmarks.get(oldItemPosition);
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
            filteredBookmarks = (List<PostEntity>) bookmarks;
            result.dispatchUpdatesTo(this);
        }
    }

    public void sortByTitle() {
        filteredBookmarks.sort(Comparator.comparing(PostEntity::getTitle));
        notifyItemRangeChanged(0, filteredBookmarks.size());
    }

    public void sortByDate() {
        filteredBookmarks.sort(Comparator.comparing(PostEntity::getDate).reversed());
        notifyItemRangeChanged(0, filteredBookmarks.size());
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                List<Post> filteredList = new ArrayList<>();
                if (charString.isEmpty()) {
                    filteredList = (List<Post>) bookmarks;
                } else {
                    for (Post row : bookmarks) {
                        if (row.getTitle().toLowerCase().contains(charString.toLowerCase())
                                || row.getAuthor().getName().toLowerCase()
                                .contains(charString.toLowerCase())) {
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
                filteredBookmarks = (List<PostEntity>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}