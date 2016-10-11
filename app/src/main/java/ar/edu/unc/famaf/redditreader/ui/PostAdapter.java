package ar.edu.unc.famaf.redditreader.ui;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ar.edu.unc.famaf.redditreader.R;
import ar.edu.unc.famaf.redditreader.model.PostModel;

public class PostAdapter extends ArrayAdapter<PostModel> {
    private List<PostModel> postLst = null;

    private class ViewHolder {
        public final ImageView postThumbnail;
        public final TextView postSubreddit;
        public final TextView postDate;
        public final TextView postTitle;
        public final TextView postNoComments;

        public ViewHolder(ImageView postThumbnail, TextView postSubreddit, TextView postDate,
                          TextView postTitle, TextView postNoComments) {
            this.postThumbnail = postThumbnail;
            this.postSubreddit = postSubreddit;
            this.postDate = postDate;
            this.postTitle = postTitle;
            this.postNoComments = postNoComments;
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
                    (TextView) convertView.findViewById(R.id.post_no_comments));
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        PostModel pm = postLst.get(position);

        viewHolder.postThumbnail.setImageResource(pm.getPost_thumbnail_id());

        viewHolder.postSubreddit.setText(pm.getPost_subreddit());

        CharSequence relativeTime = DateUtils.getRelativeDateTimeString(getContext(),
                pm.getPost_create().getTime(), DateUtils.SECOND_IN_MILLIS,
                DateUtils.WEEK_IN_MILLIS,0);
        viewHolder.postDate.setText(relativeTime);

        viewHolder.postTitle.setText(pm.getPost_title());

        viewHolder.postNoComments.setText(String.format(
                getContext().getResources().getString(R.string.no_comments_post),
                pm.getPost_no_comments()));

        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return postLst.get(position).hashCode();
    }
}
