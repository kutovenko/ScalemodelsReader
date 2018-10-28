package com.blogspot.alexeykutovenko.scalemodelsreader.db.entity;

import com.blogspot.alexeykutovenko.scalemodelsreader.utilities.MyAppConctants;

public class Author
{
    private String uid;

    private String name;

    public String getUid ()
    {
        return uid;
    }

    public void setUid (String uid)
    {
        this.uid = uid;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public Author (String uid, String name){
        this.uid = uid;
        this.name = name;
    }

    @Override
    public String toString()
    {
        return uid + MyAppConctants.STRING_SEPARATOR + name;
    }
}
