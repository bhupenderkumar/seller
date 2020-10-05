package com.gharharyali.org.repository.search;

import com.gharharyali.org.domain.Images;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Images} entity.
 */
public interface ImagesSearchRepository extends ElasticsearchRepository<Images, Long> {
}
