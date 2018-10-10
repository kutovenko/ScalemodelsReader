package com.blogspot.alexeykutovenko.scalemodelsreader.ui.fragments;


import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blogspot.alexeykutovenko.scalemodelsreader.R;
import com.blogspot.alexeykutovenko.scalemodelsreader.databinding.FragmentNewsBinding;
import com.blogspot.alexeykutovenko.scalemodelsreader.ui.MainActivity;
import com.blogspot.alexeykutovenko.scalemodelsreader.ui.adapters.FeaturedAdapter;
import com.blogspot.alexeykutovenko.scalemodelsreader.ui.adapters.NewsAdapter;
import com.blogspot.alexeykutovenko.scalemodelsreader.ui.callbacks.BookmarkClickCallback;
import com.blogspot.alexeykutovenko.scalemodelsreader.ui.callbacks.FeaturedClickCallback;
import com.blogspot.alexeykutovenko.scalemodelsreader.ui.callbacks.PostClickCallback;
import com.blogspot.alexeykutovenko.scalemodelsreader.viewmodel.PostListViewModel;

/**
 * Main screen with posts list and feature items list. Utilise swipe to update.
 */
public class NewsFragment extends Fragment {
    public static String TAG = "news";
    private FeaturedAdapter mFeaturedAdapter;
    private FragmentNewsBinding mBinding;
    PostListViewModel viewModel;
    NewsAdapter mNewsAdapter;

    public NewsFragment() {
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle("News");

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_news, container, false);
        mNewsAdapter = new NewsAdapter(mPostClickCallback, mBookmarkClickCallback);
        mBinding.rvPostList.setAdapter(mNewsAdapter);

        viewModel = ViewModelProviders.of(this).get(PostListViewModel.class);
        subscribeUi(viewModel);
        viewModel.refreshPosts();
        viewModel.refreshFeatured();

        /**
         * PagedAdapter
         */
//        PagedList.Config config = new PagedList.Config.Builder()
//                .setEnablePlaceholders(true)
//                .setPageSize(10)
//                .build();
//        DataSource.Factory dataSource = viewModel.getRepository().getPagedPostsFactory();
//
//        //to viewmodel
//        LiveData<PagedList<PostEntity>> pagedListLiveData =
//                new LivePagedListBuilder<>(dataSource, config)
////                        .setInitialLoadKey(5)
//                        .build();
//        NewsPagedAdapter pagedAdapter = new NewsPagedAdapter(PostEntity.DIFF_CALLBACK, mPostClickCallback,
//                mBookmarkClickCallback);
//
//        pagedListLiveData.observe(this, pagedAdapter::submitList);
//
//        mBinding.rvPostList.setAdapter(pagedAdapter);


        mFeaturedAdapter = new FeaturedAdapter(mFeaturedClickCallback);
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
        mBinding.swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        return mBinding.getRoot();
    }

    private void subscribeUi(PostListViewModel viewModel) {
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

    private final PostClickCallback mPostClickCallback = post -> {
        if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
            ((MainActivity) getActivity()).show(post);
        }
    };

    private final FeaturedClickCallback mFeaturedClickCallback = featured -> {
        if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
            ((MainActivity) getActivity()).show(featured);
        }
    };

    private final BookmarkClickCallback mBookmarkClickCallback = post -> {
        viewModel.updatePost(post);
    };
}