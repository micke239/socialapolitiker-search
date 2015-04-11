package app.tweet;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogram;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogram.Interval;
import org.elasticsearch.search.aggregations.bucket.histogram.Histogram.Order;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

@Service
public class TweetSearcherImpl implements TweetSearcher {

    @Autowired
    ElasticsearchOperations elasticsearchOperations;

    @Override
    public List<TweetedWord> getTweetedWordForParty(String partyUrlName) {
        return tweetedWordsWithQuery(QueryBuilders.matchQuery("partyUrlName", partyUrlName));
    }

    @Override
    public List<TweetedWord> getTweetedWordForPolitician(String politician) {
        return tweetedWordsWithQuery(QueryBuilders.matchQuery("politicianTwitterScreenName", politician));
    }

    @Override
    public List<PopularWord> getPopularWordsForParty(String party) {
        return popularWordsWithQuery(QueryBuilders.matchQuery("partyUrlName", party));
    }

    @Override
    public List<PopularWord> getPopularWordsForPolitician(String politician) {
        return popularWordsWithQuery(QueryBuilders.matchQuery("politicianTwitterScreenName", politician));
    }

    private List<TweetedWord> tweetedWordsWithQuery(QueryBuilder queryBuilder) {
        NativeSearchQuery query = new NativeSearchQueryBuilder()
                .addAggregation(
                        AggregationBuilders.terms("words").field("tweetedWords").size(100)
                                .order(org.elasticsearch.search.aggregations.bucket.terms.Terms.Order.count(false))
                                .shardSize(0)).withQuery(queryBuilder).withIndices("tweet").build();

        return elasticsearchOperations.query(query, (response) -> {
            Terms terms = response.getAggregations().get("words");

            return terms.getBuckets().stream().map((bucket) -> {
                return new TweetedWord(bucket.getKey(), bucket.getDocCount());
            }).collect(Collectors.toList());
        });
    }

    private List<PopularWord> popularWordsWithQuery(QueryBuilder queryBuilder) {
        NativeSearchQuery query = new NativeSearchQueryBuilder()
                .addAggregation(
                        AggregationBuilders
                                .dateHistogram("popularWords")
                                .field("postedAt")
                                .interval(Interval.MONTH)
                                .order(Order.KEY_DESC)
                                .subAggregation(
                                        AggregationBuilders.terms("words").field("tweetedWords").size(1).shardSize(0)))
                .withQuery(queryBuilder).withIndices("tweet").build();

        return elasticsearchOperations.query(
                query,
                (response) -> {
                    DateHistogram dateHistogram = response.getAggregations().get("popularWords");

                    return dateHistogram
                            .getBuckets()
                            .stream()
                            .map((bucket) -> {
                                Terms terms = bucket.getAggregations().get("words");
                                LocalDate date = bucket.getKeyAsDate().toDate().toInstant()
                                        .atZone(ZoneId.systemDefault()).toLocalDate();
                                return new PopularWord(date, terms.getBuckets().iterator().next().getKey(), bucket
                                        .getDocCount());
                            }).collect(Collectors.toList());
                });
    }
}
