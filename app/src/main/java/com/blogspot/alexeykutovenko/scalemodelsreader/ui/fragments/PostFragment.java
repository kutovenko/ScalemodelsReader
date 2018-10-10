package com.blogspot.alexeykutovenko.scalemodelsreader.ui.fragments;


import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blogspot.alexeykutovenko.scalemodelsreader.R;
import com.blogspot.alexeykutovenko.scalemodelsreader.databinding.FragmentPostBinding;
import com.blogspot.alexeykutovenko.scalemodelsreader.ui.callbacks.WebClickCallback;
import com.blogspot.alexeykutovenko.scalemodelsreader.utilities.MyAppConctants;
import com.blogspot.alexeykutovenko.scalemodelsreader.viewmodel.PostViewModel;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;

/**
 * A simple {@link Fragment} subclass.
 */
public class PostFragment extends Fragment {
    public static String TAG = "post";
    private static final String KEY_POST_ID = "id";
    private FragmentPostBinding mBinding;
    private NavController navController;

    public PostFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_post, container, false);
        navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);

        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        PostViewModel.Factory factory = new PostViewModel.Factory(
                getActivity().getApplication(), getArguments().getInt(KEY_POST_ID));

        final PostViewModel model = ViewModelProviders.of(this, factory)
                .get(PostViewModel.class);

        mBinding.setPostViewModel(model);
        mBinding.setCallback(mWebClickCallback);
        subscribeToModel(model);
    }

    private void subscribeToModel(final PostViewModel model) {
        model.getObservablePost().observe(this, model::setPost);
    }


//    /** Creates post fragment for specific ID */
//    public static PostFragment forPost(int postId) {
//        PostFragment fragment = new PostFragment();
//        Bundle args = new Bundle();
//        args.putInt(KEY_POST_ID, postId);
//        fragment.setArguments(args);
//        return fragment;
//    }

    private final WebClickCallback mWebClickCallback = post -> {

        Bundle bundle = new Bundle(1);
        bundle.putString(MyAppConctants.URL, post.getPrintingUrl());
        navController.navigate(R.id.webviewFragment, bundle);
    };

}