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


public class GetTopPostsTask extends AsyncTask<String, Void, Listing> {
    private Context mCtx;

    public GetTopPostsTask(Context context) {
        super();
        this.mCtx = context;
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
                DBHelper.savePosts(listing.getChildren());
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
    }

    private boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager)
                mCtx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
