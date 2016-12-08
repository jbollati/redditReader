package ar.edu.unc.famaf.redditreader.backend;

import android.content.Context;


public class Backend {
    private static Backend ourInstance = new Backend();
    private final int maxPosts;

    public static Backend getInstance() {
        return ourInstance;
    }

    private Backend() {
        maxPosts = 50;
    }

    public static void GetPosts(Context context, int category) {
        switch (category) {
            case 0:
                new GetPostsTask(context, category).execute("https://www.reddit.com/top/.json?limit=50");
                break;
            case 1:
                new GetPostsTask(context, category).execute("https://www.reddit.com/new/.json?limit=50");
                break;
            case 2:
                new GetPostsTask(context, category).execute("https://www.reddit.com/hot/.json?limit=50");
                break;
            default:
                break;
        }
    }

    public boolean getNextPosts(final PostsIteratorListener listener, Context context, int count, int category) {
        if (count < maxPosts) {
            RedditDBHelper DBHelper = new RedditDBHelper(context, 1);
            listener.nextPosts(DBHelper.getPosts(5, count, category), category);
            DBHelper.close();
            return true;
        } else {
            return false;
        }
    }
}
