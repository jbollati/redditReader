package ar.edu.unc.famaf.redditreader.backend;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import ar.edu.unc.famaf.redditreader.model.Listing;


public class GetPostsTask extends AsyncTask<String, Void, Listing> {
    private PostsIteratorListener mListener;
    private Context mCtx;
    private int mCategory;

    public GetPostsTask(PostsIteratorListener listener, Context context, int category) {
        super();
        this.mListener = listener;
        this.mCtx = context;
        this.mCategory = category;
    }

    @Override
    protected Listing doInBackground(String...urls) {
        Listing listing = null;

        RedditDBHelper DBHelper = new RedditDBHelper(mCtx, 1);

        if (isOnline()) {
            HttpURLConnection conn;
            try {
                conn = (HttpURLConnection) new URL(urls[0]).openConnection();
                conn.setRequestMethod("GET");
                InputStream jsonStream = conn.getInputStream();
                listing = new Parser().readJsonStream(jsonStream);
                DBHelper.savePosts(listing.getChildren(), mCategory);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        DBHelper.close();
        return listing;
    }

    @Override
    protected void onPostExecute(Listing listing) {
        super.onPostExecute(listing);
        Backend.getInstance().getNextPosts(mListener, mCtx, 0, mCategory);
    }

    private boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager)
                mCtx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
