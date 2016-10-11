package ar.edu.unc.famaf.redditreader.model;


import java.util.Date;

public class PostModel {
    private String mPost_autor;
    private String mPost_title;
    private int mPost_no_comments;
    private Date mPost_create;
    private int mPost_thumbnail_id;
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

    public void setPost_no_commnets(int post_no_commnets) {
        mPost_no_comments = post_no_commnets;
    }

    public Date getPost_create() {
        return mPost_create;
    }

    public void setPost_create(Date post_create) {
        mPost_create = post_create;
    }

    public int getPost_thumbnail_id() {
        return mPost_thumbnail_id;
    }

    public void setPost_thumbnail(int post_thumbnail_id) {
        mPost_thumbnail_id = post_thumbnail_id;
    }

    public String getPost_subreddit() {
        return mPost_subreddit;
    }

    public void setPost_subreddit(String post_subreddit) {
        mPost_subreddit = post_subreddit;
    }
}
