package app.tweet.dto;

public class TweetedEntity implements Comparable<TweetedEntity> {
    private String entity;
    private long count;

    public TweetedEntity(String word, long count) {
        this.entity = word;
        this.count = count;
    }

    public String getEntity() {
        return entity;
    }

    public long getCount() {
        return count;
    }

    @Override
    public int compareTo(TweetedEntity tweetedWord) {
        return (int) (tweetedWord.count - count);
    }

}
