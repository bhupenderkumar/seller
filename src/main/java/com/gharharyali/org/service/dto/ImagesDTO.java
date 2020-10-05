package com.gharharyali.org.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.gharharyali.org.domain.Images} entity.
 */
public class ImagesDTO implements Serializable {
    
    private Long id;

    
    @Lob
    private byte[] image;

    private String imageContentType;
    @Lob
    private byte[] thumbImage;

    private String thumbImageContentType;
    @Size(max = 500)
    private String alt;

    @Size(max = 500)
    private String title;


    private Long productId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public byte[] getThumbImage() {
        return thumbImage;
    }

    public void setThumbImage(byte[] thumbImage) {
        this.thumbImage = thumbImage;
    }

    public String getThumbImageContentType() {
        return thumbImageContentType;
    }

    public void setThumbImageContentType(String thumbImageContentType) {
        this.thumbImageContentType = thumbImageContentType;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ImagesDTO)) {
            return false;
        }

        return id != null && id.equals(((ImagesDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ImagesDTO{" +
            "id=" + getId() +
            ", image='" + getImage() + "'" +
            ", thumbImage='" + getThumbImage() + "'" +
            ", alt='" + getAlt() + "'" +
            ", title='" + getTitle() + "'" +
            ", productId=" + getProductId() +
            "}";
    }
}
