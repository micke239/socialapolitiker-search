package app.tweet.searcher;

import java.util.List;
import java.util.stream.Collectors;

import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import app.tweet.dto.TweetedEntity;

@Service
public class TweetedUserMentionSearcherImpl implements TweetedUserMentionSearcher {

    @Autowired
    ElasticsearchOperations elasticsearchOperations;

    @Override
    public List<TweetedEntity> getTweetedEntitysForPolitician(String politician) {
        return tweetedWordsWithQuery(QueryBuilders.matchQuery("politicianTwitterScreenName", politician));
    }

    @Override
    public List<TweetedEntity> getTweetedEntitysForParty(String partyUrlName) {
        return tweetedWordsWithQuery(QueryBuilders.matchQuery("partyUrlName", partyUrlName));
    }

    private List<TweetedEntity> tweetedWordsWithQuery(MatchQueryBuilder queryBuilder) {
        NativeSearchQuery query = new NativeSearchQueryBuilder()
                .addAggregation(
                        AggregationBuilders.terms("words").field("userMentions").size(20)
                                .order(org.elasticsearch.search.aggregations.bucket.terms.Terms.Order.count(false))
                                .shardSize(0)).withQuery(queryBuilder).withIndices("tweet").build();

        return elasticsearchOperations.query(query, (response) -> {
            Terms terms = response.getAggregations().get("words");

            return terms.getBuckets().stream().map((bucket) -> {
                return new TweetedEntity(bucket.getKey(), bucket.getDocCount());
            }).collect(Collectors.toList());
        });
    }

}
