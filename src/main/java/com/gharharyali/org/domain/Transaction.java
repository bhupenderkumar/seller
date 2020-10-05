package com.gharharyali.org.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.LocalDate;

import com.gharharyali.org.domain.enumeration.PayMentMode;

/**
 * A Transaction.
 */
@Entity
@Table(name = "transaction")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "transaction")
public class Transaction implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "transaction_amount")
    private Double transactionAmount;

    @Column(name = "transaction_date")
    private LocalDate transactionDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_method")
    private PayMentMode transactionMethod;

    @Column(name = "processed_by")
    private String processedBy;

    @ManyToOne
    @JsonIgnoreProperties(value = "transactions", allowSetters = true)
    private Nursery nursery;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public Transaction userName(String userName) {
        this.userName = userName;
        return this;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Double getTransactionAmount() {
        return transactionAmount;
    }

    public Transaction transactionAmount(Double transactionAmount) {
        this.transactionAmount = transactionAmount;
        return this;
    }

    public void setTransactionAmount(Double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public Transaction transactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
        return this;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }

    public PayMentMode getTransactionMethod() {
        return transactionMethod;
    }

    public Transaction transactionMethod(PayMentMode transactionMethod) {
        this.transactionMethod = transactionMethod;
        return this;
    }

    public void setTransactionMethod(PayMentMode transactionMethod) {
        this.transactionMethod = transactionMethod;
    }

    public String getProcessedBy() {
        return processedBy;
    }

    public Transaction processedBy(String processedBy) {
        this.processedBy = processedBy;
        return this;
    }

    public void setProcessedBy(String processedBy) {
        this.processedBy = processedBy;
    }

    public Nursery getNursery() {
        return nursery;
    }

    public Transaction nursery(Nursery nursery) {
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
        if (!(o instanceof Transaction)) {
            return false;
        }
        return id != null && id.equals(((Transaction) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Transaction{" +
            "id=" + getId() +
            ", userName='" + getUserName() + "'" +
            ", transactionAmount=" + getTransactionAmount() +
            ", transactionDate='" + getTransactionDate() + "'" +
            ", transactionMethod='" + getTransactionMethod() + "'" +
            ", processedBy='" + getProcessedBy() + "'" +
            "}";
    }
}
