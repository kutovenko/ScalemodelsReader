package com.blogspot.alexeykutovenko.scalemodelsreader.ui;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.view.View;
import android.widget.ImageView;

import com.blogspot.alexeykutovenko.scalemodelsreader.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class BindingAdapters {
    @BindingAdapter("visibleGone")
    public static void showHide(View view, boolean show) {
        view.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @BindingAdapter("thumbnailUrl")
    public static void setImageUrl(ImageView imageView, String url) {
        Context context = imageView.getContext();
        // TODO: 30.09.2018 change placeholder image
        Glide.with(context)
                .load(url)
                .apply(new RequestOptions().placeholder(R.drawable.abc_btn_check_material)
                        .error(R.drawable.abc_btn_check_material))
                .into(imageView);
    }
}
