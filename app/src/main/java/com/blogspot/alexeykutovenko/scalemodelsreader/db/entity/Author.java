package com.blogspot.alexeykutovenko.scalemodelsreader.db.entity;

import com.blogspot.alexeykutovenko.scalemodelsreader.util.MyAppConctants;
import com.blogspot.alexeykutovenko.scalemodelsreader.util.StringUtils;


/**
 * Class for Scalemodels Authors
 */
public class Author
{
    private String uid;

    private String name;

    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = StringUtils.unescapeHtml3(name);
    }

    public Author (String uid, String name){
        this.uid = uid;
        this.name = StringUtils.unescapeHtml3(name);
    }

    @Override
    public String toString() {
        return uid + MyAppConctants.STRING_SEPARATOR + name;
    }
}
