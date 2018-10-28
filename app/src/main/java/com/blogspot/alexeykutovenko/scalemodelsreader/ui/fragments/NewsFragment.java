package com.blogspot.alexeykutovenko.scalemodelsreader.ui.fragments;


import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProviders;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blogspot.alexeykutovenko.scalemodelsreader.R;
import com.blogspot.alexeykutovenko.scalemodelsreader.databinding.FragmentNewsBinding;
import com.blogspot.alexeykutovenko.scalemodelsreader.ui.MainActivity;
import com.blogspot.alexeykutovenko.scalemodelsreader.ui.adapters.FeaturedAdapter;
import com.blogspot.alexeykutovenko.scalemodelsreader.ui.adapters.NewsAdapter;
import com.blogspot.alexeykutovenko.scalemodelsreader.ui.callbacks.BookmarkClickCallback;
import com.blogspot.alexeykutovenko.scalemodelsreader.ui.callbacks.PostClickCallback;
import com.blogspot.alexeykutovenko.scalemodelsreader.viewmodel.NewsListViewModel;

/**
 * Main screen with posts list and feature items list. Utilise swipe to update.
 */
public class NewsFragment extends Fragment {
    public static String TAG = "news";
    private FeaturedAdapter mFeaturedAdapter;
    private FragmentNewsBinding mBinding;
    NewsListViewModel viewModel;
    NewsAdapter mNewsAdapter;

    public NewsFragment() {
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewModel.refreshPosts();

        viewModel.refreshFeatured();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_news, container, false);
        mNewsAdapter = new NewsAdapter(mPostClickCallback, mBookmarkClickCallback);
        mBinding.rvPostList.setAdapter(mNewsAdapter);

        viewModel = ViewModelProviders.of(this).get(NewsListViewModel.class);
        subscribeUi(viewModel);


        /**
         * PagedAdapter
         * todo return to position
         *
         */
//        PagedList.Config config = new PagedList.Config.Builder()
//                .setEnablePlaceholders(true)
//                .setPageSize(10)
//                .build();
//        DataSource.Factory dataSource = viewModel.getRepository().getPagedPostsFactory();
//
//        LiveData<PagedList<PostEntity>> pagedListLiveData =
//                new LivePagedListBuilder<>(dataSource, config)
//                        .build();
//        NewsPagedAdapter pagedAdapter = new NewsPagedAdapter(PostEntity.DIFF_CALLBACK, mPostClickCallback,
//                mBookmarkClickCallback);
//
//        pagedListLiveData.observe(this, pagedAdapter::submitList);
//
//        mBinding.rvPostList.setAdapter(pagedAdapter);


        mFeaturedAdapter = new FeaturedAdapter(mPostClickCallback);
        LinearLayoutManager featuredManager
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mBinding.rvFeaturedList.setLayoutManager(featuredManager);
        mBinding.rvFeaturedList.setAdapter(mFeaturedAdapter);

        mBinding.swipeContainer.setOnRefreshListener(() -> {
            new Handler().post(() -> {
                viewModel.refreshPosts();
                mBinding.swipeContainer.setRefreshing(false);

            });
        });

        mBinding.swipeContainer.setColorSchemeResources(R.color.colorPrimary);

        setToolbarWithMenu();

        return mBinding.getRoot();
    }

    private void subscribeUi(NewsListViewModel viewModel) {
        viewModel.getPosts().observe(this, myPosts -> {
            if (myPosts != null) {
                mBinding.setIsLoading(false);
                mNewsAdapter.setPostList(myPosts);
            } else {
                mBinding.setIsLoading(true);
            }
            mBinding.executePendingBindings();
        });

        viewModel.getFeaturedEntities().observe(this, myFeaturedEntities -> {
            if (myFeaturedEntities != null) {
                mBinding.setIsLoading(false);
                mFeaturedAdapter.setFeaturedList(myFeaturedEntities);
            } else {
                mBinding.setIsLoading(true);
            }
            mBinding.executePendingBindings();
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.news, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void setToolbarWithMenu() {
        getActivity().setTitle(R.string.title_news);
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