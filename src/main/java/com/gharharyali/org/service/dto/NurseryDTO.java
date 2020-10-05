package com.gharharyali.org.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import com.gharharyali.org.domain.enumeration.COUNTRY;
import com.gharharyali.org.domain.enumeration.PayMentMode;
import com.gharharyali.org.domain.enumeration.PayMentDuration;

/**
 * A DTO for the {@link com.gharharyali.org.domain.Nursery} entity.
 */
public class NurseryDTO implements Serializable {
    
    private Long id;

    private String nurseryName;

    private String houseNo;

    private String salutation;

    @NotNull
    private String firstName;

    private String lastName;

    private String middleName;

    private String streetNo;

    private String districtNo;

    private String city;

    private String state;

    private COUNTRY country;

    private Double latitude;

    private Double longitude;

    private String addharNo;

    private String panCardNo;

    private PayMentMode payMentMode;

    private String upiId;

    private PayMentDuration payMentDuration;

    private String accountNo;

    private String bankIFSC;

    private String bankName;

    private LocalDate createdDate;

    private LocalDate updatedDate;

    private String userName;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNurseryName() {
        return nurseryName;
    }

    public void setNurseryName(String nurseryName) {
        this.nurseryName = nurseryName;
    }

    public String getHouseNo() {
        return houseNo;
    }

    public void setHouseNo(String houseNo) {
        this.houseNo = houseNo;
    }

    public String getSalutation() {
        return salutation;
    }

    public void setSalutation(String salutation) {
        this.salutation = salutation;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getStreetNo() {
        return streetNo;
    }

    public void setStreetNo(String streetNo) {
        this.streetNo = streetNo;
    }

    public String getDistrictNo() {
        return districtNo;
    }

    public void setDistrictNo(String districtNo) {
        this.districtNo = districtNo;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public COUNTRY getCountry() {
        return country;
    }

    public void setCountry(COUNTRY country) {
        this.country = country;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getAddharNo() {
        return addharNo;
    }

    public void setAddharNo(String addharNo) {
        this.addharNo = addharNo;
    }

    public String getPanCardNo() {
        return panCardNo;
    }

    public void setPanCardNo(String panCardNo) {
        this.panCardNo = panCardNo;
    }

    public PayMentMode getPayMentMode() {
        return payMentMode;
    }

    public void setPayMentMode(PayMentMode payMentMode) {
        this.payMentMode = payMentMode;
    }

    public String getUpiId() {
        return upiId;
    }

    public void setUpiId(String upiId) {
        this.upiId = upiId;
    }

    public PayMentDuration getPayMentDuration() {
        return payMentDuration;
    }

    public void setPayMentDuration(PayMentDuration payMentDuration) {
        this.payMentDuration = payMentDuration;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getBankIFSC() {
        return bankIFSC;
    }

    public void setBankIFSC(String bankIFSC) {
        this.bankIFSC = bankIFSC;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NurseryDTO)) {
            return false;
        }

        return id != null && id.equals(((NurseryDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NurseryDTO{" +
            "id=" + getId() +
            ", nurseryName='" + getNurseryName() + "'" +
            ", houseNo='" + getHouseNo() + "'" +
            ", salutation='" + getSalutation() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", middleName='" + getMiddleName() + "'" +
            ", streetNo='" + getStreetNo() + "'" +
            ", districtNo='" + getDistrictNo() + "'" +
            ", city='" + getCity() + "'" +
            ", state='" + getState() + "'" +
            ", country='" + getCountry() + "'" +
            ", latitude=" + getLatitude() +
            ", longitude=" + getLongitude() +
            ", addharNo='" + getAddharNo() + "'" +
            ", panCardNo='" + getPanCardNo() + "'" +
            ", payMentMode='" + getPayMentMode() + "'" +
            ", upiId='" + getUpiId() + "'" +
            ", payMentDuration='" + getPayMentDuration() + "'" +
            ", accountNo='" + getAccountNo() + "'" +
            ", bankIFSC='" + getBankIFSC() + "'" +
            ", bankName='" + getBankName() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", updatedDate='" + getUpdatedDate() + "'" +
            ", userName='" + getUserName() + "'" +
            "}";
    }
}
