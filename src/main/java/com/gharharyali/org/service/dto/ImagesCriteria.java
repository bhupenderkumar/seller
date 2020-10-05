package com.gharharyali.org.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.gharharyali.org.domain.Images} entity. This class is used
 * in {@link com.gharharyali.org.web.rest.ImagesResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /images?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ImagesCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter alt;

    private StringFilter title;

    private LongFilter productId;

    public ImagesCriteria() {
    }

    public ImagesCriteria(ImagesCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.alt = other.alt == null ? null : other.alt.copy();
        this.title = other.title == null ? null : other.title.copy();
        this.productId = other.productId == null ? null : other.productId.copy();
    }

    @Override
    public ImagesCriteria copy() {
        return new ImagesCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getAlt() {
        return alt;
    }

    public void setAlt(StringFilter alt) {
        this.alt = alt;
    }

    public StringFilter getTitle() {
        return title;
    }

    public void setTitle(StringFilter title) {
        this.title = title;
    }

    public LongFilter getProductId() {
        return productId;
    }

    public void setProductId(LongFilter productId) {
        this.productId = productId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ImagesCriteria that = (ImagesCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(alt, that.alt) &&
            Objects.equals(title, that.title) &&
            Objects.equals(productId, that.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        alt,
        title,
        productId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ImagesCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (alt != null ? "alt=" + alt + ", " : "") +
                (title != null ? "title=" + title + ", " : "") +
                (productId != null ? "productId=" + productId + ", " : "") +
            "}";
    }

}
