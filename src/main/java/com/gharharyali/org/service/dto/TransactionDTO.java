package com.gharharyali.org.service.dto;

import java.time.LocalDate;
import java.io.Serializable;
import com.gharharyali.org.domain.enumeration.PayMentMode;

/**
 * A DTO for the {@link com.gharharyali.org.domain.Transaction} entity.
 */
public class TransactionDTO implements Serializable {
    
    private Long id;

    private String userName;

    private Double transactionAmount;

    private LocalDate transactionDate;

    private PayMentMode transactionMethod;

    private String processedBy;


    private Long nurseryId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Double getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(Double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }

    public PayMentMode getTransactionMethod() {
        return transactionMethod;
    }

    public void setTransactionMethod(PayMentMode transactionMethod) {
        this.transactionMethod = transactionMethod;
    }

    public String getProcessedBy() {
        return processedBy;
    }

    public void setProcessedBy(String processedBy) {
        this.processedBy = processedBy;
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
        if (!(o instanceof TransactionDTO)) {
            return false;
        }

        return id != null && id.equals(((TransactionDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TransactionDTO{" +
            "id=" + getId() +
            ", userName='" + getUserName() + "'" +
            ", transactionAmount=" + getTransactionAmount() +
            ", transactionDate='" + getTransactionDate() + "'" +
            ", transactionMethod='" + getTransactionMethod() + "'" +
            ", processedBy='" + getProcessedBy() + "'" +
            ", nurseryId=" + getNurseryId() +
            "}";
    }
}
