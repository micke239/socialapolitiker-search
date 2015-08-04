package app.tweet.searcher;

import java.util.List;

import app.tweet.dto.TweetedEntity;

public interface TweetedUserMentionSearcher {
    List<TweetedEntity> getTweetedEntitysForPolitician(String politician);

    List<TweetedEntity> getTweetedEntitysForParty(String party);
}
