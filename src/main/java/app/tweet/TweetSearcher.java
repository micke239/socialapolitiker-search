package app.tweet;

import java.util.List;

public interface TweetSearcher {

    List<TweetedWord> getTweetedWordForParty(String partyUrlName);

    List<TweetedWord> getTweetedWordForPolitician(String politician);

    List<PopularWord> getPopularWordsForParty(String party);

    List<PopularWord> getPopularWordsForPolitician(String politician);

}
