package com.gharharyali.org.repository.search;

import com.gharharyali.org.domain.FeedBack;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link FeedBack} entity.
 */
public interface FeedBackSearchRepository extends ElasticsearchRepository<FeedBack, Long> {
}
