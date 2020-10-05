package com.gharharyali.org.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.gharharyali.org.web.rest.TestUtil;

public class ImagesDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ImagesDTO.class);
        ImagesDTO imagesDTO1 = new ImagesDTO();
        imagesDTO1.setId(1L);
        ImagesDTO imagesDTO2 = new ImagesDTO();
        assertThat(imagesDTO1).isNotEqualTo(imagesDTO2);
        imagesDTO2.setId(imagesDTO1.getId());
        assertThat(imagesDTO1).isEqualTo(imagesDTO2);
        imagesDTO2.setId(2L);
        assertThat(imagesDTO1).isNotEqualTo(imagesDTO2);
        imagesDTO1.setId(null);
        assertThat(imagesDTO1).isNotEqualTo(imagesDTO2);
    }
}
