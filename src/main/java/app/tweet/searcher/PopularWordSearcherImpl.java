package app.tweet.searcher;

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

import app.tweet.dto.PopularWord;

@Service
public class PopularWordSearcherImpl implements PopularWordSearcher {

    @Autowired
    private ElasticsearchOperations elasticsearchOperations;

    @Override
    public List<PopularWord> getPopularWordsForParty(String party) {
        return popularWordsWithQuery(QueryBuilders.matchQuery("partyUrlName", party));
    }

    @Override
    public List<PopularWord> getPopularWordsForPolitician(String politician) {
        return popularWordsWithQuery(QueryBuilders.matchQuery("politicianTwitterScreenName", politician));
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
