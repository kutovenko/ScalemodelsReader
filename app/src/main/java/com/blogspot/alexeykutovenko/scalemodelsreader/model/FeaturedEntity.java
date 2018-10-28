package com.blogspot.alexeykutovenko.scalemodelsreader.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

//import com.blogspot.alexeykutovenko.scalemodelsreader.BR;
import com.blogspot.alexeykutovenko.scalemodelsreader.db.converters.DataConverters;
import com.blogspot.alexeykutovenko.scalemodelsreader.db.entity.Author;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity(tableName = "featured")
public class FeaturedEntity extends BaseObservable implements Featured, Serializable {
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

//    @ColumnInfo(name = "category") //to plain
//    @SerializedName("category")
//    @TypeConverters({DataConverters.class})
//    private Category category;

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

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    @Override
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    @Override
    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    @Override
    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    @Override
    public String getPrintingUrl() {
        return printingUrl;
    }

    public void setPrintingUrl(String printingUrl) {
        this.printingUrl = printingUrl;
    }

    @Override
    public String[] getImagesUrls() {
        return imagesUrls;
    }

    public void setImagesUrls(String[] imagesUrls) {
        this.imagesUrls = imagesUrls;
    }

    @Override
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String getStoryid() {
        return storyid;
    }

    public void setStoryid(String storyid) {
        this.storyid = storyid;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
//    @Bindable
    public boolean getIsBookmark() {
        return isBookmark;
    }

    @Override
    public void setIsBookmark(boolean isBookmark) {
        this.isBookmark = isBookmark;
//        notifyPropertyChanged(BR.isBookmark);
    }

    @Override
    public boolean getIsRead() {
        return isRead;
    }

    @Override
    public void setIsRead(boolean isRead) {
        this.isRead = isRead;
    }


    public FeaturedEntity() {

    }


    public FeaturedEntity(FeaturedEntity post) {
        this.id = post.getId();
        this.author = post.getAuthor();
        this.title = post.getTitle();
        this.lastUpdate = post.getLastUpdate();
//        this.category = post.getCategory();
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

    }

    public static DiffUtil.ItemCallback<FeaturedEntity> DIFF_CALLBACK = new DiffUtil.ItemCallback<FeaturedEntity>() {
        @Override
        public boolean areItemsTheSame(@NonNull FeaturedEntity oldItem, @NonNull FeaturedEntity newItem) {
            return oldItem.storyid.equals(newItem.storyid);

        }

        @Override
        public boolean areContentsTheSame(@NonNull FeaturedEntity oldItem, @NonNull FeaturedEntity newItem) {
            return oldItem.equals(newItem);
        }
    };
}