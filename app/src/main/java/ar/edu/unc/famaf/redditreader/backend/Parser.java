package ar.edu.unc.famaf.redditreader.backend;


import android.util.JsonReader;
import android.util.JsonToken;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ar.edu.unc.famaf.redditreader.model.Listing;
import ar.edu.unc.famaf.redditreader.model.PostModel;

public class Parser {
    public Listing readJsonStream(InputStream in) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        Listing posts = null;

        try {
            reader.beginObject();
            while (reader.hasNext()) {
                String name = reader.nextName();
                if (name.equals("data")) {
                    posts = readListingObject(reader);
                } else {
                    reader.skipValue();
                }
            }
            reader.endObject();
        } finally {
            reader.close();
        }

        return posts;
    }

    private Listing readListingObject(JsonReader reader) throws IOException {
        Listing listing = new Listing();
        String before = null;
        String after = null;
        String modhash = null;
        List<PostModel> children = null;

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            switch (name) {
                case "modhash":
                    modhash = reader.nextString();
                    break;
                case "before":
                    if (reader.peek() != JsonToken.NULL) {
                        before = reader.nextString();
                    } else {
                        reader.skipValue();
                    }
                    break;
                case "after":
                    if (reader.peek() != JsonToken.NULL) {
                        after = reader.nextString();
                    } else {
                        reader.skipValue();
                    }
                    break;
                case "children":
                    children = readPostsArray(reader);
                    break;
                default:
                    reader.skipValue();
            }
        }
        reader.endObject();
        listing.setAfter(after);
        listing.setBefore(before);
        listing.setChildren(children);
        listing.setModhash(modhash);
        return listing;
    }

    private List<PostModel> readPostsArray(JsonReader reader) throws IOException {
        List<PostModel> posts = new ArrayList<>();
        reader.beginArray();
        while (reader.hasNext()) {
            reader.beginObject();
            while (reader.hasNext()) {
                String name = reader.nextName();
                if (name.equals("data")) {
                    posts.add(readPost(reader));
                } else {
                    reader.skipValue();
                }
            }
            reader.endObject();
        }
        reader.endArray();
        return posts;
    }

    private PostModel readPost(JsonReader reader) throws IOException {
        PostModel post = new PostModel();
        String author = null;
        String title = null;
        int num_comments = 0;
        long create = 0;
        String thumbnail = null;
        String subreddit = null;

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            switch (name) {
                case "author":
                    if (reader.peek() != JsonToken.NULL) {
                        author = reader.nextString();
                    } else {
                        reader.skipValue();
                    }
                    break;
                case "title":
                    title = reader.nextString();
                    break;
                case "num_comments":
                    num_comments = reader.nextInt();
                    break;
                case "created_utc":
                    create = reader.nextLong() * 1000;
                    break;
                case "thumbnail":
                    thumbnail = reader.nextString();
                    break;
                case "subreddit":
                    subreddit = "/r/" + reader.nextString();
                    break;
                default:
                    reader.skipValue();
            }
        }
        reader.endObject();
        post.setSubreddit(subreddit);
        post.setTitle(title);
        post.setAuthor(author);
        post.setCreateTime(create);
        post.setNoComments(num_comments);
        post.setThumbnailUrl(thumbnail);

        return post;
    }
}

