package com.gharharyali.org.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.gharharyali.org.web.rest.TestUtil;

public class NurseryDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(NurseryDTO.class);
        NurseryDTO nurseryDTO1 = new NurseryDTO();
        nurseryDTO1.setId(1L);
        NurseryDTO nurseryDTO2 = new NurseryDTO();
        assertThat(nurseryDTO1).isNotEqualTo(nurseryDTO2);
        nurseryDTO2.setId(nurseryDTO1.getId());
        assertThat(nurseryDTO1).isEqualTo(nurseryDTO2);
        nurseryDTO2.setId(2L);
        assertThat(nurseryDTO1).isNotEqualTo(nurseryDTO2);
        nurseryDTO1.setId(null);
        assertThat(nurseryDTO1).isNotEqualTo(nurseryDTO2);
    }
}
