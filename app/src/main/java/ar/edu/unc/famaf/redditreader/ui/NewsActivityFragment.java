package ar.edu.unc.famaf.redditreader.ui;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import ar.edu.unc.famaf.redditreader.R;
import ar.edu.unc.famaf.redditreader.backend.Backend;
import ar.edu.unc.famaf.redditreader.backend.EndlessScrollListener;
import ar.edu.unc.famaf.redditreader.backend.PostsIteratorListener;
import ar.edu.unc.famaf.redditreader.model.PostModel;


/**
 * A placeholder fragment containing a simple view.
 */
public class NewsActivityFragment extends Fragment implements PostsIteratorListener {
    private PostAdapter mPostAdapter = null;
    private OnPostItemSelectedListener mCallback;

    public NewsActivityFragment() {
    }

    public interface OnPostItemSelectedListener{
        void onPostItemPicked(PostModel post);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mCallback = (OnPostItemSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_news, container, false);
        mPostAdapter = new PostAdapter(getContext(), R.layout.post_row);
        ListView postLV = (ListView) rootView.findViewById(R.id.posts_lv);
        postLV.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                return Backend.getInstance().getNextPosts(NewsActivityFragment.this, getContext(),
                        totalItemsCount);
            }
        });
        postLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mCallback.onPostItemPicked(mPostAdapter.getItem(position));
            }
        });
        Backend.getInstance().getNextPosts(NewsActivityFragment.this, getContext(), 0);
        postLV.setAdapter(mPostAdapter);
        return rootView;
    }

    @Override
    public void nextPosts(List<PostModel> posts) {
        if (posts != null) {
            mPostAdapter.addAll(posts);
        }
    }
}
