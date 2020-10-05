package com.gharharyali.org.repository.search;

import com.gharharyali.org.domain.Nursery;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Nursery} entity.
 */
public interface NurserySearchRepository extends ElasticsearchRepository<Nursery, Long> {
}
