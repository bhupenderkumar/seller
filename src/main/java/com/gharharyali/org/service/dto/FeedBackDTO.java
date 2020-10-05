package com.gharharyali.org.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.gharharyali.org.domain.FeedBack} entity.
 */
@ApiModel(description = "The Enter the entity name entity.\n@author A true hipster")
public class FeedBackDTO implements Serializable {
    
    private Long id;

    /**
     * fieldName
     */
    @ApiModelProperty(value = "fieldName")
    private Integer rating;

    private String userName;

    @NotNull
    @Size(min = 1, max = 3999)
    private String userComments;


    private Long productId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserComments() {
        return userComments;
    }

    public void setUserComments(String userComments) {
        this.userComments = userComments;
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
        if (!(o instanceof FeedBackDTO)) {
            return false;
        }

        return id != null && id.equals(((FeedBackDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FeedBackDTO{" +
            "id=" + getId() +
            ", rating=" + getRating() +
            ", userName='" + getUserName() + "'" +
            ", userComments='" + getUserComments() + "'" +
            ", productId=" + getProductId() +
            "}";
    }
}
