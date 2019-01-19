package com.blogspot.alexeykutovenko.scalemodelsreader.model;

import com.blogspot.alexeykutovenko.scalemodelsreader.db.converter.DataConverters;
import com.blogspot.alexeykutovenko.scalemodelsreader.db.entity.Author;
import com.blogspot.alexeykutovenko.scalemodelsreader.db.entity.Category;
import com.blogspot.alexeykutovenko.scalemodelsreader.util.StringUtils;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;
import androidx.recyclerview.widget.DiffUtil;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

/**
 * Main Entity class for all the posts from Scalemodels.
 * Two sets of annotations used: one for Room and another for Retfofit:
 * Room annotation
 * Gson annotation
 */
@Entity (tableName = "posts")
public class PostEntity extends BaseObservable implements Post, Serializable {
    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "author")
    @SerializedName("author")
    @TypeConverters({DataConverters.class})
    private Author author;

    @ColumnInfo(name = "title_ru")
    @SerializedName("title_ru")
    private String title;

    @ColumnInfo(name = "last_update")
    @SerializedName("last_update")
    private String lastUpdate;

    @ColumnInfo(name = "category")
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
    @SerializedName("images")
    @TypeConverters({DataConverters.class})
    private String[] imagesUrls;

    @ColumnInfo(name = "type")
    @SerializedName("type")
    private String type;

    @ColumnInfo(name = "date")
    @SerializedName("date")
    @TypeConverters({DataConverters.class})
    private String date;

    @ColumnInfo(name = "storyid")
    @SerializedName("storyid")
    private String storyid;

    @ColumnInfo(name = "description_ru")
    @SerializedName("description_ru")
    private String description;

    @ColumnInfo(name = "isBookmark")
    private boolean isBookmark;

    @ColumnInfo(name = "isFeatured")
    private boolean isFeatured;

    @ColumnInfo(name = "convertedDate")
    private String convertedDate;

    @Override
    public long getId() {
        return id;
    }

    public void setId(long id) {
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
        this.title = StringUtils.unescapeHtml3(title);
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
        this.description = StringUtils.unescapeHtml3(description);
    }

    @Override
    public boolean getIsFeatured() {
        return isFeatured;
    }

    public void setIsFeatured(boolean isFeatured) {
        this.isFeatured = isFeatured;
    }

    @Override
    public String getConvertedDate() {
        return convertedDate;
    }

    public void setConvertedDate(String convertedDate) {
        this.convertedDate = convertedDate;
    }

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
    public String transformDate(String unixDate) {
        DateFormat df = SimpleDateFormat.getDateInstance();
        Calendar calendar = Calendar.getInstance();
        long longDate = Long.parseLong(unixDate) * 1000L;
        calendar.setTimeInMillis(longDate);
        return df.format(calendar.getTime());
    }

    public PostEntity() {
    }

    public PostEntity(Post post) {
        this.id = post.getId();
        this.author = post.getAuthor();
        this.title = StringUtils.unescapeHtml3(post.getTitle());
        this.lastUpdate = post.getLastUpdate();
        this.category = post.getCategory();
        this.originalUrl = post.getOriginalUrl();
        this.thumbnailUrl = post.getThumbnailUrl();
        this.printingUrl = post.getPrintingUrl();
        this.imagesUrls = post.getImagesUrls();
        this.type = post.getType();
        this.date = post.getDate();
        this.storyid = post.getStoryid();
        this.description = StringUtils.unescapeHtml3(post.getDescription());
        this.isBookmark = post.getIsBookmark();
        this.isFeatured = post.getIsFeatured();
    }

    public static DiffUtil.ItemCallback<PostEntity> DIFF_CALLBACK = new DiffUtil.ItemCallback<PostEntity>() {
        @Override
        public boolean areItemsTheSame(@NonNull PostEntity oldItem, @NonNull PostEntity newItem) {
            return oldItem.storyid.equals(newItem.storyid)
                    && oldItem.isFeatured == newItem.isFeatured;
        }

        @Override
        public boolean areContentsTheSame(@NonNull PostEntity oldItem, @NonNull PostEntity newItem) {
            return oldItem.equals(newItem);
        }
    };
}
