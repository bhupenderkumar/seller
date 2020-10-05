package com.gharharyali.org.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.gharharyali.org.web.rest.TestUtil;

public class FeedBackDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FeedBackDTO.class);
        FeedBackDTO feedBackDTO1 = new FeedBackDTO();
        feedBackDTO1.setId(1L);
        FeedBackDTO feedBackDTO2 = new FeedBackDTO();
        assertThat(feedBackDTO1).isNotEqualTo(feedBackDTO2);
        feedBackDTO2.setId(feedBackDTO1.getId());
        assertThat(feedBackDTO1).isEqualTo(feedBackDTO2);
        feedBackDTO2.setId(2L);
        assertThat(feedBackDTO1).isNotEqualTo(feedBackDTO2);
        feedBackDTO1.setId(null);
        assertThat(feedBackDTO1).isNotEqualTo(feedBackDTO2);
    }
}
