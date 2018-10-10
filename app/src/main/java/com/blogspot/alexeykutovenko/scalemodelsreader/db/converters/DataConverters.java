package com.blogspot.alexeykutovenko.scalemodelsreader.db.converters;

import android.arch.persistence.room.TypeConverter;

import com.blogspot.alexeykutovenko.scalemodelsreader.network.Author;
import com.blogspot.alexeykutovenko.scalemodelsreader.network.Category;
import com.blogspot.alexeykutovenko.scalemodelsreader.utilities.MyAppConctants;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class DataConverters {
    @TypeConverter
    public static Date toDate(Long timestamp) {
        return timestamp == null ? null : new Date(timestamp);
    }
    @TypeConverter
    public static Long toTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

    @TypeConverter
    public static String fromAuthor(Author author) {
        return author.toString();
    }
    @TypeConverter
    public static Author toAuthor(String data) {
        List<String> authorList = Arrays.asList(data.split(MyAppConctants.STRING_SEPARATOR));
        return new Author(authorList.get(0), authorList.get(1));
    }

    @TypeConverter
    public static String fromCategory(Category category){
        return category.toString();
    }
    @TypeConverter
    public static Category toCategory(String data){
        List<String> categoryList = Arrays.asList(data.split(MyAppConctants.STRING_SEPARATOR));
        return new Category(categoryList.get(0), categoryList.get(1));
    }

    @TypeConverter
    public static String fromImagesUrls(String[] array){
        StringBuilder str = new StringBuilder();
        for (int i = 0;i<array.length; i++) {
            str.append(array[i]);
            // Do not append comma at the end of last element
            if(i<array.length-1){
                str.append(MyAppConctants.STRING_SEPARATOR);
            }
        }
        return str.toString();
    }
    @TypeConverter
    public static String[] toImagesUrls(String str){
        return str.split(MyAppConctants.STRING_SEPARATOR);
    }
}
