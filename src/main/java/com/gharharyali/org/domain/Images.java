package com.gharharyali.org.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A Images.
 */
@Entity
@Table(name = "images")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "images")
public class Images implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    
    @Lob
    @Column(name = "image")
    private byte[] image;

    @Column(name = "image_content_type")
    private String imageContentType;

    @Lob
    @Column(name = "thumb_image")
    private byte[] thumbImage;

    @Column(name = "thumb_image_content_type")
    private String thumbImageContentType;

    @Size(max = 500)
    @Column(name = "alt", length = 500)
    private String alt;

    @Size(max = 500)
    @Column(name = "title", length = 500)
    private String title;

    @ManyToOne
    @JsonIgnoreProperties(value = "images", allowSetters = true)
    private Product product;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getImage() {
        return image;
    }

    public Images image(byte[] image) {
        this.image = image;
        return this;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public Images imageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
        return this;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public byte[] getThumbImage() {
        return thumbImage;
    }

    public Images thumbImage(byte[] thumbImage) {
        this.thumbImage = thumbImage;
        return this;
    }

    public void setThumbImage(byte[] thumbImage) {
        this.thumbImage = thumbImage;
    }

    public String getThumbImageContentType() {
        return thumbImageContentType;
    }

    public Images thumbImageContentType(String thumbImageContentType) {
        this.thumbImageContentType = thumbImageContentType;
        return this;
    }

    public void setThumbImageContentType(String thumbImageContentType) {
        this.thumbImageContentType = thumbImageContentType;
    }

    public String getAlt() {
        return alt;
    }

    public Images alt(String alt) {
        this.alt = alt;
        return this;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public String getTitle() {
        return title;
    }

    public Images title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Product getProduct() {
        return product;
    }

    public Images product(Product product) {
        this.product = product;
        return this;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Images)) {
            return false;
        }
        return id != null && id.equals(((Images) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Images{" +
            "id=" + getId() +
            ", image='" + getImage() + "'" +
            ", imageContentType='" + getImageContentType() + "'" +
            ", thumbImage='" + getThumbImage() + "'" +
            ", thumbImageContentType='" + getThumbImageContentType() + "'" +
            ", alt='" + getAlt() + "'" +
            ", title='" + getTitle() + "'" +
            "}";
    }
}
