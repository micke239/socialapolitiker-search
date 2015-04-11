package app.tweet;

import java.time.LocalDate;

public class PopularWord {
    private LocalDate date;
    private String word;
    private long count;

    public PopularWord() {
    }

    public PopularWord(LocalDate date, String word, long count) {
        this.date = date;
        this.word = word;
        this.count = count;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getWord() {
        return word;
    }

    public long getCount() {
        return count;
    }

}
