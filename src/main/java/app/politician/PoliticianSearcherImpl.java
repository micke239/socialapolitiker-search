package app.politician;

import java.util.List;

import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

@Service
public class PoliticianSearcherImpl implements PoliticianSearcher {

    @Autowired
    ElasticsearchOperations elasticsearchOperations;

    @Override
    public List<PoliticianDocument> findPoliticiansByPartyUrlName(String partyUrlName) {
        NativeSearchQuery query = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchQuery("partyUrlName", partyUrlName))
                .withPageable(new PageRequest(0, Integer.MAX_VALUE))
                .withSort(SortBuilders.fieldSort("name").order(SortOrder.ASC)).build();
        return elasticsearchOperations.queryForList(query, PoliticianDocument.class);
    }

    @Override
    public List<PoliticianDocument> findPoliticians() {
        NativeSearchQuery query = new NativeSearchQueryBuilder().withQuery(QueryBuilders.matchAllQuery())
                .withPageable(new PageRequest(0, Integer.MAX_VALUE))
                .withSort(SortBuilders.fieldSort("name").order(SortOrder.ASC)).build();
        return elasticsearchOperations.queryForList(query, PoliticianDocument.class);
    }
}
