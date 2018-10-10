package com.blogspot.alexeykutovenko.scalemodelsreader.model;

import com.blogspot.alexeykutovenko.scalemodelsreader.network.Author;
import com.blogspot.alexeykutovenko.scalemodelsreader.network.Category;

public interface Post {
    int getId();
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
    boolean getIsBookmark();
    void setIsBookmark(boolean isBookmark);
    boolean getIsRead();
    void setIsRead(boolean isRead);
//    int getId();
//    int getOnlineId();
//    String getTitle();
//    String getAuthor();
//    String getThumbnailUrl();
//    String getImagesUrls();
//    String getOriginalUrl();
//    Date getDate();
//    int getCategoryId();
//    String getCategoryName();
//    String getDescription();
//    Date getLastUpdateDate();
//    boolean getIsBookmark();
//    void setIsBookmark(boolean favorite);
}
