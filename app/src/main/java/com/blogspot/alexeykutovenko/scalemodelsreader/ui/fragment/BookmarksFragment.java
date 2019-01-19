package com.blogspot.alexeykutovenko.scalemodelsreader.ui.fragment;

import android.app.SearchManager;
import android.content.Context;
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
import com.blogspot.alexeykutovenko.scalemodelsreader.ui.adapter.BookmarksAdapter;
import com.blogspot.alexeykutovenko.scalemodelsreader.ui.callback.BookmarkClickCallback;
import com.blogspot.alexeykutovenko.scalemodelsreader.ui.callback.PostClickCallback;
import com.blogspot.alexeykutovenko.scalemodelsreader.viewmodel.BookmarksListViewModel;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProviders;

/**
 * Fragment with the list of saved bookmarks.
 */
public class BookmarksFragment extends Fragment {
    private BookmarksAdapter bookmarksAdapter;
    private FragmentBookmarksBinding binding;
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
                binding.setIsLoading(false);
                bookmarksAdapter.setBookmarks(myFavorites);

            } else {
                binding.setIsLoading(true);
            }
            binding.executePendingBindings();
        });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil
                .inflate(inflater, R.layout.fragment_bookmarks, container, false);
        bookmarksAdapter = new BookmarksAdapter(mPostClickCallback, mBookmarkClickCallback);
        binding.rvFavoritesPostList.setAdapter(bookmarksAdapter);

        setToolbarWithMenu();

        return binding.getRoot();
    }

    private void setToolbarWithMenu() {
        Objects.requireNonNull(getActivity()).setTitle(R.string.title_bookmarks);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.bookmarks, menu);

        MenuItem sortByDateItem = menu.findItem(R.id.miSortByDate);
        sortByDateItem.setOnMenuItemClickListener(item -> {
            bookmarksAdapter.sortByDate();
            return false;
        });
        MenuItem sortByTitleItem = menu.findItem(R.id.miSortByTitle);
        sortByTitleItem.setOnMenuItemClickListener(item -> {
            bookmarksAdapter.sortByTitle();
            return false;
        });

        SearchView searchView = (SearchView) menu.findItem(R.id.miSearch)
                .getActionView();
        searchView.setOnQueryTextListener(onQueryTextListener);
        SearchManager searchManager = (SearchManager) Objects.requireNonNull(getActivity()).getSystemService(Context.SEARCH_SERVICE);
        assert searchManager != null;
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getActivity().getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                bookmarksAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                bookmarksAdapter.getFilter().filter(query);
                return false;
            }
        });
    }

    private SearchView.OnQueryTextListener onQueryTextListener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            viewModel.setFilter(query);
            bookmarksAdapter.getFilter().filter(query);
            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            viewModel.setFilter(newText);

            bookmarksAdapter.getFilter().filter(newText);
            return false;
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return id == R.id.miSearch || super.onOptionsItemSelected(item);
    }


    private final PostClickCallback mPostClickCallback = post -> {
        if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
            ((MainActivity) Objects.requireNonNull(getActivity())).show(post);
        }
    };

    private final BookmarkClickCallback mBookmarkClickCallback = post -> viewModel.updatePost(post);
}