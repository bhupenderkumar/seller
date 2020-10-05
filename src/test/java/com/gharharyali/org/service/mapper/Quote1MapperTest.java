package com.gharharyali.org.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class Quote1MapperTest {

    private Quote1Mapper quote1Mapper;

    @BeforeEach
    public void setUp() {
        quote1Mapper = new Quote1MapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(quote1Mapper.fromId(id).getId()).isEqualTo(id);
        assertThat(quote1Mapper.fromId(null)).isNull();
    }
}
