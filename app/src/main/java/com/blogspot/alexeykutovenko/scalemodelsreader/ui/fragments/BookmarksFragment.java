package com.blogspot.alexeykutovenko.scalemodelsreader.ui.fragments;

import android.app.SearchManager;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.blogspot.alexeykutovenko.scalemodelsreader.R;
import com.blogspot.alexeykutovenko.scalemodelsreader.databinding.FragmentBookmarksBinding;
import com.blogspot.alexeykutovenko.scalemodelsreader.ui.MainActivity;
import com.blogspot.alexeykutovenko.scalemodelsreader.ui.RecyclerViewSwipeDecorator;
import com.blogspot.alexeykutovenko.scalemodelsreader.ui.adapters.BookmarksAdapter;
import com.blogspot.alexeykutovenko.scalemodelsreader.ui.callbacks.BookmarkClickCallback;
import com.blogspot.alexeykutovenko.scalemodelsreader.ui.callbacks.PostClickCallback;
import com.blogspot.alexeykutovenko.scalemodelsreader.viewmodel.BookmarksListViewModel;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Fragment with the list of saved bookmarks.
 */
public class BookmarksFragment extends Fragment {
    public static String TAG = "favorites";
    private BookmarksAdapter mBookmarksAdapter;
    private FragmentBookmarksBinding mBinding;
    private BookmarksListViewModel viewModel;

    public BookmarksFragment() {
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(BookmarksListViewModel.class);
        subscribeUi(viewModel);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    private void subscribeUi(BookmarksListViewModel viewModel) {
        viewModel.getBookmarks().observe(this, myFavorites -> {
            if (myFavorites != null) {
                mBinding.setIsLoading(false);
//                mBookmarksAdapter.setBookmarksList(myFavorites);
                mBookmarksAdapter.setBookmarksList(myFavorites);

            } else {
                mBinding.setIsLoading(true);
            }
            mBinding.executePendingBindings();
        });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil
                .inflate(inflater, R.layout.fragment_bookmarks, container, false);
        mBookmarksAdapter = new BookmarksAdapter(mPostClickCallback, mBookmarkClickCallback);
        mBinding.rvFavoritesPostList.setAdapter(mBookmarksAdapter);
//        setSwipeToDelete();
        setToolbarWithMenu();

        return mBinding.getRoot();
    }

    private void setToolbarWithMenu() {
        getActivity().setTitle(R.string.title_bookmarks);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.bookmarks, menu);

        MenuItem sortByDateItem = menu.findItem(R.id.miSortByDate);
        sortByDateItem.setOnMenuItemClickListener(item -> {
            mBookmarksAdapter.sortByDate();
            return false;
        });
        MenuItem sortByTitleItem = menu.findItem(R.id.miSortByTitle);
        sortByTitleItem.setOnMenuItemClickListener(item -> {
            mBookmarksAdapter.sortByTitle();
            return false;
        });

        SearchView searchView = (SearchView) menu.findItem(R.id.miSearch)
                .getActionView();
        searchView.setOnQueryTextListener(onQueryTextListener);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getActivity().getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mBookmarksAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                mBookmarksAdapter.getFilter().filter(query);
                return false;
            }
        });
    }

    private SearchView.OnQueryTextListener onQueryTextListener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            viewModel.setFilter(query);
            mBookmarksAdapter.getFilter().filter(query);
            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            viewModel.setFilter(newText);

            mBookmarksAdapter.getFilter().filter(newText);
            return false;
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return id == R.id.miSearch || super.onOptionsItemSelected(item);
    }

    private void setSwipeToDelete() {
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
        itemTouchHelper.attachToRecyclerView(mBinding.rvFavoritesPostList);
    }

    private void deleteBookmark(long id, int position) {
//        viewModel.updatePost(id);
//        mBookmarksAdapter.notifyItemRemoved(position);
        Snackbar snackbar = Snackbar
                .make(mBinding.llBookmarksLayout, "Deleted " + String.valueOf(position),
                        Snackbar.LENGTH_SHORT);
        snackbar.setAction(R.string.undo, view -> {
//            viewModel.setBookmark(id);
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