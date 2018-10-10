package com.blogspot.alexeykutovenko.scalemodelsreader.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.blogspot.alexeykutovenko.scalemodelsreader.model.Featured;

import java.util.Date;

@Entity(tableName = "featured")
public class FeaturedEntity implements Featured {
    @PrimaryKey
    private int id;
    private int onlineId;
    private String title;
    private String author;
    private String thumbnailUrl;
    private String imagesUrls;
    private String originalUrl;
    private Date date;
    private int categoryId;
    private String categoryName;
    private Date lastUpdateDate;
    private String description;
    private boolean isFavorite;

    @Override
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int getOnlineId() {
        return onlineId;
    }
    public void setOnlineId(int onlineId) {
        this.onlineId = onlineId;
    }

    @Override
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String getThumbnailUrl() {
        return thumbnailUrl;
    }
    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    @Override
    public String getImagesUrls() {
        return imagesUrls;
    }
    public void setImagesUrls(String imagesUrls) {
        this.imagesUrls = imagesUrls;
    }

    @Override
    public String getOriginalUrl() {
        return originalUrl;
    }
    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    @Override
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public int getCategoryId() {
        return categoryId;
    }
    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public String getCategoryName() {
        return categoryName;
    }
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }
    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    @Override
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean getIsFavorite() {
        return isFavorite;
    }
    public void setFavorite(boolean favorite) {
        this.isFavorite = favorite;
    }


    public FeaturedEntity() {
    }

    public FeaturedEntity(int id, int onlineId, String title, String author, String thumbnailUrl,
                      String imagesUrls, String originalUrl, Date date, int categoryId,
                      String categoryName, Date lastUpdateDate, String description,
                      boolean isFavorite) {
        this.id = id;
        this.onlineId = onlineId;
        this.title = title;
        this.author = author;
        this.thumbnailUrl = thumbnailUrl;
        this.imagesUrls = imagesUrls;
        this.originalUrl = originalUrl;
        this.date = date;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.lastUpdateDate = lastUpdateDate;
        this.description = description;
        this.isFavorite = isFavorite;
    }

    public FeaturedEntity(FeaturedEntity featuredEntity) {
        this.id = featuredEntity.getId();
        this.onlineId = featuredEntity.getOnlineId();
        this.title = featuredEntity.getTitle();
        this.author = featuredEntity.getAuthor();
        this.thumbnailUrl = featuredEntity.getThumbnailUrl();
        this.imagesUrls = featuredEntity.getImagesUrls();
        this.originalUrl = featuredEntity.getOriginalUrl();
        this.date = featuredEntity.getDate();
        this.categoryId = featuredEntity.getCategoryId();
        this.categoryName = featuredEntity.getCategoryName();
        this.lastUpdateDate = featuredEntity.getLastUpdateDate();
        this.description = featuredEntity.getDescription();
        this.isFavorite = featuredEntity.getIsFavorite();
    }
}