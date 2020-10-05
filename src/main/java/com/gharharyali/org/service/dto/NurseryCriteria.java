package com.gharharyali.org.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import com.gharharyali.org.domain.enumeration.COUNTRY;
import com.gharharyali.org.domain.enumeration.PayMentMode;
import com.gharharyali.org.domain.enumeration.PayMentDuration;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the {@link com.gharharyali.org.domain.Nursery} entity. This class is used
 * in {@link com.gharharyali.org.web.rest.NurseryResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /nurseries?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class NurseryCriteria implements Serializable, Criteria {
    /**
     * Class for filtering COUNTRY
     */
    public static class COUNTRYFilter extends Filter<COUNTRY> {

        public COUNTRYFilter() {
        }

        public COUNTRYFilter(COUNTRYFilter filter) {
            super(filter);
        }

        @Override
        public COUNTRYFilter copy() {
            return new COUNTRYFilter(this);
        }

    }
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
    /**
     * Class for filtering PayMentDuration
     */
    public static class PayMentDurationFilter extends Filter<PayMentDuration> {

        public PayMentDurationFilter() {
        }

        public PayMentDurationFilter(PayMentDurationFilter filter) {
            super(filter);
        }

        @Override
        public PayMentDurationFilter copy() {
            return new PayMentDurationFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nurseryName;

    private StringFilter houseNo;

    private StringFilter salutation;

    private StringFilter firstName;

    private StringFilter lastName;

    private StringFilter middleName;

    private StringFilter streetNo;

    private StringFilter districtNo;

    private StringFilter city;

    private StringFilter state;

    private COUNTRYFilter country;

    private DoubleFilter latitude;

    private DoubleFilter longitude;

    private StringFilter addharNo;

    private StringFilter panCardNo;

    private PayMentModeFilter payMentMode;

    private StringFilter upiId;

    private PayMentDurationFilter payMentDuration;

    private StringFilter accountNo;

    private StringFilter bankIFSC;

    private StringFilter bankName;

    private LocalDateFilter createdDate;

    private LocalDateFilter updatedDate;

    private StringFilter userName;

    private LongFilter productId;

    private LongFilter transactionsId;

    public NurseryCriteria() {
    }

    public NurseryCriteria(NurseryCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.nurseryName = other.nurseryName == null ? null : other.nurseryName.copy();
        this.houseNo = other.houseNo == null ? null : other.houseNo.copy();
        this.salutation = other.salutation == null ? null : other.salutation.copy();
        this.firstName = other.firstName == null ? null : other.firstName.copy();
        this.lastName = other.lastName == null ? null : other.lastName.copy();
        this.middleName = other.middleName == null ? null : other.middleName.copy();
        this.streetNo = other.streetNo == null ? null : other.streetNo.copy();
        this.districtNo = other.districtNo == null ? null : other.districtNo.copy();
        this.city = other.city == null ? null : other.city.copy();
        this.state = other.state == null ? null : other.state.copy();
        this.country = other.country == null ? null : other.country.copy();
        this.latitude = other.latitude == null ? null : other.latitude.copy();
        this.longitude = other.longitude == null ? null : other.longitude.copy();
        this.addharNo = other.addharNo == null ? null : other.addharNo.copy();
        this.panCardNo = other.panCardNo == null ? null : other.panCardNo.copy();
        this.payMentMode = other.payMentMode == null ? null : other.payMentMode.copy();
        this.upiId = other.upiId == null ? null : other.upiId.copy();
        this.payMentDuration = other.payMentDuration == null ? null : other.payMentDuration.copy();
        this.accountNo = other.accountNo == null ? null : other.accountNo.copy();
        this.bankIFSC = other.bankIFSC == null ? null : other.bankIFSC.copy();
        this.bankName = other.bankName == null ? null : other.bankName.copy();
        this.createdDate = other.createdDate == null ? null : other.createdDate.copy();
        this.updatedDate = other.updatedDate == null ? null : other.updatedDate.copy();
        this.userName = other.userName == null ? null : other.userName.copy();
        this.productId = other.productId == null ? null : other.productId.copy();
        this.transactionsId = other.transactionsId == null ? null : other.transactionsId.copy();
    }

    @Override
    public NurseryCriteria copy() {
        return new NurseryCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getNurseryName() {
        return nurseryName;
    }

    public void setNurseryName(StringFilter nurseryName) {
        this.nurseryName = nurseryName;
    }

    public StringFilter getHouseNo() {
        return houseNo;
    }

    public void setHouseNo(StringFilter houseNo) {
        this.houseNo = houseNo;
    }

    public StringFilter getSalutation() {
        return salutation;
    }

    public void setSalutation(StringFilter salutation) {
        this.salutation = salutation;
    }

    public StringFilter getFirstName() {
        return firstName;
    }

    public void setFirstName(StringFilter firstName) {
        this.firstName = firstName;
    }

    public StringFilter getLastName() {
        return lastName;
    }

    public void setLastName(StringFilter lastName) {
        this.lastName = lastName;
    }

    public StringFilter getMiddleName() {
        return middleName;
    }

    public void setMiddleName(StringFilter middleName) {
        this.middleName = middleName;
    }

    public StringFilter getStreetNo() {
        return streetNo;
    }

    public void setStreetNo(StringFilter streetNo) {
        this.streetNo = streetNo;
    }

    public StringFilter getDistrictNo() {
        return districtNo;
    }

    public void setDistrictNo(StringFilter districtNo) {
        this.districtNo = districtNo;
    }

    public StringFilter getCity() {
        return city;
    }

    public void setCity(StringFilter city) {
        this.city = city;
    }

    public StringFilter getState() {
        return state;
    }

    public void setState(StringFilter state) {
        this.state = state;
    }

    public COUNTRYFilter getCountry() {
        return country;
    }

    public void setCountry(COUNTRYFilter country) {
        this.country = country;
    }

    public DoubleFilter getLatitude() {
        return latitude;
    }

    public void setLatitude(DoubleFilter latitude) {
        this.latitude = latitude;
    }

    public DoubleFilter getLongitude() {
        return longitude;
    }

    public void setLongitude(DoubleFilter longitude) {
        this.longitude = longitude;
    }

    public StringFilter getAddharNo() {
        return addharNo;
    }

    public void setAddharNo(StringFilter addharNo) {
        this.addharNo = addharNo;
    }

    public StringFilter getPanCardNo() {
        return panCardNo;
    }

    public void setPanCardNo(StringFilter panCardNo) {
        this.panCardNo = panCardNo;
    }

    public PayMentModeFilter getPayMentMode() {
        return payMentMode;
    }

    public void setPayMentMode(PayMentModeFilter payMentMode) {
        this.payMentMode = payMentMode;
    }

    public StringFilter getUpiId() {
        return upiId;
    }

    public void setUpiId(StringFilter upiId) {
        this.upiId = upiId;
    }

    public PayMentDurationFilter getPayMentDuration() {
        return payMentDuration;
    }

    public void setPayMentDuration(PayMentDurationFilter payMentDuration) {
        this.payMentDuration = payMentDuration;
    }

    public StringFilter getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(StringFilter accountNo) {
        this.accountNo = accountNo;
    }

    public StringFilter getBankIFSC() {
        return bankIFSC;
    }

    public void setBankIFSC(StringFilter bankIFSC) {
        this.bankIFSC = bankIFSC;
    }

    public StringFilter getBankName() {
        return bankName;
    }

    public void setBankName(StringFilter bankName) {
        this.bankName = bankName;
    }

    public LocalDateFilter getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateFilter createdDate) {
        this.createdDate = createdDate;
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

    public LongFilter getProductId() {
        return productId;
    }

    public void setProductId(LongFilter productId) {
        this.productId = productId;
    }

    public LongFilter getTransactionsId() {
        return transactionsId;
    }

    public void setTransactionsId(LongFilter transactionsId) {
        this.transactionsId = transactionsId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final NurseryCriteria that = (NurseryCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(nurseryName, that.nurseryName) &&
            Objects.equals(houseNo, that.houseNo) &&
            Objects.equals(salutation, that.salutation) &&
            Objects.equals(firstName, that.firstName) &&
            Objects.equals(lastName, that.lastName) &&
            Objects.equals(middleName, that.middleName) &&
            Objects.equals(streetNo, that.streetNo) &&
            Objects.equals(districtNo, that.districtNo) &&
            Objects.equals(city, that.city) &&
            Objects.equals(state, that.state) &&
            Objects.equals(country, that.country) &&
            Objects.equals(latitude, that.latitude) &&
            Objects.equals(longitude, that.longitude) &&
            Objects.equals(addharNo, that.addharNo) &&
            Objects.equals(panCardNo, that.panCardNo) &&
            Objects.equals(payMentMode, that.payMentMode) &&
            Objects.equals(upiId, that.upiId) &&
            Objects.equals(payMentDuration, that.payMentDuration) &&
            Objects.equals(accountNo, that.accountNo) &&
            Objects.equals(bankIFSC, that.bankIFSC) &&
            Objects.equals(bankName, that.bankName) &&
            Objects.equals(createdDate, that.createdDate) &&
            Objects.equals(updatedDate, that.updatedDate) &&
            Objects.equals(userName, that.userName) &&
            Objects.equals(productId, that.productId) &&
            Objects.equals(transactionsId, that.transactionsId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        nurseryName,
        houseNo,
        salutation,
        firstName,
        lastName,
        middleName,
        streetNo,
        districtNo,
        city,
        state,
        country,
        latitude,
        longitude,
        addharNo,
        panCardNo,
        payMentMode,
        upiId,
        payMentDuration,
        accountNo,
        bankIFSC,
        bankName,
        createdDate,
        updatedDate,
        userName,
        productId,
        transactionsId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NurseryCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (nurseryName != null ? "nurseryName=" + nurseryName + ", " : "") +
                (houseNo != null ? "houseNo=" + houseNo + ", " : "") +
                (salutation != null ? "salutation=" + salutation + ", " : "") +
                (firstName != null ? "firstName=" + firstName + ", " : "") +
                (lastName != null ? "lastName=" + lastName + ", " : "") +
                (middleName != null ? "middleName=" + middleName + ", " : "") +
                (streetNo != null ? "streetNo=" + streetNo + ", " : "") +
                (districtNo != null ? "districtNo=" + districtNo + ", " : "") +
                (city != null ? "city=" + city + ", " : "") +
                (state != null ? "state=" + state + ", " : "") +
                (country != null ? "country=" + country + ", " : "") +
                (latitude != null ? "latitude=" + latitude + ", " : "") +
                (longitude != null ? "longitude=" + longitude + ", " : "") +
                (addharNo != null ? "addharNo=" + addharNo + ", " : "") +
                (panCardNo != null ? "panCardNo=" + panCardNo + ", " : "") +
                (payMentMode != null ? "payMentMode=" + payMentMode + ", " : "") +
                (upiId != null ? "upiId=" + upiId + ", " : "") +
                (payMentDuration != null ? "payMentDuration=" + payMentDuration + ", " : "") +
                (accountNo != null ? "accountNo=" + accountNo + ", " : "") +
                (bankIFSC != null ? "bankIFSC=" + bankIFSC + ", " : "") +
                (bankName != null ? "bankName=" + bankName + ", " : "") +
                (createdDate != null ? "createdDate=" + createdDate + ", " : "") +
                (updatedDate != null ? "updatedDate=" + updatedDate + ", " : "") +
                (userName != null ? "userName=" + userName + ", " : "") +
                (productId != null ? "productId=" + productId + ", " : "") +
                (transactionsId != null ? "transactionsId=" + transactionsId + ", " : "") +
            "}";
    }

}
