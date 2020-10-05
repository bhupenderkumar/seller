package com.gharharyali.org.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link NurserySearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class NurserySearchRepositoryMockConfiguration {

    @MockBean
    private NurserySearchRepository mockNurserySearchRepository;

}
