package app.tweet.searcher;

import java.util.List;

import app.tweet.dto.TweetedEntity;

public interface TweetedWordSearcher {
    List<TweetedEntity> getTweetedWordForParty(String partyUrlName);

    List<TweetedEntity> getTweetedWordForPolitician(String politician);
}
