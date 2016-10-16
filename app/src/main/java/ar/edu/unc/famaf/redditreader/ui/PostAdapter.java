package ar.edu.unc.famaf.redditreader.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.util.LruCache;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import ar.edu.unc.famaf.redditreader.R;
import ar.edu.unc.famaf.redditreader.model.PostModel;

public class PostAdapter extends ArrayAdapter<PostModel> {
    private List<PostModel> postLst = null;
    final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
    final int cacheSize = maxMemory / 8;
    private LruCache<URL, Bitmap> bitmapLruCache = new LruCache<>(cacheSize);

    private class ViewHolder {
        final ImageView postThumbnail;
        final TextView postSubreddit;
        final TextView postDate;
        final TextView postTitle;
        final TextView postNoComments;
        final ProgressBar postProgressBar;

        ViewHolder(ImageView postThumbnail, TextView postSubreddit, TextView postDate,
                   TextView postTitle, TextView postNoComments,ProgressBar postProgressBar) {
            this.postThumbnail = postThumbnail;
            this.postSubreddit = postSubreddit;
            this.postDate = postDate;
            this.postTitle = postTitle;
            this.postNoComments = postNoComments;
            this.postProgressBar = postProgressBar;
        }
    }

    public PostAdapter(Context context, int textViewResourceId, List<PostModel> postLst) {
        super(context, textViewResourceId);
        this.postLst = postLst;
    }

    @Override
    public int getCount() {
        return postLst.size();
    }

    @Override
    public PostModel getItem(int position) {
        return postLst.get(position);
    }

    @Override
    public int getPosition(PostModel item) {
        return postLst.indexOf(item);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.post_row, null);

            viewHolder = new ViewHolder((ImageView) convertView.findViewById(R.id.post_thumbnail),
                    (TextView) convertView.findViewById(R.id.post_subreddit),
                    (TextView) convertView.findViewById(R.id.post_submitted_time),
                    (TextView) convertView.findViewById(R.id.post_title),
                    (TextView) convertView.findViewById(R.id.post_no_comments),
                    (ProgressBar) convertView.findViewById(R.id.thumbnail_progressBar));
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        PostModel pm = postLst.get(position);

        viewHolder.postSubreddit.setText(pm.getPost_subreddit());

        CharSequence relativeTime = DateUtils.getRelativeDateTimeString(getContext(),
                pm.getPost_create().getTime(), DateUtils.SECOND_IN_MILLIS,
                DateUtils.WEEK_IN_MILLIS,0);

        viewHolder.postDate.setText(relativeTime);

        viewHolder.postTitle.setText(pm.getPost_title());

        viewHolder.postNoComments.setText(String.format(
                getContext().getResources().getString(R.string.no_comments_post),
                pm.getPost_no_comments()));


        viewHolder.postThumbnail.setImageDrawable(null);
        imageDownloader imDw = new imageDownloader(this.bitmapLruCache, viewHolder.postThumbnail, viewHolder.postProgressBar);
        imDw.execute(pm.getPost_thumbnail());

        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return postLst.get(position).hashCode();
    }

    private class imageDownloader extends AsyncTask<String, Void, Bitmap> {
        private ImageView imageView;
        private ProgressBar progressBar;
        private LruCache<URL, Bitmap> bitmapLruCache;

        public imageDownloader(LruCache<URL, Bitmap> bitmapCache, ImageView imageView, ProgressBar pb) {
            super();
            this.bitmapLruCache = bitmapCache;
            this.imageView = imageView;
            this.progressBar = pb;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            URL url;
            Bitmap bitmap = null;
            HttpURLConnection connection;
            InputStream is;

            try {
                url = new URL(params[0]);
                bitmap = bitmapLruCache.get(url);
                if (bitmap == null) {
                    connection = (HttpURLConnection) url.openConnection();
                    is = connection.getInputStream();
                    bitmap = BitmapFactory.decodeStream(is, null, null);
                    bitmapLruCache.put(url, bitmap);
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            progressBar.setVisibility(View.INVISIBLE);
            imageView.setImageBitmap(bitmap);
        }
    }
}
