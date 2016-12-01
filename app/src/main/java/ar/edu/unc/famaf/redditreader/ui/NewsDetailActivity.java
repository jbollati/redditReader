package ar.edu.unc.famaf.redditreader.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import ar.edu.unc.famaf.redditreader.R;
import ar.edu.unc.famaf.redditreader.model.PostModel;

public class NewsDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        PostModel post = intent.getParcelableExtra("post");

        TextView titleView = (TextView) findViewById(R.id.detail_title);
        titleView.setText(post.getTitle());

        TextView subredditView = (TextView) findViewById(R.id.detail_subbreddit);
        subredditView.setText(post.getSubreddit());

        TextView dateView = (TextView) findViewById(R.id.detail_date);
        CharSequence relativeTime = DateUtils.getRelativeDateTimeString(this, post.getCreateTime(),
                DateUtils.SECOND_IN_MILLIS, DateUtils.WEEK_IN_MILLIS, DateUtils.FORMAT_ABBREV_ALL);
        dateView.setText(relativeTime);

        TextView authorView = (TextView) findViewById(R.id.detail_author);
        authorView.setText(String.format(getResources().getString(R.string.detail_upload),
                post.getAuthor()));

        TextView permalinkView = (TextView) findViewById(R.id.detail_permalink);
        permalinkView.setTag(post.getPermalink());

        final ImageView previewView = (ImageView) findViewById(R.id.detail_preview);
        String strUrl = post.getPreview();
        if (strUrl != null) {
            new AsyncTask<String, Void, Bitmap>() {
                @Override
                protected Bitmap doInBackground(String... params) {
                    Bitmap bitmap = null;
                    try {
                        URL url = new URL(params[0]);
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        InputStream is = connection.getInputStream();
                        bitmap = BitmapFactory.decodeStream(is, null, null);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return bitmap;
                }

                @Override
                protected void onPostExecute(Bitmap bitmap) {
                    super.onPostExecute(bitmap);
                    previewView.setImageBitmap(bitmap);
                }
            }.execute(strUrl);
        }
    }

    public void onClick (View v) {
        if (v.getId() == R.id.detail_permalink) {
            Intent intent = new Intent(this, WebDetailActivity.class);
            intent.putExtra("link", "https://www.reddit.com" + v.getTag());
            startActivity(intent);
        }
    }
}
