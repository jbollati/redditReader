package ar.edu.unc.famaf.redditreader.backend;

import android.content.Context;


public class Backend {
    private static Backend ourInstance = new Backend();
    private int fstTime = 1;
    private final int maxPosts = 50;

    public static Backend getInstance() {
        return ourInstance;
    }

    private Backend() {
    }

    public static void getTopPosts(Context context) {
        new GetTopPostsTask(context).execute("https://www.reddit.com/top/.json?limit=50");
        Backend.getInstance().fstTime = 0;
    }

    public boolean getNextPosts(final PostsIteratorListener listener, Context context, int count) {
        if (fstTime == 1) {
            getTopPosts(context);
        }

        if (count < maxPosts) {
            RedditDBHelper DBHelper = new RedditDBHelper(context, 1);
            listener.nextPosts(DBHelper.getPosts(5, count));
            DBHelper.close();
            return true;
        } else {
            return false;
        }
    }
}
