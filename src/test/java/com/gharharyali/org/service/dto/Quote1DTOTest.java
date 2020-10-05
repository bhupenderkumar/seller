package com.gharharyali.org.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.gharharyali.org.web.rest.TestUtil;

public class Quote1DTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(Quote1DTO.class);
        Quote1DTO quote1DTO1 = new Quote1DTO();
        quote1DTO1.setId(1L);
        Quote1DTO quote1DTO2 = new Quote1DTO();
        assertThat(quote1DTO1).isNotEqualTo(quote1DTO2);
        quote1DTO2.setId(quote1DTO1.getId());
        assertThat(quote1DTO1).isEqualTo(quote1DTO2);
        quote1DTO2.setId(2L);
        assertThat(quote1DTO1).isNotEqualTo(quote1DTO2);
        quote1DTO1.setId(null);
        assertThat(quote1DTO1).isNotEqualTo(quote1DTO2);
    }
}
