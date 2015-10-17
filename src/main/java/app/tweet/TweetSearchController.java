package app.tweet;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import app.tweet.dto.PopularWord;
import app.tweet.dto.TweetedEntity;
import app.tweet.searcher.PopularWordSearcher;
import app.tweet.searcher.TweetedDomainSearcher;
import app.tweet.searcher.TweetedHashtagSearcher;
import app.tweet.searcher.TweetedUserMentionSearcher;
import app.tweet.searcher.TweetedWordSearcher;

@RestController
public class TweetSearchController {
    Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private TweetedWordSearcher tweetWordSearcher;

    @Autowired
    private PopularWordSearcher popularWordSearcher;

    @Autowired
    private TweetedDomainSearcher tweetedDomainSearcher;

    @Autowired
    private TweetedHashtagSearcher tweetedHashtagSearcher;

    @Autowired
    private TweetedUserMentionSearcher tweetedUserMentionSearcher;

    @RequestMapping("/tweeted-words/party")
    public List<TweetedEntity> tweetedWordsParty(@RequestParam String partyUrlName) {
        return tweetWordSearcher.getTweetedWordForParty(partyUrlName);
    }

    @RequestMapping("/tweeted-words/politician")
    public List<TweetedEntity> tweetedWordsPolitician(@RequestParam String politician) {
        return tweetWordSearcher.getTweetedWordForPolitician(politician);
    }

    @RequestMapping("/popular-words/party")
    public List<PopularWord> popularWordsParty(@RequestParam String partyUrlName) {
        return popularWordSearcher.getPopularWordsForParty(partyUrlName);
    }

    @RequestMapping("/popular-words/politician")
    public List<PopularWord> popularWordsPolitician(@RequestParam String politician) {
        return popularWordSearcher.getPopularWordsForPolitician(politician);
    }

    @RequestMapping("/tweeted-domains/politician")
    public List<TweetedEntity> tweetedDomainsPolitician(@RequestParam String politician) {
        return tweetedDomainSearcher.getTweetedEntitysForPolitician(politician);
    }

    @RequestMapping("/tweeted-domains/party")
    public List<TweetedEntity> tweetedDomainsParty(@RequestParam String partyUrlName) {
        return tweetedDomainSearcher.getTweetedEntitysForParty(partyUrlName);
    }

    @RequestMapping("/tweeted-hashtags/politician")
    public List<TweetedEntity> tweetedHashtagsPolitician(@RequestParam String politician) {
        return tweetedHashtagSearcher.getTweetedEntitysForPolitician(politician);
    }

    @RequestMapping("/tweeted-hashtags/party")
    public List<TweetedEntity> tweetedHashtagsParty(@RequestParam String partyUrlName) {
        return tweetedHashtagSearcher.getTweetedEntitysForParty(partyUrlName);
    }

    @RequestMapping("/tweeted-user-mentions/politician")
    public List<TweetedEntity> tweetedUserMentionsPolitician(@RequestParam String politician) {
        return tweetedUserMentionSearcher.getTweetedEntitysForPolitician(politician);
    }

    @RequestMapping("/tweeted-user-mentions/party")
    public List<TweetedEntity> tweetedUserMentionsParty(@RequestParam String partyUrlName) {
        return tweetedUserMentionSearcher.getTweetedEntitysForParty(partyUrlName);
    }

}
