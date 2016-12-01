package ar.edu.unc.famaf.redditreader.model;


import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;


public class PostModel implements Parcelable {
    private String mAuthor;
    private String mTitle;
    private int mNoComments;
    private long mCreateTime;
    private String mThumbnailUrl;
    private Bitmap mThumbnailBitmap;
    private String mSubreddit;
    private String mPermalink;
    private String mPreview;


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

    public long getCreateTime() {
        return mCreateTime;
    }

    public void setCreateTime(long createTime) {
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

    public String getPermalink() {
        return mPermalink;
    }

    public void setPermalink(String permalink) {
        this.mPermalink = permalink;
    }

    public String getPreview() {
        return mPreview;
    }

    public void setPreview(String preview) {
        this.mPreview = preview;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mAuthor);
        dest.writeString(mTitle);
        dest.writeInt(mNoComments);
        dest.writeLong(mCreateTime);
        dest.writeString(mThumbnailUrl);
        dest.writeParcelable(mThumbnailBitmap, flags);
        dest.writeString(mSubreddit);
        dest.writeString(mPermalink);
        dest.writeString(mPreview);
    }

    public static final Parcelable.Creator<PostModel> CREATOR = new Parcelable.Creator<PostModel>() {
        public PostModel createFromParcel(Parcel in) {
            return new PostModel(in);
        }

        public PostModel[] newArray(int size) {
            return new PostModel[size];
        }
    };

    private PostModel(Parcel in) {
        mAuthor = in.readString();
        mTitle = in.readString();
        mNoComments = in.readInt();
        mCreateTime = in.readLong();
        mThumbnailUrl = in.readString();
        mThumbnailBitmap = in.readParcelable(Bitmap.class.getClassLoader());
        mSubreddit = in.readString();
        mPermalink = in.readString();
        mPreview = in.readString();
    }

    public PostModel() {
    }
}
