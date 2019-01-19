package com.blogspot.alexeykutovenko.scalemodelsreader.ui.fragment;


import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.blogspot.alexeykutovenko.scalemodelsreader.R;
import com.blogspot.alexeykutovenko.scalemodelsreader.databinding.FragmentNewsBinding;
import com.blogspot.alexeykutovenko.scalemodelsreader.db.entity.Category;
import com.blogspot.alexeykutovenko.scalemodelsreader.ui.MainActivity;
import com.blogspot.alexeykutovenko.scalemodelsreader.ui.adapter.FeaturedAdapter;
import com.blogspot.alexeykutovenko.scalemodelsreader.ui.adapter.NewsAdapter;
import com.blogspot.alexeykutovenko.scalemodelsreader.ui.callback.PostClickCallback;
import com.blogspot.alexeykutovenko.scalemodelsreader.util.MyAppConctants;
import com.blogspot.alexeykutovenko.scalemodelsreader.viewmodel.NewsListModelFactory;
import com.blogspot.alexeykutovenko.scalemodelsreader.viewmodel.NewsListViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * Main screen with posts list and feature items list. Utilise swipe to update.
 */
public class NewsFragment extends Fragment {
    private FeaturedAdapter featuredAdapter;
    private FragmentNewsBinding binding;
    private NewsListViewModel viewModel;
    private NewsAdapter newsAdapter;
    private Set<Category> categorySet;

    public NewsFragment() {
        this.categorySet = new HashSet<>();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        checkNetworkAvailability();
//        refreshData();
    }

    @SuppressLint("WrongConstant")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_news, container, false);

        newsAdapter = new NewsAdapter(mPostClickCallback);
        LinearLayoutManager newsLayoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false);
        binding.rvPostList.setLayoutManager(newsLayoutManager);
        binding.rvPostList.setAdapter(newsAdapter);
        binding.swipeContainer.setRefreshing(false);

        //If necessary, paged adapter goes here

        featuredAdapter = new FeaturedAdapter(mPostClickCallback);
        LinearLayoutManager featuredManager
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        binding.rvFeaturedList.setLayoutManager(featuredManager);
        binding.rvFeaturedList.setAdapter(featuredAdapter);

        binding.swipeContainer.setOnRefreshListener(() -> new Handler().post(() -> {
            binding.swipeContainer.setRefreshing(true);
            refreshData();
            binding.swipeContainer.setRefreshing(false);
            new Handler().postDelayed(() -> binding.rvPostList.scrollToPosition(0), 300);
        }));
        binding.swipeContainer.setColorSchemeResources(R.color.colorPrimary);

        viewModel = ViewModelProviders.of(this,
                new NewsListModelFactory(Objects.requireNonNull(this.getActivity(), "Activity must not be null")
                        .getApplication()))
                .get(NewsListViewModel.class);

        subscribeUi(viewModel);

        setToolbarWithMenu();

        return binding.getRoot();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.news, menu);
        super.onCreateOptionsMenu(menu, inflater);

        Set<Category> choosedCategory = new TreeSet<Category>() {
        };

        MenuItem firstItem = menu.add(Menu.NONE, 1, 2, R.string.All);
        firstItem.setOnMenuItemClickListener(fItem -> {
            choosedCategory.clear();
            choosedCategory.addAll(viewModel.loadCategories());
            viewModel.setCategory(choosedCategory);
            newsAdapter.notifyDataSetChanged();

            return false;
        });
        firstItem.setIcon(R.drawable.ic_filter_list_black_24dp);

        for (Category category : categorySet) {
            MenuItem categoryItem = menu.add(Menu.NONE, // groupId
                    1, // itemId
                    2, // order
                    category.getName_ru()); // category title
            categoryItem.setOnMenuItemClickListener(item -> {
                choosedCategory.clear();
                choosedCategory.add(category);
                viewModel.setCategory(choosedCategory);
                newsAdapter.notifyDataSetChanged();

                return false;
            });
        }
    }

    private void subscribeUi(NewsListViewModel viewModel) {
        viewModel.getLiveCategories().observe(this, categories -> binding.executePendingBindings());

        viewModel.getNews().observe(this, myPosts -> {
            if (myPosts != null) {
                newsAdapter.setPostList(myPosts);
                binding.setIsLoading(false);
            } else {
                binding.setIsLoading(true);
            }
            binding.executePendingBindings();
        });

        viewModel.getFeatureds().observe(this, myFeaturedEntities -> {
            if (myFeaturedEntities != null) {
                binding.setIsLoading(false);
                featuredAdapter.setFeaturedList(myFeaturedEntities);
            } else {
                binding.setIsLoading(true);
            }
            binding.executePendingBindings();
        });
    }

    private void setToolbarWithMenu() {
        Objects.requireNonNull(getActivity(), "Activity must not be null").setTitle(R.string.title_news);
    }

    private void checkNetworkAvailability() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) Objects.requireNonNull(getActivity(), "Activity must not be null")
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo == null || !activeNetworkInfo.isConnected()) {
            Snackbar snackbar = Snackbar
                    .make(binding.getRoot(), getString(R.string.no_internet_connection), Snackbar.LENGTH_LONG);
            View sbView = snackbar.getView();
            sbView.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorAlert));
            snackbar.show();
        }
    }

    private void refreshData() {
        int numberOfNews = viewModel.refreshPosts();
        viewModel.refreshFeatured();
        categorySet = viewModel.loadCategories();
        viewModel.setCategory(categorySet);
        if (numberOfNews > 0) {
            Toast.makeText(getActivity(), numberOfNews
                    + getResources().getQuantityString(R.plurals.news, numberOfNews)
                    + MyAppConctants.SCALEMODELSRU, Toast.LENGTH_SHORT).show();
        }
    }

    private final PostClickCallback mPostClickCallback = post -> {
        if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
            ((MainActivity) Objects.requireNonNull(getActivity(), "Activity must not be null")).show(post);
        }
    };
}