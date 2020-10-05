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
 * Criteria class for the {@link com.gharharyali.org.domain.FeedBack} entity. This class is used
 * in {@link com.gharharyali.org.web.rest.FeedBackResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /feed-backs?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class FeedBackCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter rating;

    private StringFilter userName;

    private StringFilter userComments;

    private LongFilter productId;

    public FeedBackCriteria() {
    }

    public FeedBackCriteria(FeedBackCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.rating = other.rating == null ? null : other.rating.copy();
        this.userName = other.userName == null ? null : other.userName.copy();
        this.userComments = other.userComments == null ? null : other.userComments.copy();
        this.productId = other.productId == null ? null : other.productId.copy();
    }

    @Override
    public FeedBackCriteria copy() {
        return new FeedBackCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public IntegerFilter getRating() {
        return rating;
    }

    public void setRating(IntegerFilter rating) {
        this.rating = rating;
    }

    public StringFilter getUserName() {
        return userName;
    }

    public void setUserName(StringFilter userName) {
        this.userName = userName;
    }

    public StringFilter getUserComments() {
        return userComments;
    }

    public void setUserComments(StringFilter userComments) {
        this.userComments = userComments;
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
        final FeedBackCriteria that = (FeedBackCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(rating, that.rating) &&
            Objects.equals(userName, that.userName) &&
            Objects.equals(userComments, that.userComments) &&
            Objects.equals(productId, that.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        rating,
        userName,
        userComments,
        productId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FeedBackCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (rating != null ? "rating=" + rating + ", " : "") +
                (userName != null ? "userName=" + userName + ", " : "") +
                (userComments != null ? "userComments=" + userComments + ", " : "") +
                (productId != null ? "productId=" + productId + ", " : "") +
            "}";
    }

}
