package com.gharharyali.org.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * The Enter the entity name entity.\n@author A true hipster
 */
@Entity
@Table(name = "feed_back")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "feedback")
public class FeedBack implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * fieldName
     */
    @Column(name = "rating")
    private Integer rating;

    @Column(name = "user_name")
    private String userName;

    @NotNull
    @Size(min = 1, max = 3999)
    @Column(name = "user_comments", length = 3999, nullable = false)
    private String userComments;

    @ManyToOne
    @JsonIgnoreProperties(value = "ratings", allowSetters = true)
    private Product product;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRating() {
        return rating;
    }

    public FeedBack rating(Integer rating) {
        this.rating = rating;
        return this;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getUserName() {
        return userName;
    }

    public FeedBack userName(String userName) {
        this.userName = userName;
        return this;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserComments() {
        return userComments;
    }

    public FeedBack userComments(String userComments) {
        this.userComments = userComments;
        return this;
    }

    public void setUserComments(String userComments) {
        this.userComments = userComments;
    }

    public Product getProduct() {
        return product;
    }

    public FeedBack product(Product product) {
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
        if (!(o instanceof FeedBack)) {
            return false;
        }
        return id != null && id.equals(((FeedBack) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FeedBack{" +
            "id=" + getId() +
            ", rating=" + getRating() +
            ", userName='" + getUserName() + "'" +
            ", userComments='" + getUserComments() + "'" +
            "}";
    }
}
