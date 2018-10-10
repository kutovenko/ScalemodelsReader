package com.blogspot.alexeykutovenko.scalemodelsreader.network;

import com.blogspot.alexeykutovenko.scalemodelsreader.utilities.MyAppConctants;

public class Category
{
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

    public String getName_ru ()
    {
        return name_ru;
    }

    public void setName_ru (String name_ru)
    {
        this.name_ru = name_ru;
    }

    public Category (String id, String name_ru){
        this.id = id;
        this.name_ru = name_ru;
    }

    @Override
    public String toString()
    {
        return id + MyAppConctants.STRING_SEPARATOR + name_ru;
    }
}
