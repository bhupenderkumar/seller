package com.gharharyali.org.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.gharharyali.org.web.rest.TestUtil;

public class FeedBackTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FeedBack.class);
        FeedBack feedBack1 = new FeedBack();
        feedBack1.setId(1L);
        FeedBack feedBack2 = new FeedBack();
        feedBack2.setId(feedBack1.getId());
        assertThat(feedBack1).isEqualTo(feedBack2);
        feedBack2.setId(2L);
        assertThat(feedBack1).isNotEqualTo(feedBack2);
        feedBack1.setId(null);
        assertThat(feedBack1).isNotEqualTo(feedBack2);
    }
}
