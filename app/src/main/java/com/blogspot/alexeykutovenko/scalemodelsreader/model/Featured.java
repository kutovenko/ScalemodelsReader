package com.blogspot.alexeykutovenko.scalemodelsreader.model;

import java.util.Date;

public interface Featured {
    int getId();
    int getOnlineId();
    String getTitle();
    String getAuthor();
    String getThumbnailUrl();
    String getImagesUrls();
    String getOriginalUrl();
    Date getDate();
    int getCategoryId();
    String getCategoryName();
    String getDescription();
    Date getLastUpdateDate();
    boolean getIsFavorite();
}

