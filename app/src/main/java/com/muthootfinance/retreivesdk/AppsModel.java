package com.muthootfinance.retreivesdk;

import java.io.File;

public class AppsModel {
    private File File;
    private String Title;
    private String Description;
    private String Tags;
    private String IsPasswordProtected;
    private String Password;
    private String LUSR;

    public AppsModel(java.io.File file, String title, String description, String tags, String isPasswordProtected, String password, String LUSR) {
        File = file;
        Title = title;
        Description = description;
        Tags = tags;
        IsPasswordProtected = isPasswordProtected;
        Password = password;
        this.LUSR = LUSR;
    }

    public java.io.File getFile() {
        return File;
    }

    public void setFile(java.io.File file) {
        File = file;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getTags() {
        return Tags;
    }

    public void setTags(String tags) {
        Tags = tags;
    }

    public String getIsPasswordProtected() {
        return IsPasswordProtected;
    }

    public void setIsPasswordProtected(String isPasswordProtected) {
        IsPasswordProtected = isPasswordProtected;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getLUSR() {
        return LUSR;
    }

    public void setLUSR(String LUSR) {
        this.LUSR = LUSR;
    }
}
