package ar.edu.unc.famaf.redditreader.backend;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import ar.edu.unc.famaf.redditreader.model.PostModel;

public class RedditDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "post_db";
    private static final String POST_TABLE = "post";
    private static final String POST_AUTHOR = "author";
    private static final String POST_TITLE = "title";
    private static final String POST_NO_COMMENTS = "no_comments";
    private static final String POST_CREATE_TIME = "create_time";
    private static final String POST_THUMBNAIL_URL = "thumbnail_url";
    private static final String POST_THUMBNAIL_ARRAY = "thumbnail_array";
    private static final String POST_SUBREDDIT = "subreddit";

    /*private String mAuthor;
    private String mTitle;
    private long mNoComments;
    private Date mCreateTime;
    private String mThumbnailUrl;
    private Bitmap mThumbnailBitmap;
    private String mSubreddit;
*/

    public RedditDBHelper(Context context, int version) {
        super(context, DATABASE_NAME, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createSentence = "create_table"
                + POST_TABLE + "(_id integer primary key autoincrement, "
                + POST_AUTHOR + " text,"
                + POST_TITLE + " text not null, "
                + POST_NO_COMMENTS + " integer not null, "
                + POST_CREATE_TIME + " long not null, "
                + POST_THUMBNAIL_URL + " text, "
                + POST_THUMBNAIL_ARRAY + " blob, "
                + POST_SUBREDDIT + " text not null);";

        db.execSQL(createSentence);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXIST " + POST_TABLE);
        this.onCreate(db);
    }

    public void savePosts(List<PostModel> posts) {
        SQLiteDatabase db = getWritableDatabase();

        db.beginTransaction();

        // Delete old posts
        db.delete(POST_TABLE, null, null);
        for (PostModel post : posts) {
            ContentValues values = new ContentValues();
            values.put(POST_AUTHOR, post.getAuthor());
            values.put(POST_TITLE, post.getTitle());
            values.put(POST_NO_COMMENTS, post.getNoComments());
            values.put(POST_CREATE_TIME, post.getCreateTime());
            values.put(POST_THUMBNAIL_URL, post.getThumbnailUrl());
            values.put(POST_SUBREDDIT, post.getSubreddit());

            db.insert(POST_TABLE, null, values);
        }

        db.endTransaction();
    }

    public List<PostModel> getPosts() {
        List<PostModel> posts = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + POST_TABLE, null);

        if (cursor.moveToFirst()) {
            do {
                PostModel post = new PostModel();
                if (!cursor.isNull(cursor.getColumnIndex(POST_AUTHOR))) {
                    post.setAuthor(cursor.getString(cursor.getColumnIndex(POST_AUTHOR)));
                }
                post.setTitle(cursor.getString(cursor.getColumnIndex(POST_TITLE)));
                post.setNoComments(cursor.getInt(cursor.getColumnIndex(POST_NO_COMMENTS)));
                post.setCreateTime(cursor.getLong(cursor.getColumnIndex(POST_CREATE_TIME)));
                post.setThumbnailUrl(cursor.getString(cursor.getColumnIndex(POST_THUMBNAIL_URL)));
                post.setSubreddit(cursor.getString(cursor.getColumnIndex(POST_SUBREDDIT)));
                if (!cursor.isNull(cursor.getColumnIndex(POST_THUMBNAIL_ARRAY))) {
                    byte[] image = cursor.getBlob(cursor.getColumnIndex(POST_THUMBNAIL_ARRAY));
                    Bitmap thumbnail = BitmapFactory.decodeByteArray(image, 0, image.length);
                    post.setThumbnailBitmap(thumbnail);
                }
                posts.add(post);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return posts;
    }
}
