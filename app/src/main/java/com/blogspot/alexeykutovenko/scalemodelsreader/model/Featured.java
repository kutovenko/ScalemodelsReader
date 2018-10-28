package com.blogspot.alexeykutovenko.scalemodelsreader.model;

import com.blogspot.alexeykutovenko.scalemodelsreader.db.entity.Author;

public interface Featured {
    int getId();

    Author getAuthor();

    String getTitle();

    String getLastUpdate();

    //    Category getCategory();
    String getOriginalUrl();

    String getThumbnailUrl();

    String getPrintingUrl();

    String[] getImagesUrls();

    String getType();

    String getDate();

    String getStoryid();
    String getDescription();

    boolean getIsBookmark();

    void setIsBookmark(boolean isBookmark);

    boolean getIsRead();

    void setIsRead(boolean isRead);
}

