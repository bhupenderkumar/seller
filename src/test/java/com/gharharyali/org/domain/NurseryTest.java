package com.gharharyali.org.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.gharharyali.org.web.rest.TestUtil;

public class NurseryTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Nursery.class);
        Nursery nursery1 = new Nursery();
        nursery1.setId(1L);
        Nursery nursery2 = new Nursery();
        nursery2.setId(nursery1.getId());
        assertThat(nursery1).isEqualTo(nursery2);
        nursery2.setId(2L);
        assertThat(nursery1).isNotEqualTo(nursery2);
        nursery1.setId(null);
        assertThat(nursery1).isNotEqualTo(nursery2);
    }
}
