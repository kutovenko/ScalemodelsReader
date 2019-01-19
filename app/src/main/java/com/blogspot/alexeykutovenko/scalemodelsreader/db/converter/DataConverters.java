package com.blogspot.alexeykutovenko.scalemodelsreader.db.converter;

import androidx.room.TypeConverter;

import com.blogspot.alexeykutovenko.scalemodelsreader.db.entity.Author;
import com.blogspot.alexeykutovenko.scalemodelsreader.db.entity.Category;
import com.blogspot.alexeykutovenko.scalemodelsreader.util.MyAppConctants;

import java.util.Arrays;
import java.util.List;

import static com.blogspot.alexeykutovenko.scalemodelsreader.util.MyAppConctants.DEFAULT_CATEGORY;

/**
 * Data Converters for Room Database
 */
public class DataConverters {
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
        String categoryId;
        String categoryName;
        try {
            categoryId = categoryList.get(0);
        } catch (NullPointerException npe) {
            categoryId = DEFAULT_CATEGORY;
        }

        try {
            categoryName = categoryList.get(1);
        } catch (ArrayIndexOutOfBoundsException aibe) {
            categoryName = "No category";
        }


        return new Category(categoryId, categoryName);
    }

    @TypeConverter
    public static String fromImagesUrls(String[] array){
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            str.append(array[i]);
            if (i < array.length - 1) {
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