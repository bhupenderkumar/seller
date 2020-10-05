package com.gharharyali.org.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.gharharyali.org.domain.enumeration.COUNTRY;

import com.gharharyali.org.domain.enumeration.PayMentMode;

import com.gharharyali.org.domain.enumeration.PayMentDuration;

/**
 * A Nursery.
 */
@Entity
@Table(name = "nursery")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "nursery")
public class Nursery implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nursery_name")
    private String nurseryName;

    @Column(name = "house_no")
    private String houseNo;

    @Column(name = "salutation")
    private String salutation;

    @NotNull
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "street_no")
    private String streetNo;

    @Column(name = "district_no")
    private String districtNo;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Enumerated(EnumType.STRING)
    @Column(name = "country")
    private COUNTRY country;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "addhar_no")
    private String addharNo;

    @Column(name = "pan_card_no")
    private String panCardNo;

    @Enumerated(EnumType.STRING)
    @Column(name = "pay_ment_mode")
    private PayMentMode payMentMode;

    @Column(name = "upi_id")
    private String upiId;

    @Enumerated(EnumType.STRING)
    @Column(name = "pay_ment_duration")
    private PayMentDuration payMentDuration;

    @Column(name = "account_no")
    private String accountNo;

    @Column(name = "bank_ifsc")
    private String bankIFSC;

    @Column(name = "bank_name")
    private String bankName;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @Column(name = "updated_date")
    private LocalDate updatedDate;

    @Column(name = "user_name")
    private String userName;

    @OneToMany(mappedBy = "nursery")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Product> products = new HashSet<>();

    @OneToMany(mappedBy = "nursery")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Transaction> transactions = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNurseryName() {
        return nurseryName;
    }

    public Nursery nurseryName(String nurseryName) {
        this.nurseryName = nurseryName;
        return this;
    }

    public void setNurseryName(String nurseryName) {
        this.nurseryName = nurseryName;
    }

    public String getHouseNo() {
        return houseNo;
    }

    public Nursery houseNo(String houseNo) {
        this.houseNo = houseNo;
        return this;
    }

    public void setHouseNo(String houseNo) {
        this.houseNo = houseNo;
    }

    public String getSalutation() {
        return salutation;
    }

    public Nursery salutation(String salutation) {
        this.salutation = salutation;
        return this;
    }

    public void setSalutation(String salutation) {
        this.salutation = salutation;
    }

    public String getFirstName() {
        return firstName;
    }

    public Nursery firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Nursery lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public Nursery middleName(String middleName) {
        this.middleName = middleName;
        return this;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getStreetNo() {
        return streetNo;
    }

    public Nursery streetNo(String streetNo) {
        this.streetNo = streetNo;
        return this;
    }

    public void setStreetNo(String streetNo) {
        this.streetNo = streetNo;
    }

    public String getDistrictNo() {
        return districtNo;
    }

    public Nursery districtNo(String districtNo) {
        this.districtNo = districtNo;
        return this;
    }

    public void setDistrictNo(String districtNo) {
        this.districtNo = districtNo;
    }

    public String getCity() {
        return city;
    }

    public Nursery city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public Nursery state(String state) {
        this.state = state;
        return this;
    }

    public void setState(String state) {
        this.state = state;
    }

    public COUNTRY getCountry() {
        return country;
    }

    public Nursery country(COUNTRY country) {
        this.country = country;
        return this;
    }

    public void setCountry(COUNTRY country) {
        this.country = country;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Nursery latitude(Double latitude) {
        this.latitude = latitude;
        return this;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Nursery longitude(Double longitude) {
        this.longitude = longitude;
        return this;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getAddharNo() {
        return addharNo;
    }

    public Nursery addharNo(String addharNo) {
        this.addharNo = addharNo;
        return this;
    }

    public void setAddharNo(String addharNo) {
        this.addharNo = addharNo;
    }

    public String getPanCardNo() {
        return panCardNo;
    }

    public Nursery panCardNo(String panCardNo) {
        this.panCardNo = panCardNo;
        return this;
    }

    public void setPanCardNo(String panCardNo) {
        this.panCardNo = panCardNo;
    }

    public PayMentMode getPayMentMode() {
        return payMentMode;
    }

    public Nursery payMentMode(PayMentMode payMentMode) {
        this.payMentMode = payMentMode;
        return this;
    }

    public void setPayMentMode(PayMentMode payMentMode) {
        this.payMentMode = payMentMode;
    }

    public String getUpiId() {
        return upiId;
    }

    public Nursery upiId(String upiId) {
        this.upiId = upiId;
        return this;
    }

    public void setUpiId(String upiId) {
        this.upiId = upiId;
    }

    public PayMentDuration getPayMentDuration() {
        return payMentDuration;
    }

    public Nursery payMentDuration(PayMentDuration payMentDuration) {
        this.payMentDuration = payMentDuration;
        return this;
    }

    public void setPayMentDuration(PayMentDuration payMentDuration) {
        this.payMentDuration = payMentDuration;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public Nursery accountNo(String accountNo) {
        this.accountNo = accountNo;
        return this;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getBankIFSC() {
        return bankIFSC;
    }

    public Nursery bankIFSC(String bankIFSC) {
        this.bankIFSC = bankIFSC;
        return this;
    }

    public void setBankIFSC(String bankIFSC) {
        this.bankIFSC = bankIFSC;
    }

    public String getBankName() {
        return bankName;
    }

    public Nursery bankName(String bankName) {
        this.bankName = bankName;
        return this;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public Nursery createdDate(LocalDate createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDate getUpdatedDate() {
        return updatedDate;
    }

    public Nursery updatedDate(LocalDate updatedDate) {
        this.updatedDate = updatedDate;
        return this;
    }

    public void setUpdatedDate(LocalDate updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getUserName() {
        return userName;
    }

    public Nursery userName(String userName) {
        this.userName = userName;
        return this;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public Nursery products(Set<Product> products) {
        this.products = products;
        return this;
    }

    public Nursery addProduct(Product product) {
        this.products.add(product);
        product.setNursery(this);
        return this;
    }

    public Nursery removeProduct(Product product) {
        this.products.remove(product);
        product.setNursery(null);
        return this;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    public Set<Transaction> getTransactions() {
        return transactions;
    }

    public Nursery transactions(Set<Transaction> transactions) {
        this.transactions = transactions;
        return this;
    }

    public Nursery addTransactions(Transaction transaction) {
        this.transactions.add(transaction);
        transaction.setNursery(this);
        return this;
    }

    public Nursery removeTransactions(Transaction transaction) {
        this.transactions.remove(transaction);
        transaction.setNursery(null);
        return this;
    }

    public void setTransactions(Set<Transaction> transactions) {
        this.transactions = transactions;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Nursery)) {
            return false;
        }
        return id != null && id.equals(((Nursery) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Nursery{" +
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
