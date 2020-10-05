package com.gharharyali.org.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import com.gharharyali.org.domain.enumeration.PayMentMode;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the {@link com.gharharyali.org.domain.Transaction} entity. This class is used
 * in {@link com.gharharyali.org.web.rest.TransactionResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /transactions?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class TransactionCriteria implements Serializable, Criteria {
    /**
     * Class for filtering PayMentMode
     */
    public static class PayMentModeFilter extends Filter<PayMentMode> {

        public PayMentModeFilter() {
        }

        public PayMentModeFilter(PayMentModeFilter filter) {
            super(filter);
        }

        @Override
        public PayMentModeFilter copy() {
            return new PayMentModeFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter userName;

    private DoubleFilter transactionAmount;

    private LocalDateFilter transactionDate;

    private PayMentModeFilter transactionMethod;

    private StringFilter processedBy;

    private LongFilter nurseryId;

    public TransactionCriteria() {
    }

    public TransactionCriteria(TransactionCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.userName = other.userName == null ? null : other.userName.copy();
        this.transactionAmount = other.transactionAmount == null ? null : other.transactionAmount.copy();
        this.transactionDate = other.transactionDate == null ? null : other.transactionDate.copy();
        this.transactionMethod = other.transactionMethod == null ? null : other.transactionMethod.copy();
        this.processedBy = other.processedBy == null ? null : other.processedBy.copy();
        this.nurseryId = other.nurseryId == null ? null : other.nurseryId.copy();
    }

    @Override
    public TransactionCriteria copy() {
        return new TransactionCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getUserName() {
        return userName;
    }

    public void setUserName(StringFilter userName) {
        this.userName = userName;
    }

    public DoubleFilter getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(DoubleFilter transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public LocalDateFilter getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateFilter transactionDate) {
        this.transactionDate = transactionDate;
    }

    public PayMentModeFilter getTransactionMethod() {
        return transactionMethod;
    }

    public void setTransactionMethod(PayMentModeFilter transactionMethod) {
        this.transactionMethod = transactionMethod;
    }

    public StringFilter getProcessedBy() {
        return processedBy;
    }

    public void setProcessedBy(StringFilter processedBy) {
        this.processedBy = processedBy;
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
        final TransactionCriteria that = (TransactionCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(userName, that.userName) &&
            Objects.equals(transactionAmount, that.transactionAmount) &&
            Objects.equals(transactionDate, that.transactionDate) &&
            Objects.equals(transactionMethod, that.transactionMethod) &&
            Objects.equals(processedBy, that.processedBy) &&
            Objects.equals(nurseryId, that.nurseryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        userName,
        transactionAmount,
        transactionDate,
        transactionMethod,
        processedBy,
        nurseryId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TransactionCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (userName != null ? "userName=" + userName + ", " : "") +
                (transactionAmount != null ? "transactionAmount=" + transactionAmount + ", " : "") +
                (transactionDate != null ? "transactionDate=" + transactionDate + ", " : "") +
                (transactionMethod != null ? "transactionMethod=" + transactionMethod + ", " : "") +
                (processedBy != null ? "processedBy=" + processedBy + ", " : "") +
                (nurseryId != null ? "nurseryId=" + nurseryId + ", " : "") +
            "}";
    }

}
