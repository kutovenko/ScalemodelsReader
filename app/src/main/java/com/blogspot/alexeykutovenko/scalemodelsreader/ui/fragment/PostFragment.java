package com.blogspot.alexeykutovenko.scalemodelsreader.ui.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blogspot.alexeykutovenko.scalemodelsreader.R;
import com.blogspot.alexeykutovenko.scalemodelsreader.databinding.FragmentPostBinding;
import com.blogspot.alexeykutovenko.scalemodelsreader.ui.callback.BookmarkClickCallback;
import com.blogspot.alexeykutovenko.scalemodelsreader.ui.callback.WebClickCallback;
import com.blogspot.alexeykutovenko.scalemodelsreader.util.MyAppConctants;
import com.blogspot.alexeykutovenko.scalemodelsreader.viewmodel.PostViewModel;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import static com.blogspot.alexeykutovenko.scalemodelsreader.util.MyAppConctants.KEY_POST_ID;

/**
 * Class for Post (News and Featureds)
 */
public class PostFragment extends Fragment {
    private FragmentPostBinding binding;
    private NavController navController;
    private PostViewModel viewModel;

    public PostFragment() {
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(false);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_post, container, false);
        navController = Navigation.findNavController(Objects.requireNonNull(getActivity()),
                R.id.nav_host_fragment);

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        assert getArguments() != null;
        PostViewModel.Factory factory = new PostViewModel.Factory(
                Objects.requireNonNull(getActivity()).getApplication(),
                getArguments().getLong(KEY_POST_ID));

        viewModel = ViewModelProviders.of(this, factory)
                .get(PostViewModel.class);

        viewModel.getObservablePost().observe(this, viewModel::setPost);
        binding.setPostViewModel(viewModel);
        binding.setWebCallback(mWebClickCallback);
        binding.setBookmarkCallback(mBookmarkClickCallback);
    }

    private final BookmarkClickCallback mBookmarkClickCallback = post -> viewModel.updatePost(post);

    private final WebClickCallback mWebClickCallback = post -> {
        Bundle bundle = new Bundle(2);
        bundle.putString(MyAppConctants.PRINTING_URL, post.getPrintingUrl());
        bundle.putString(MyAppConctants.ORIGINAL_URL, post.getOriginalUrl());
        navController.navigate(R.id.webviewFragment, bundle);
    };
}