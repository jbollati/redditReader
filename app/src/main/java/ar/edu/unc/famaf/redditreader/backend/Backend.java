package ar.edu.unc.famaf.redditreader.backend;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import ar.edu.unc.famaf.redditreader.model.Listing;
import ar.edu.unc.famaf.redditreader.model.PostModel;

public class Backend {
    private static Backend ourInstance = new Backend();

    public static Backend getInstance() {
        return ourInstance;
    }

    private Backend() {
    }

    public static void getTopPosts(TaskListener listener) {
        new GetTopPostsTask(listener).execute("https://www.reddit.com/top/.json?limit=50");
    }

}
