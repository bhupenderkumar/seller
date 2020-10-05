package com.gharharyali.org.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import javax.persistence.Lob;
import com.gharharyali.org.domain.enumeration.Approval;
import com.gharharyali.org.domain.enumeration.ProductType;

/**
 * A DTO for the {@link com.gharharyali.org.domain.Product} entity.
 */
public class ProductDTO implements Serializable {
    
    private Long id;

    @NotNull
    @Size(min = 5, max = 500)
    private String productName;

    @NotNull
    @Size(max = 3999)
    private String productDescription;

    @NotNull
    @DecimalMin(value = "10")
    @DecimalMax(value = "1000")
    private Double price;

    private Approval approval;

    private Boolean showStatus;

    private ProductType productType;

    @Lob
    private byte[] video;

    private String videoContentType;
    private LocalDate productDate;

    private LocalDate updatedDate;

    private String userName;


    private Long nurseryId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Approval getApproval() {
        return approval;
    }

    public void setApproval(Approval approval) {
        this.approval = approval;
    }

    public Boolean isShowStatus() {
        return showStatus;
    }

    public void setShowStatus(Boolean showStatus) {
        this.showStatus = showStatus;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public byte[] getVideo() {
        return video;
    }

    public void setVideo(byte[] video) {
        this.video = video;
    }

    public String getVideoContentType() {
        return videoContentType;
    }

    public void setVideoContentType(String videoContentType) {
        this.videoContentType = videoContentType;
    }

    public LocalDate getProductDate() {
        return productDate;
    }

    public void setProductDate(LocalDate productDate) {
        this.productDate = productDate;
    }

    public LocalDate getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDate updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getNurseryId() {
        return nurseryId;
    }

    public void setNurseryId(Long nurseryId) {
        this.nurseryId = nurseryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductDTO)) {
            return false;
        }

        return id != null && id.equals(((ProductDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductDTO{" +
            "id=" + getId() +
            ", productName='" + getProductName() + "'" +
            ", productDescription='" + getProductDescription() + "'" +
            ", price=" + getPrice() +
            ", approval='" + getApproval() + "'" +
            ", showStatus='" + isShowStatus() + "'" +
            ", productType='" + getProductType() + "'" +
            ", video='" + getVideo() + "'" +
            ", productDate='" + getProductDate() + "'" +
            ", updatedDate='" + getUpdatedDate() + "'" +
            ", userName='" + getUserName() + "'" +
            ", nurseryId=" + getNurseryId() +
            "}";
    }
}
