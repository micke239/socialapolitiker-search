package app.tweet;

public class TweetedWord implements Comparable<TweetedWord> {
    private String word;
    private long count;

    public TweetedWord(String word, long count) {
        this.word = word;
        this.count = count;
    }

    public String getWord() {
        return word;
    }

    public long getCount() {
        return count;
    }

    @Override
    public int compareTo(TweetedWord tweetedWord) {
        return (int) (tweetedWord.count - count);
    }

}
