package ar.edu.unc.famaf.redditreader.backend;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import ar.edu.unc.famaf.redditreader.model.PostModel;

public class Backend {
    private static Backend ourInstance = new Backend();

    public static Backend getInstance() {
        return ourInstance;
    }

    private Backend() {
    }

    public static List<PostModel> getTopPosts() {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.US);


        PostModel post1 = new PostModel();
        post1.setPost_autor("Wolverine");
        try {
            post1.setPost_create(df.parse("09/10/2016 13:12:01"));
        } catch(java.text.ParseException e) {
            System.out.println(e.getMessage());
        }
        post1.setPost_no_comments(2342);
        post1.setPost_subreddit("/r/pics");
        post1.setPost_thumbnail("http://vignette2.wikia.nocookie.net/valkyriecrusade/images/b/b5/Reddit-The-Official-App-Icon.png/revision/latest?cb=20161004160133");
        post1.setPost_title("This is not concrete..this is New Delhi");

        PostModel post2 = new PostModel();
        post2.setPost_autor("IronMan");
        try {
            post2.setPost_create(df.parse("10/10/2016 22:12:30"));
        } catch(java.text.ParseException e) {
            System.out.println(e.getMessage());
        }
        post2.setPost_no_comments(864);
        post2.setPost_subreddit("/r/funny");
        post2.setPost_thumbnail("http://orig03.deviantart.net/88d4/f/2015/156/8/6/reddit_app_icon_by_sandiskplayer34-d8w5gj3.png");
        post2.setPost_title("When the beat drops, age doesn't matter.");

        PostModel post3 = new PostModel();
        post3.setPost_autor("SuperMan");
        try {
            post3.setPost_create(df.parse("22/09/2016 10:25:24"));
        } catch(java.text.ParseException e) {
            System.out.println(e.getMessage());
        }
        post3.setPost_no_comments(4777);
        post3.setPost_subreddit("/r/funny");
        post3.setPost_thumbnail("http://www.freeiconspng.com/uploads/red-circle-reddit-icon-1.png");
        post3.setPost_title("My hotel spent thousands on 25 pin iPhone docks just before Apple switched to lighting ports. A few months ago they finally retrofitted them all with these:");

        PostModel post4 = new PostModel();
        post4.setPost_autor("Batman");
        try {
            post4.setPost_create(df.parse("10/10/2016 23:27:30"));
        } catch(java.text.ParseException e) {
            System.out.println(e.getMessage());
        }
        post4.setPost_no_comments(2104);
        post4.setPost_subreddit("/r/todayilearned");
        post4.setPost_thumbnail("http://www.freeiconspng.com/uploads/reddit-social-icon-15.png");
        post4.setPost_title("TIL A 9,000 Year Old Skeleton Was Found in a Cave in Cheddar, England Known As Cheddar Man. When CM's DNA was tested a Living Relative Was Found Teaching School Half a Mile Away, Linking Back 300 Generations");

        PostModel post5 = new PostModel();
        post5.setPost_autor("IronMan");
        try {
            post5.setPost_create(df.parse("07/10/2016 14:12:30"));
        } catch(java.text.ParseException e) {
            System.out.println(e.getMessage());
        }
        post5.setPost_no_comments(1422);
        post5.setPost_subreddit("/r/gifs");
        post5.setPost_thumbnail("http://cdn.mysitemyway.com/etc-mysitemyway/icons/legacy-previews/icons-256/yellow-road-sign-icons-social-media-logos/102838-yellow-road-sign-icon-social-media-logos-reddit-logo.png");
        post5.setPost_title("Humpback whale breaching");

        ArrayList<PostModel> postModelLst = new ArrayList<>();
        postModelLst.add(post1);
        postModelLst.add(post2);
        postModelLst.add(post3);
        postModelLst.add(post4);
        postModelLst.add(post5);

        return postModelLst;
    }
}
