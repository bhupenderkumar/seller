package com.gharharyali.org.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.gharharyali.org.domain.enumeration.Approval;

import com.gharharyali.org.domain.enumeration.ProductType;

/**
 * A Product.
 */
@Entity
@Table(name = "product")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "product")
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 5, max = 500)
    @Column(name = "product_name", length = 500, nullable = false)
    private String productName;

    @NotNull
    @Size(max = 3999)
    @Column(name = "product_description", length = 3999, nullable = false)
    private String productDescription;

    @NotNull
    @DecimalMin(value = "10")
    @DecimalMax(value = "1000")
    @Column(name = "price", nullable = false)
    private Double price;

    @Enumerated(EnumType.STRING)
    @Column(name = "approval")
    private Approval approval;

    @Column(name = "show_status")
    private Boolean showStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "product_type")
    private ProductType productType;

    @Lob
    @Column(name = "video")
    private byte[] video;

    @Column(name = "video_content_type")
    private String videoContentType;

    @Column(name = "product_date")
    private LocalDate productDate;

    @Column(name = "updated_date")
    private LocalDate updatedDate;

    @Column(name = "user_name")
    private String userName;

    @OneToMany(mappedBy = "product")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Images> images = new HashSet<>();

    @OneToMany(mappedBy = "product")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<FeedBack> ratings = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "products", allowSetters = true)
    private Nursery nursery;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public Product productName(String productName) {
        this.productName = productName;
        return this;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public Product productDescription(String productDescription) {
        this.productDescription = productDescription;
        return this;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public Double getPrice() {
        return price;
    }

    public Product price(Double price) {
        this.price = price;
        return this;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Approval getApproval() {
        return approval;
    }

    public Product approval(Approval approval) {
        this.approval = approval;
        return this;
    }

    public void setApproval(Approval approval) {
        this.approval = approval;
    }

    public Boolean isShowStatus() {
        return showStatus;
    }

    public Product showStatus(Boolean showStatus) {
        this.showStatus = showStatus;
        return this;
    }

    public void setShowStatus(Boolean showStatus) {
        this.showStatus = showStatus;
    }

    public ProductType getProductType() {
        return productType;
    }

    public Product productType(ProductType productType) {
        this.productType = productType;
        return this;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public byte[] getVideo() {
        return video;
    }

    public Product video(byte[] video) {
        this.video = video;
        return this;
    }

    public void setVideo(byte[] video) {
        this.video = video;
    }

    public String getVideoContentType() {
        return videoContentType;
    }

    public Product videoContentType(String videoContentType) {
        this.videoContentType = videoContentType;
        return this;
    }

    public void setVideoContentType(String videoContentType) {
        this.videoContentType = videoContentType;
    }

    public LocalDate getProductDate() {
        return productDate;
    }

    public Product productDate(LocalDate productDate) {
        this.productDate = productDate;
        return this;
    }

    public void setProductDate(LocalDate productDate) {
        this.productDate = productDate;
    }

    public LocalDate getUpdatedDate() {
        return updatedDate;
    }

    public Product updatedDate(LocalDate updatedDate) {
        this.updatedDate = updatedDate;
        return this;
    }

    public void setUpdatedDate(LocalDate updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getUserName() {
        return userName;
    }

    public Product userName(String userName) {
        this.userName = userName;
        return this;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Set<Images> getImages() {
        return images;
    }

    public Product images(Set<Images> images) {
        this.images = images;
        return this;
    }

    public Product addImages(Images images) {
        this.images.add(images);
        images.setProduct(this);
        return this;
    }

    public Product removeImages(Images images) {
        this.images.remove(images);
        images.setProduct(null);
        return this;
    }

    public void setImages(Set<Images> images) {
        this.images = images;
    }

    public Set<FeedBack> getRatings() {
        return ratings;
    }

    public Product ratings(Set<FeedBack> feedBacks) {
        this.ratings = feedBacks;
        return this;
    }

    public Product addRating(FeedBack feedBack) {
        this.ratings.add(feedBack);
        feedBack.setProduct(this);
        return this;
    }

    public Product removeRating(FeedBack feedBack) {
        this.ratings.remove(feedBack);
        feedBack.setProduct(null);
        return this;
    }

    public void setRatings(Set<FeedBack> feedBacks) {
        this.ratings = feedBacks;
    }

    public Nursery getNursery() {
        return nursery;
    }

    public Product nursery(Nursery nursery) {
        this.nursery = nursery;
        return this;
    }

    public void setNursery(Nursery nursery) {
        this.nursery = nursery;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Product)) {
            return false;
        }
        return id != null && id.equals(((Product) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Product{" +
            "id=" + getId() +
            ", productName='" + getProductName() + "'" +
            ", productDescription='" + getProductDescription() + "'" +
            ", price=" + getPrice() +
            ", approval='" + getApproval() + "'" +
            ", showStatus='" + isShowStatus() + "'" +
            ", productType='" + getProductType() + "'" +
            ", video='" + getVideo() + "'" +
            ", videoContentType='" + getVideoContentType() + "'" +
            ", productDate='" + getProductDate() + "'" +
            ", updatedDate='" + getUpdatedDate() + "'" +
            ", userName='" + getUserName() + "'" +
            "}";
    }
}
