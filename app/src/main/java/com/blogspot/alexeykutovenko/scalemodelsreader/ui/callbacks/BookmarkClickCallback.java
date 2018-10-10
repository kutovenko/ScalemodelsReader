package com.blogspot.alexeykutovenko.scalemodelsreader.ui.callbacks;

import com.blogspot.alexeykutovenko.scalemodelsreader.db.entity.PostEntity;
import com.blogspot.alexeykutovenko.scalemodelsreader.model.Post;

public interface BookmarkClickCallback {
    void onClick(Post post);
}
