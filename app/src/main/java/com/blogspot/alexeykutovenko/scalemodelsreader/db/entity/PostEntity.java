package com.blogspot.alexeykutovenko.scalemodelsreader.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;

import com.blogspot.alexeykutovenko.scalemodelsreader.BR;
import com.blogspot.alexeykutovenko.scalemodelsreader.db.converters.DataConverters;
import com.blogspot.alexeykutovenko.scalemodelsreader.model.Post;
import com.blogspot.alexeykutovenko.scalemodelsreader.network.Author;
import com.blogspot.alexeykutovenko.scalemodelsreader.network.Category;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Main Entity class for all the posts from Scalemodels.
 * Two sets of annotations used: one for Room and another for Retfofit.
 * Room annotation
 * Gson annotation
 */
@Entity (tableName = "posts")
public class PostEntity extends BaseObservable implements Post, Serializable {
    @PrimaryKey(autoGenerate = true)           // Room annotation
    private int id;

    @ColumnInfo(name = "author")
    @SerializedName("author") //to plain
    @TypeConverters({DataConverters.class})
    private Author author;

    @ColumnInfo(name = "title_ru")
    @SerializedName("title_ru")
    private String title;

    @ColumnInfo(name = "last_update")
    @SerializedName("last_update")
    private String lastUpdate;

    @ColumnInfo(name = "category") //to plain
    @SerializedName("category")
    @TypeConverters({DataConverters.class})
    private Category category;

    @ColumnInfo(name = "original_url")
    @SerializedName("original_url")
    private String originalUrl;

    @ColumnInfo(name = "thumbnail")
    @SerializedName("thumbnail")
    private String thumbnailUrl;

    @ColumnInfo(name = "printing_url")
    @SerializedName("printing_url")
    private String printingUrl;

    @ColumnInfo(name = "images")
    @SerializedName("images") //to plain
    @TypeConverters({DataConverters.class})
    private String[] imagesUrls;

    @ColumnInfo(name = "type")
    @SerializedName("type")
    private String type;

    @ColumnInfo(name = "date")
    @SerializedName("date")
    @TypeConverters({DataConverters.class})//?
    private String date;

    @ColumnInfo(name = "storyid")
    @SerializedName("storyid")
    private String storyid;

    @ColumnInfo(name = "description_ru")
    @SerializedName("description_ru")
    private String description;

    @ColumnInfo(name = "isBookmark")
    private boolean isBookmark;
    @ColumnInfo(name = "isRead")
    private boolean isRead;

//    private int onlineId;
//    private String title;
//    private String author;
//    private String thumbnailUrl;
//    private String imagesUrls;
//    private String originalUrl;
//    private Date date;
//    private int categoryId;
//    private String categoryName;
//    private Date lastUpdateDate;
//    private String description;
//    private boolean isFavorite;
//    private boolean isRead;

    @Override
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    @Override
    public Author getAuthor ()
    {
        return author;
    }
    public void setAuthor (Author author)
    {
        this.author = author;
    }

    @Override
    public String getTitle ()
    {
        return title;
    }
    public void setTitle (String title)
    {
        this.title = title;
    }

    @Override
    public String getLastUpdate ()
    {
        return lastUpdate;
    }
    public void setLastUpdate (String lastUpdate)
    {
        this.lastUpdate = lastUpdate;
    }

    @Override
    public Category getCategory ()
    {
        return category;
    }
    public void setCategory (Category category)
    {
        this.category = category;
    }

    @Override
    public String getOriginalUrl ()
    {
        return originalUrl;
    }
    public void setOriginalUrl (String originalUrl)
    {
        this.originalUrl = originalUrl;
    }

    @Override
    public String getThumbnailUrl()
    {
        return thumbnailUrl;
    }
    public void setThumbnailUrl(String thumbnailUrl)
    {
        this.thumbnailUrl = thumbnailUrl;
    }

    @Override
    public String getPrintingUrl ()
    {
        return printingUrl;
    }
    public void setPrintingUrl (String printingUrl)
    {
        this.printingUrl = printingUrl;
    }

    @Override
    public String[] getImagesUrls ()
    {
        return imagesUrls;
    }
    public void setImagesUrls (String[] imagesUrls)
    {
        this.imagesUrls = imagesUrls;
    }

    @Override
    public String getType ()
    {
        return type;
    }
    public void setType (String type)
    {
        this.type = type;
    }

    @Override
    public String getDate ()
    {
        return date;
    }
    public void setDate (String date)
    {
        this.date = date;
    }

    @Override
    public String getStoryid ()
    {
        return storyid;
    }
    public void setStoryid (String storyid)
    {
        this.storyid = storyid;
    }

    @Override
    public String getDescription ()
    {
        return description;
    }
    public void setDescription (String description)
    {
        this.description = description;
    }

//    @Override
//    public String toString()
//    {
//        return "ClassPojo [author = "+author+", title_ru = "+title_ru+", last_update = "+last_update+", category = "+category+", original_url = "+original_url+", thumbnail = "+thumbnail+", printing_url = "+printing_url+", images = "+images+", type = "+type+", date = "+date+", storyid = "+storyid+", description_ru = "+description_ru+"]";
//    }


//}
//
//    @Override
//    public int getOnlineId() {
//        return onlineId;
//    }
//    public void setOnlineId(int onlineId) {
//        this.onlineId = onlineId;
//    }
//
//    @Override
//    public String getTitle() {
//        return title;
//    }
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    @Override
//    public String getAuthor() {
//        return author;
//    }
//    public void setAuthor(String author) {
//        this.author = author;
//    }
//
//    @Override
//    public String getThumbnailUrl() {
//        return thumbnailUrl;
//    }
//    public void setThumbnailUrl(String thumbnailUrl) {
//        this.thumbnailUrl = thumbnailUrl;
//    }
//
//    @Override
//    public String getImagesUrls() {
//        return imagesUrls;
//    }
//    public void setImagesUrls(String imagesUrls) {
//        this.imagesUrls = imagesUrls;
//    }
//
//    @Override
//    public String getOriginalUrl() {
//        return originalUrl;
//    }
//    public void setOriginalUrl(String originalUrl) {
//        this.originalUrl = originalUrl;
//    }
//
//    @Override
//    public Date getDate() {
//        return date;
//    }
//    public void setDate(Date date) {
//        this.date = date;
//    }
//
//    @Override
//    public int getCategoryId() {
//        return categoryId;
//    }
//    public void setCategoryId(int categoryId) {
//        this.categoryId = categoryId;
//    }
//
//    @Override
//    public String getCategoryName() {
//        return categoryName;
//    }
//    public void setCategoryName(String categoryName) {
//        this.categoryName = categoryName;
//    }
//
//    @Override
//    public Date getLastUpdateDate() {
//        return lastUpdateDate;
//    }
//    public void setLastUpdateDate(Date lastUpdateDate) {
//        this.lastUpdateDate = lastUpdateDate;
//    }
//
//    @Override
//    public String getDescription() {
//        return description;
//    }
//    public void setDescription(String description) {
//        this.description = description;
//    }

    @Override
    @Bindable
    public boolean getIsBookmark() {
        return isBookmark;
    }
    @Override
    public void setIsBookmark(boolean isBookmark) {
        this.isBookmark = isBookmark;
        notifyPropertyChanged(BR.isBookmark);
    }

    @Override
    public boolean getIsRead() {
        return isRead;
    }
    @Override
    public void setIsRead(boolean isRead) {
        this.isRead = isRead;
    }

    public PostEntity() {
    }

//    public PostEntity(int id, int onlineId, String title, String author, String thumbnailUrl,
//                      String imagesUrls, String originalUrl, Date date, int categoryId,
//                      String categoryName, Date lastUpdateDate, String description,
//                      boolean isBookmark) {
//        this.id = id;
//        this.onlineId = onlineId;
//        this.title = title;
//        this.author = author;
//        this.thumbnailUrl = thumbnailUrl;
//        this.imagesUrls = imagesUrls;
//        this.originalUrl = originalUrl;
//        this.date = date;
//        this.categoryId = categoryId;
//        this.categoryName = categoryName;
//        this.lastUpdateDate = lastUpdateDate;
//        this.description = description;
//        this.isBookmark = isBookmark;
//    }

    public PostEntity(Post post) {
        this.id = post.getId();
        this.author = post.getAuthor();
        this.title = post.getTitle();
        this.lastUpdate = post.getLastUpdate();
        this.category = post.getCategory();
        this.originalUrl = post.getOriginalUrl();
        this.thumbnailUrl = post.getThumbnailUrl();
        this.printingUrl = post.getPrintingUrl();
        this.imagesUrls = post.getImagesUrls();
        this.type = post.getType();
        this.date = post.getDate();
        this.storyid = post.getStoryid();
        this.description = post.getDescription();
        this.isBookmark = post.getIsBookmark();
        this.isRead = post.getIsRead();

//
//        this.id = post.getId();
//        this.onlineId = post.getOnlineId();
//        this.title = post.getTitle();
//        this.author = post.getAuthor();
//        this.thumbnailUrl = post.getThumbnailUrl();
//        this.imagesUrls = post.getImagesUrls();
//        this.originalUrl = post.getOriginalUrl();
//        this.date = post.getDate();
//        this.categoryId = post.getCategoryId();
//        this.categoryName = post.getCategoryName();
//        this.lastUpdateDate = post.getLastUpdateDate();
//        this.description = post.getDescription();
//        this.isBookmark = post.getIsBookmark();
    }

    public static DiffUtil.ItemCallback<PostEntity> DIFF_CALLBACK = new DiffUtil.ItemCallback<PostEntity>() {
        @Override
        public boolean areItemsTheSame(@NonNull PostEntity oldItem, @NonNull PostEntity newItem) {
//            return oldItem.id == newItem.id;
            return oldItem.storyid == newItem.storyid;

        }

        @Override
        public boolean areContentsTheSame(@NonNull PostEntity oldItem, @NonNull PostEntity newItem) {
            return oldItem.equals(newItem);
        }
    };
}
