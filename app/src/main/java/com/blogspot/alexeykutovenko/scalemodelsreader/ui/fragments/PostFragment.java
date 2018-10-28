package com.blogspot.alexeykutovenko.scalemodelsreader.ui.fragments;


import androidx.lifecycle.ViewModelProviders;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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

import static com.blogspot.alexeykutovenko.scalemodelsreader.utilities.MyAppConctants.KEY_POST_ID;
import static com.blogspot.alexeykutovenko.scalemodelsreader.utilities.MyAppConctants.KEY_POST_TITLE;

/**
 * A simple {@link Fragment} subclass.
 */
public class PostFragment extends Fragment {
    public static String TAG = "post";

    private FragmentPostBinding mBinding;
    private NavController navController;

    public PostFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_post, container, false);
        navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        String title = getArguments().getString(KEY_POST_TITLE);
        getActivity().setTitle(title);

        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        PostViewModel.Factory factory = new PostViewModel.Factory(
                getActivity().getApplication(),
                getArguments().getInt(KEY_POST_ID));

        final PostViewModel model = ViewModelProviders.of(this, factory)
                .get(PostViewModel.class);

        mBinding.setPostViewModel(model);
        mBinding.setCallback(mWebClickCallback);
        subscribeToModel(model);
    }

    private void subscribeToModel(final PostViewModel model) {
        model.getObservablePost().observe(this, model::setPost);
    }

    private final WebClickCallback mWebClickCallback = post -> {
        Bundle bundle = new Bundle(2);
        bundle.putString(MyAppConctants.PRINTING_URL, post.getPrintingUrl());
        bundle.putString(MyAppConctants.ORIGINAL_URL, post.getOriginalUrl());
        navController.navigate(R.id.webviewFragment, bundle);
    };

}