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
    private PostAdapter mPostAdapterTOP = null;
    private PostAdapter mPostAdapterNEW = null;
    private PostAdapter mPostAdapterHOT = null;
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
        // TOP
        mPostAdapterTOP = new PostAdapter(getContext(), R.layout.post_row);
        ListView postLV = (ListView) rootView.findViewById(R.id.posts_lv_top);
        postLV.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                return Backend.getInstance().getNextPosts(NewsActivityFragment.this, getContext(),
                        totalItemsCount, 0);
            }
        });
        postLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mCallback.onPostItemPicked(mPostAdapterTOP.getItem(position));
            }
        });
        postLV.setAdapter(mPostAdapterTOP);

        // NEW
        mPostAdapterNEW = new PostAdapter(getContext(), R.layout.post_row);
        postLV = (ListView) rootView.findViewById(R.id.posts_lv_new);
        postLV.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                return Backend.getInstance().getNextPosts(NewsActivityFragment.this, getContext(),
                        totalItemsCount, 1);
            }
        });
        postLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mCallback.onPostItemPicked(mPostAdapterNEW.getItem(position));
            }
        });
        postLV.setAdapter(mPostAdapterNEW);

        // HOT
        mPostAdapterHOT = new PostAdapter(getContext(), R.layout.post_row);
        postLV = (ListView) rootView.findViewById(R.id.posts_lv_hot);
        postLV.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                return Backend.getInstance().getNextPosts(NewsActivityFragment.this, getContext(),
                        totalItemsCount, 2);
            }
        });
        postLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mCallback.onPostItemPicked(mPostAdapterHOT.getItem(position));
            }
        });
        postLV.setAdapter(mPostAdapterHOT);

        Backend.GetPosts(getContext(), 0);
        Backend.GetPosts(getContext(), 1);
        Backend.GetPosts(getContext(), 2);
        Backend.getInstance().getNextPosts(NewsActivityFragment.this, getContext(), 0, 0);
        Backend.getInstance().getNextPosts(NewsActivityFragment.this, getContext(), 0, 1);
        Backend.getInstance().getNextPosts(NewsActivityFragment.this, getContext(), 0, 2);
        return rootView;
    }

    @Override
    public void nextPosts(List<PostModel> posts, int category) {
        if (posts != null) {
            switch (category) {
                case 0:
                    mPostAdapterTOP.addAll(posts);
                    break;
                case 1:
                    mPostAdapterNEW.addAll(posts);
                    break;
                case 2:
                    mPostAdapterHOT.addAll(posts);
                    break;
                default:
                    break;
            }

        }
    }
}
