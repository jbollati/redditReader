package ar.edu.unc.famaf.redditreader.model;


import android.graphics.Bitmap;

import java.util.Date;

public class PostModel {
    private String mAuthor;
    private String mTitle;
    private int mNoComments;
    private Date mCreateTime;
    private String mThumbnailUrl;
    private Bitmap mThumbnailBitmap;
    private String mSubreddit;

    public String getAuthor() {
        return mAuthor;
    }

    public void setAuthor(String author) {
        this.mAuthor = author;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public int getNoComments() {
        return mNoComments;
    }

    public void setNoComments(int noComments) {
        this.mNoComments = noComments;
    }

    public Date getCreateTime() {
        return mCreateTime;
    }

    public void setCreateTime(Date createTime) {
        this.mCreateTime = createTime;
    }

    public String getThumbnailUrl() {
        return mThumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.mThumbnailUrl = thumbnailUrl;
    }

    public Bitmap getThumbnailBitmap() {
        return mThumbnailBitmap;
    }

    public void setThumbnailBitmap(Bitmap thumbnailBitmap) {
        this.mThumbnailBitmap = thumbnailBitmap;
    }

    public String getSubreddit() {
        return mSubreddit;
    }

    public void setSubreddit(String subreddit) {
        this.mSubreddit = subreddit;
    }
}

