package Objects;


import java.util.ArrayList;

public class Comment extends Tweet{

    public int fatherId;
    public boolean isCommentOfTweet;

    Comment(User user, String text, int fatherId, boolean isCommentOfTweet) {
        super(user, text);
        this.fatherId = fatherId;
        this.isCommentOfTweet = isCommentOfTweet;
    }

}