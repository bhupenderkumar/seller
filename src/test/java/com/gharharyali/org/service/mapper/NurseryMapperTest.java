package com.gharharyali.org.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class NurseryMapperTest {

    private NurseryMapper nurseryMapper;

    @BeforeEach
    public void setUp() {
        nurseryMapper = new NurseryMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(nurseryMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(nurseryMapper.fromId(null)).isNull();
    }
}
