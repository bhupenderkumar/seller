package com.gharharyali.org.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import com.gharharyali.org.domain.enumeration.Approval;
import com.gharharyali.org.domain.enumeration.ProductType;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the {@link com.gharharyali.org.domain.Product} entity. This class is used
 * in {@link com.gharharyali.org.web.rest.ProductResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /products?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ProductCriteria implements Serializable, Criteria {
    /**
     * Class for filtering Approval
     */
    public static class ApprovalFilter extends Filter<Approval> {

        public ApprovalFilter() {
        }

        public ApprovalFilter(ApprovalFilter filter) {
            super(filter);
        }

        @Override
        public ApprovalFilter copy() {
            return new ApprovalFilter(this);
        }

    }
    /**
     * Class for filtering ProductType
     */
    public static class ProductTypeFilter extends Filter<ProductType> {

        public ProductTypeFilter() {
        }

        public ProductTypeFilter(ProductTypeFilter filter) {
            super(filter);
        }

        @Override
        public ProductTypeFilter copy() {
            return new ProductTypeFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter productName;

    private StringFilter productDescription;

    private DoubleFilter price;

    private ApprovalFilter approval;

    private BooleanFilter showStatus;

    private ProductTypeFilter productType;

    private LocalDateFilter productDate;

    private LocalDateFilter updatedDate;

    private StringFilter userName;

    private LongFilter imagesId;

    private LongFilter ratingId;

    private LongFilter nurseryId;

    public ProductCriteria() {
    }

    public ProductCriteria(ProductCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.productName = other.productName == null ? null : other.productName.copy();
        this.productDescription = other.productDescription == null ? null : other.productDescription.copy();
        this.price = other.price == null ? null : other.price.copy();
        this.approval = other.approval == null ? null : other.approval.copy();
        this.showStatus = other.showStatus == null ? null : other.showStatus.copy();
        this.productType = other.productType == null ? null : other.productType.copy();
        this.productDate = other.productDate == null ? null : other.productDate.copy();
        this.updatedDate = other.updatedDate == null ? null : other.updatedDate.copy();
        this.userName = other.userName == null ? null : other.userName.copy();
        this.imagesId = other.imagesId == null ? null : other.imagesId.copy();
        this.ratingId = other.ratingId == null ? null : other.ratingId.copy();
        this.nurseryId = other.nurseryId == null ? null : other.nurseryId.copy();
    }

    @Override
    public ProductCriteria copy() {
        return new ProductCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getProductName() {
        return productName;
    }

    public void setProductName(StringFilter productName) {
        this.productName = productName;
    }

    public StringFilter getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(StringFilter productDescription) {
        this.productDescription = productDescription;
    }

    public DoubleFilter getPrice() {
        return price;
    }

    public void setPrice(DoubleFilter price) {
        this.price = price;
    }

    public ApprovalFilter getApproval() {
        return approval;
    }

    public void setApproval(ApprovalFilter approval) {
        this.approval = approval;
    }

    public BooleanFilter getShowStatus() {
        return showStatus;
    }

    public void setShowStatus(BooleanFilter showStatus) {
        this.showStatus = showStatus;
    }

    public ProductTypeFilter getProductType() {
        return productType;
    }

    public void setProductType(ProductTypeFilter productType) {
        this.productType = productType;
    }

    public LocalDateFilter getProductDate() {
        return productDate;
    }

    public void setProductDate(LocalDateFilter productDate) {
        this.productDate = productDate;
    }

    public LocalDateFilter getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDateFilter updatedDate) {
        this.updatedDate = updatedDate;
    }

    public StringFilter getUserName() {
        return userName;
    }

    public void setUserName(StringFilter userName) {
        this.userName = userName;
    }

    public LongFilter getImagesId() {
        return imagesId;
    }

    public void setImagesId(LongFilter imagesId) {
        this.imagesId = imagesId;
    }

    public LongFilter getRatingId() {
        return ratingId;
    }

    public void setRatingId(LongFilter ratingId) {
        this.ratingId = ratingId;
    }

    public LongFilter getNurseryId() {
        return nurseryId;
    }

    public void setNurseryId(LongFilter nurseryId) {
        this.nurseryId = nurseryId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ProductCriteria that = (ProductCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(productName, that.productName) &&
            Objects.equals(productDescription, that.productDescription) &&
            Objects.equals(price, that.price) &&
            Objects.equals(approval, that.approval) &&
            Objects.equals(showStatus, that.showStatus) &&
            Objects.equals(productType, that.productType) &&
            Objects.equals(productDate, that.productDate) &&
            Objects.equals(updatedDate, that.updatedDate) &&
            Objects.equals(userName, that.userName) &&
            Objects.equals(imagesId, that.imagesId) &&
            Objects.equals(ratingId, that.ratingId) &&
            Objects.equals(nurseryId, that.nurseryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        productName,
        productDescription,
        price,
        approval,
        showStatus,
        productType,
        productDate,
        updatedDate,
        userName,
        imagesId,
        ratingId,
        nurseryId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (productName != null ? "productName=" + productName + ", " : "") +
                (productDescription != null ? "productDescription=" + productDescription + ", " : "") +
                (price != null ? "price=" + price + ", " : "") +
                (approval != null ? "approval=" + approval + ", " : "") +
                (showStatus != null ? "showStatus=" + showStatus + ", " : "") +
                (productType != null ? "productType=" + productType + ", " : "") +
                (productDate != null ? "productDate=" + productDate + ", " : "") +
                (updatedDate != null ? "updatedDate=" + updatedDate + ", " : "") +
                (userName != null ? "userName=" + userName + ", " : "") +
                (imagesId != null ? "imagesId=" + imagesId + ", " : "") +
                (ratingId != null ? "ratingId=" + ratingId + ", " : "") +
                (nurseryId != null ? "nurseryId=" + nurseryId + ", " : "") +
            "}";
    }

}
