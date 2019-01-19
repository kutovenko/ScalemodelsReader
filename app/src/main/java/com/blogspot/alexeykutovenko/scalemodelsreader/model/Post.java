package com.blogspot.alexeykutovenko.scalemodelsreader.model;

import com.blogspot.alexeykutovenko.scalemodelsreader.db.entity.Author;
import com.blogspot.alexeykutovenko.scalemodelsreader.db.entity.Category;

public interface Post {
    long getId();
    Author getAuthor();
    String getTitle ();
    String getLastUpdate();
    Category getCategory();
    String getOriginalUrl();
    String getThumbnailUrl();
    String getPrintingUrl();
    String[] getImagesUrls();
    String getType();
    String getDate();
    String getStoryid();
    String getDescription();

    String getConvertedDate();
    boolean getIsBookmark();
    void setIsBookmark(boolean isBookmark);

    boolean getIsFeatured();

    String transformDate(String unixDate);
}
