package app.tweet;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TweetSearchController {
    Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    ElasticsearchOperations elasticsearchOperations;

    @Autowired
    TweetSearcher tweetSearcher;

    @RequestMapping("/tweeted-words/party")
    public List<TweetedWord> tweetedWordsParty(@RequestParam String partyUrlName) {
        return tweetSearcher.getTweetedWordForParty(partyUrlName);
    }

    @RequestMapping("/tweeted-words/politician")
    public List<TweetedWord> tweetedWordsPolitician(@RequestParam String politician) {
        return tweetSearcher.getTweetedWordForPolitician(politician);
    }

    @RequestMapping("/popular-words/party")
    public List<PopularWord> popularWordsParty(@RequestParam String partyUrlName) {
        return tweetSearcher.getPopularWordsForParty(partyUrlName);
    }

    @RequestMapping("/popular-words/politician")
    public List<PopularWord> popularWordsPolitician(@RequestParam String politician) {
        return tweetSearcher.getPopularWordsForPolitician(politician);
    }

}
