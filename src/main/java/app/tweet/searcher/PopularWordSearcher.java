package app.tweet.searcher;

import java.util.List;

import app.tweet.dto.PopularWord;

public interface PopularWordSearcher {
    List<PopularWord> getPopularWordsForParty(String party);

    List<PopularWord> getPopularWordsForPolitician(String politician);
}
