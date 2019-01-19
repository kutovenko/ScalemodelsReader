package com.blogspot.alexeykutovenko.scalemodelsreader.ui;

import android.content.Context;

import androidx.databinding.BindingAdapter;
import android.view.View;
import android.widget.ImageView;

import com.blogspot.alexeykutovenko.scalemodelsreader.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

public class BindingAdapters {

    /**
     * Hides empty recycler view and showes underlying textview.
     * Used in bindings for news and bookmarks.
     *
     * @param view view to control.
     * @param show true for show, falce for hide.
     */
    @BindingAdapter("visibleGone")
    public static void showHide(View view, boolean show) {
        view.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    /**
     * Connects Glide library and bindings.
     * @param imageView view for the image.
     * @param url url of the online image.
     */
    @BindingAdapter("thumbnailUrl")
    public static void setImageUrl(ImageView imageView, String url) {
        Context context = imageView.getContext();
        Glide.with(context)
                .load(url)
                .apply(new RequestOptions().placeholder(R.drawable.scalemodels_logo_grey)
                        .error(R.drawable.scalemodels_logo_grey)
                        .transforms(new CenterCrop(), new RoundedCorners(8)))
                .into(imageView);
    }

    @BindingAdapter("fullImageUrl")
    public static void setFullImageUrl(ImageView imageView, String url) {
        Context context = imageView.getContext();
        Glide.with(context)
                .load(url)
                .apply(new RequestOptions().placeholder(R.drawable.scalemodels_logo_grey)
                        .error(R.drawable.scalemodels_logo_grey))
                .into(imageView);
    }
}