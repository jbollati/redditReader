package ar.edu.unc.famaf.redditreader.backend;

import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import ar.edu.unc.famaf.redditreader.model.Listing;


public class GetTopPostsTask extends AsyncTask<String, Void, Listing> {
    private TaskListener listener;

    public GetTopPostsTask(TaskListener listener) {
        super();
        this.listener = listener;
    }

    @Override
    protected Listing doInBackground(String...urls) {
        Listing list = null;
        HttpURLConnection conn;
        try {
            conn = (HttpURLConnection) new URL(urls[0]).openConnection();
            conn.setRequestMethod("GET");
            InputStream jsonStream = conn.getInputStream();
            list = new Parser().readJsonStream(jsonStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    protected void onPostExecute(Listing listing) {
        super.onPostExecute(listing);
        listener.nextPosts(listing.getChildren());
    }
}
