package ar.edu.unc.famaf.redditreader.model;


import java.util.Date;

public class PostModel {
    private String mPost_autor;
    private String mPost_title;
    private int mPost_no_comments;
    private Date mPost_create;
    private String mPost_thumbnail_url;
    private String mPost_subreddit;

    public String getPost_autor() {
        return mPost_autor;
    }

    public void setPost_autor(String post_autor) {
        mPost_autor = post_autor;
    }

    public String getPost_title() {
        return mPost_title;
    }

    public void setPost_title(String post_title) {
        mPost_title = post_title;
    }

    public int getPost_no_comments() {
        return mPost_no_comments;
    }

    public void setPost_no_comments(int post_no_comments) {
        mPost_no_comments = post_no_comments;
    }

    public Date getPost_create() {
        return mPost_create;
    }

    public void setPost_create(Date post_create) {
        mPost_create = post_create;
    }

    public String getPost_thumbnail() {
        return mPost_thumbnail_url;
    }

    public void setPost_thumbnail(String post_thumbnail_url) {
        mPost_thumbnail_url = post_thumbnail_url;
    }

    public String getPost_subreddit() {
        return mPost_subreddit;
    }

    public void setPost_subreddit(String post_subreddit) {
        mPost_subreddit = post_subreddit;
    }
}
