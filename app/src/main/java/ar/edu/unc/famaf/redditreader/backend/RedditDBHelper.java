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
    private static final String DATABASE_NAME = "post_db.db";
    private static final String TOP_TABLE = "top_post";
    private static final String NEW_TABLE = "new_post";
    private static final String HOT_TABLE = "hot_post";
    private static final String POST_AUTHOR = "author";
    private static final String POST_TITLE = "title";
    private static final String POST_NO_COMMENTS = "no_comments";
    private static final String POST_CREATE_TIME = "create_time";
    private static final String POST_THUMBNAIL_URL = "thumbnail_url";
    private static final String POST_THUMBNAIL_ARRAY = "thumbnail_array";
    private static final String POST_SUBREDDIT = "subreddit";
    private static final String POST_PREVIEW = "preview";
    private static final String POST_PERMALINK = "permalink";


    public RedditDBHelper(Context context, int version) {
        super(context, DATABASE_NAME, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createSentence = "create table "
                + TOP_TABLE + "(_id integer primary key autoincrement, "
                + POST_AUTHOR + " text,"
                + POST_TITLE + " text not null, "
                + POST_NO_COMMENTS + " integer not null, "
                + POST_CREATE_TIME + " long not null, "
                + POST_THUMBNAIL_URL + " text, "
                + POST_THUMBNAIL_ARRAY + " blob, "
                + POST_PREVIEW + " text, "
                + POST_PERMALINK + " text, "
                + POST_SUBREDDIT + " text not null);";
        db.execSQL(createSentence);

        createSentence = "create table "
                + NEW_TABLE + "(_id integer primary key autoincrement, "
                + POST_AUTHOR + " text,"
                + POST_TITLE + " text not null, "
                + POST_NO_COMMENTS + " integer not null, "
                + POST_CREATE_TIME + " long not null, "
                + POST_THUMBNAIL_URL + " text, "
                + POST_THUMBNAIL_ARRAY + " blob, "
                + POST_PREVIEW + " text, "
                + POST_PERMALINK + " text, "
                + POST_SUBREDDIT + " text not null);";
        db.execSQL(createSentence);

        createSentence = "create table "
                + HOT_TABLE + "(_id integer primary key autoincrement, "
                + POST_AUTHOR + " text,"
                + POST_TITLE + " text not null, "
                + POST_NO_COMMENTS + " integer not null, "
                + POST_CREATE_TIME + " long not null, "
                + POST_THUMBNAIL_URL + " text, "
                + POST_THUMBNAIL_ARRAY + " blob, "
                + POST_PREVIEW + " text, "
                + POST_PERMALINK + " text, "
                + POST_SUBREDDIT + " text not null);";
        db.execSQL(createSentence);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXIST " + TOP_TABLE);
        db.execSQL("DROP TABLE IF EXIST " + NEW_TABLE);
        db.execSQL("DROP TABLE IF EXIST " + HOT_TABLE);
        this.onCreate(db);
    }

    public void savePosts(List<PostModel> posts, int category) {
        SQLiteDatabase db = getWritableDatabase();
        String table;

        switch (category) {
            case 0:
                table = TOP_TABLE;
                break;
            case 1:
                table = NEW_TABLE;
                break;
            case 2:
                table = HOT_TABLE;
                break;
            default:
                table = TOP_TABLE;
        }


        db.beginTransaction();

        // Delete old posts
        db.delete(table, null, null);
        for (PostModel post : posts) {
            ContentValues values = new ContentValues();
            values.put(POST_AUTHOR, post.getAuthor());
            values.put(POST_TITLE, post.getTitle());
            values.put(POST_NO_COMMENTS, post.getNoComments());
            values.put(POST_CREATE_TIME, post.getCreateTime());
            values.put(POST_THUMBNAIL_URL, post.getThumbnailUrl());
            values.put(POST_SUBREDDIT, post.getSubreddit());
            values.put(POST_PREVIEW, post.getPreview());
            values.put(POST_PERMALINK, post.getPermalink());

            db.insert(table, null, values);
        }
        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public List<PostModel> getPosts(int limit, int offset, int category) {
        String table;

        switch (category) {
            case 0:
                table = TOP_TABLE;
                break;
            case 1:
                table = NEW_TABLE;
                break;
            case 2:
                table = HOT_TABLE;
                break;
            default:
                table = TOP_TABLE;
        }

        List<PostModel> posts = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + table
                + " LIMIT " + Integer.toString(limit)
                + " OFFSET " + Integer.toString(offset)
                , null);


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
                post.setPermalink(cursor.getString(cursor.getColumnIndex(POST_PERMALINK)));
                post.setPreview(cursor.getString(cursor.getColumnIndex(POST_PREVIEW)));
                if (!cursor.isNull(cursor.getColumnIndex(POST_THUMBNAIL_ARRAY))) {
                    Bitmap thumbnail = getImage(cursor.getBlob(cursor.getColumnIndex(POST_THUMBNAIL_ARRAY)));
                    post.setThumbnailBitmap(thumbnail);
                }
                posts.add(post);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return posts;
    }

    public void saveImage(String url, Bitmap image) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(POST_THUMBNAIL_ARRAY, getBytes(image));

        db.update(TOP_TABLE, values, POST_THUMBNAIL_URL + "=?", new String[]{url});
        db.update(NEW_TABLE, values, POST_THUMBNAIL_URL + "=?", new String[]{url});
        db.update(HOT_TABLE, values, POST_THUMBNAIL_URL + "=?", new String[]{url});
    }

    private static byte[] getBytes(Bitmap bitmap)
    {
        ByteArrayOutputStream stream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,0, stream);
        return stream.toByteArray();
    }

    private static Bitmap getImage(byte[] image)
    {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }
}
