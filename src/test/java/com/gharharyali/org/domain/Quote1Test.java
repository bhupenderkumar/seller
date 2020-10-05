package com.gharharyali.org.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.gharharyali.org.web.rest.TestUtil;

public class Quote1Test {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Quote1.class);
        Quote1 quote11 = new Quote1();
        quote11.setId(1L);
        Quote1 quote12 = new Quote1();
        quote12.setId(quote11.getId());
        assertThat(quote11).isEqualTo(quote12);
        quote12.setId(2L);
        assertThat(quote11).isNotEqualTo(quote12);
        quote11.setId(null);
        assertThat(quote11).isNotEqualTo(quote12);
    }
}
