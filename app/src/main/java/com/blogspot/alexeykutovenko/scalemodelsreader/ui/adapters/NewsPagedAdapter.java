package com.blogspot.alexeykutovenko.scalemodelsreader.ui.adapters;

import androidx.paging.PagedListAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.blogspot.alexeykutovenko.scalemodelsreader.R;
import com.blogspot.alexeykutovenko.scalemodelsreader.databinding.ItemPostBinding;
import com.blogspot.alexeykutovenko.scalemodelsreader.model.PostEntity;
import com.blogspot.alexeykutovenko.scalemodelsreader.ui.callbacks.BookmarkClickCallback;
import com.blogspot.alexeykutovenko.scalemodelsreader.ui.callbacks.PostClickCallback;

public class NewsPagedAdapter extends PagedListAdapter<PostEntity, NewsPagedAdapter.PostViewHolder> {
    @Nullable
    private final PostClickCallback mPostClickCallback;
    private final BookmarkClickCallback mBookmarkClickCallback;

    public NewsPagedAdapter(@NonNull DiffUtil.ItemCallback<PostEntity> diffCallback,
                            @Nullable PostClickCallback mPostClickCallback,
                            BookmarkClickCallback mBookmarkClickCallback) {
        super(diffCallback);
        this.mPostClickCallback = mPostClickCallback;
        this.mBookmarkClickCallback = mBookmarkClickCallback;
//        setHasStableIds(true);
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ItemPostBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.item_post,
                        viewGroup, false);
        binding.setCallback(mPostClickCallback);
        binding.setBookmarkCallback(mBookmarkClickCallback);
        return new NewsPagedAdapter.PostViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder postViewHolder, int position) {
        postViewHolder.binding.setPost(getItem(position));
    }



    static class PostViewHolder extends RecyclerView.ViewHolder {
        final ItemPostBinding binding;

        PostViewHolder(ItemPostBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

//        public void bind(PostEntity employee) {
//            if (employee == null) {
//                textViewName.setText(R.string.wait);
//                textViewSalary.setText(R.string.wait);
//            } else {
//                textViewName.setText(employee.name);
//                textViewSalary.setText(employee.salary);
//            }
//        }
    }
}
