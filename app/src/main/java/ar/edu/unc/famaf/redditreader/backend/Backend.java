package ar.edu.unc.famaf.redditreader.backend;

import android.content.Context;


public class Backend {
    private static Backend ourInstance = new Backend();

    public static Backend getInstance() {
        return ourInstance;
    }

    private Backend() {
    }

    public static void getTopPosts(TaskListener listener, Context context) {
        new GetTopPostsTask(listener, context).execute("https://www.reddit.com/top/.json?limit=50");
    }

}
