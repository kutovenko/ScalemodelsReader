package com.blogspot.alexeykutovenko.scalemodelsreader.ui.fragments;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blogspot.alexeykutovenko.scalemodelsreader.R;
import com.blogspot.alexeykutovenko.scalemodelsreader.databinding.FragmentBookmarksBinding;
import com.blogspot.alexeykutovenko.scalemodelsreader.ui.MainActivity;
import com.blogspot.alexeykutovenko.scalemodelsreader.ui.callbacks.PostClickCallback;
import com.blogspot.alexeykutovenko.scalemodelsreader.ui.RecyclerViewSwipeDecorator;
import com.blogspot.alexeykutovenko.scalemodelsreader.ui.adapters.BookmarksAdapter;
import com.blogspot.alexeykutovenko.scalemodelsreader.ui.callbacks.BookmarkClickCallback;
import com.blogspot.alexeykutovenko.scalemodelsreader.viewmodel.BookmarksListViewModel;

/**
 * Fragment with the list of saved bookmarks.
 */
public class BookmarksFragment extends Fragment {
    public static String TAG = "favorites";
    private BookmarksAdapter mBookmarksAdapter;
    private FragmentBookmarksBinding mFavoritesBinding;
    private BookmarksListViewModel viewModel;

    public BookmarksFragment() {
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(BookmarksListViewModel.class);

        subscribeUi(viewModel);
    }

    private void subscribeUi(BookmarksListViewModel viewModel) {
        viewModel.getFavoritePosts().observe(this, myFavorites -> {
            if (myFavorites != null) {
                mFavoritesBinding.setIsLoading(false);
                mBookmarksAdapter.setPostList(myFavorites);
            } else {
                mFavoritesBinding.setIsLoading(true);
            }

            mFavoritesBinding.executePendingBindings();
        });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle(R.string.bookmarks);
        mFavoritesBinding = DataBindingUtil
                .inflate(inflater, R.layout.fragment_bookmarks, container, false);
        mBookmarksAdapter = new BookmarksAdapter(mPostClickCallback, mBookmarkClickCallback);
        mFavoritesBinding.rvFavoritesPostList.setAdapter(mBookmarksAdapter);


        ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onChildDraw (@NonNull Canvas c, @NonNull RecyclerView recyclerView,
                                     @NonNull RecyclerView.ViewHolder viewHolder,
                                     float dX, float dY,
                                     int actionState, boolean isCurrentlyActive){
                new RecyclerViewSwipeDecorator.Builder(getActivity(), c,
                        recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                        .addBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorAccent))
                        .addActionIcon(R.drawable.ic_swipe_delete)
                        .create()
                        .decorate();
                super.onChildDraw(c, recyclerView, viewHolder,
                        dX, dY, actionState, isCurrentlyActive);
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                deleteBookmark(viewHolder.getItemId(), viewHolder.getAdapterPosition());
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(mFavoritesBinding.rvFavoritesPostList);

        return mFavoritesBinding.getRoot();
    }

    private void deleteBookmark(long id, int position) {
        viewModel.removeBookmark(id);
//        mBookmarksAdapter.notifyItemRemoved(position);
        Snackbar snackbar = Snackbar
                .make(mFavoritesBinding.llBookmarksLayout, "Deleted " + String.valueOf(position),
                        Snackbar.LENGTH_SHORT);
        snackbar.setAction(R.string.undo, view -> {
            viewModel.setBookmark(id);
        });
        snackbar.setActionTextColor(Color.YELLOW);
        snackbar.show();
    }



    private final PostClickCallback mPostClickCallback = post -> {
        if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
            ((MainActivity) getActivity()).show(post);
        }
    };

    private final BookmarkClickCallback mBookmarkClickCallback = post -> {
        viewModel.updatePost(post);
    };
}