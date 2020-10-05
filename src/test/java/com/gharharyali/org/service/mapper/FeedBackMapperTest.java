package com.gharharyali.org.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class FeedBackMapperTest {

    private FeedBackMapper feedBackMapper;

    @BeforeEach
    public void setUp() {
        feedBackMapper = new FeedBackMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(feedBackMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(feedBackMapper.fromId(null)).isNull();
    }
}
