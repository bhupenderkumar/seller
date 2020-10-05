package com.gharharyali.org.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link Quote1SearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class Quote1SearchRepositoryMockConfiguration {

    @MockBean
    private Quote1SearchRepository mockQuote1SearchRepository;

}
