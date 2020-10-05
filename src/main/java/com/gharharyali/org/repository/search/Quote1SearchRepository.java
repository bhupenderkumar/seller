package com.gharharyali.org.repository.search;

import com.gharharyali.org.domain.Quote1;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Quote1} entity.
 */
public interface Quote1SearchRepository extends ElasticsearchRepository<Quote1, Long> {
}
