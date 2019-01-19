package com.blogspot.alexeykutovenko.scalemodelsreader.db.entity;

import com.blogspot.alexeykutovenko.scalemodelsreader.util.MyAppConctants;
import com.blogspot.alexeykutovenko.scalemodelsreader.util.StringUtils;

import androidx.annotation.NonNull;

public class Category implements Comparable<Category> {
    private String id;

    private String name_ru;

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getName_ru()
    {
        return name_ru;
    }

    public Category() {
    }

    public Category (String id, String name_ru){
        this.id = id;
        this.name_ru = StringUtils.unescapeHtml3(name_ru);
    }

    @NonNull
    @Override
    public String toString() {
        return id + MyAppConctants.STRING_SEPARATOR + name_ru;
    }

    @Override
    public int compareTo(Category otherCategory) {
        return this.name_ru.compareTo(otherCategory.name_ru);
    }
}
