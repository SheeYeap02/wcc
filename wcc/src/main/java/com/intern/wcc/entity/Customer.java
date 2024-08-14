package com.intern.wcc.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
public class Customer implements Serializable {
    @Id
    @Column(name = "user_id", nullable = false, updatable = false)
    private String userId;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "nationality", nullable = false)
    private String nationality;

    @Column(name = "ic_type")
    private String icType;

    @Column(name = "ic_no", unique = true)
    private String icNo;

    @Column(name = "passport")
    private String passport;

    @Column(name = "issuing_country")
    private String issuingCountry;

    @Column(name = "expiry_date")
    private LocalDateTime expiryDate;

    @Column(name = "salutation", nullable = false)
    private String salutation;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "dialing_code", nullable = false)
    private String dialingCode;

    @Column(name = "phone_no", nullable = false)
    private String phoneNo;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "ic_front")
    private byte[] icFront;

    @Column(name = "ic_back")
    private byte[] icBack;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "registered_date", nullable = false)
    private LocalDateTime registeredDate;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getIcType() {
        return icType;
    }

    public void setIcType(String icType) {
        this.icType = icType;
    }

    public String getIcNo() {
        return icNo;
    }

    public void setIcNo(String icNo) {
        this.icNo = icNo;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public String getIssuingCountry() {
        return issuingCountry;
    }

    public void setIssuingCountry(String issuingCountry) {
        this.issuingCountry = issuingCountry;
    }

    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getSalutation() {
        return salutation;
    }

    public void setSalutation(String salutation) {
        this.salutation = salutation;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDialingCode() {
        return dialingCode;
    }

    public void setDialingCode(String dialingCode) {
        this.dialingCode = dialingCode;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public byte[] getIcFront() {
        return icFront;
    }

    public void setIcFront(byte[] icFront) {
        this.icFront = icFront;
    }

    public byte[] getIcBack() {
        return icBack;
    }

    public void setIcBack(byte[] icBack) {
        this.icBack = icBack;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getRegisteredDate() {
        return registeredDate;
    }

    public void setRegisteredDate(LocalDateTime registeredDate) {
        this.registeredDate = registeredDate;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "userId='" + userId + '\'' +
                ", password='" + password + '\'' +
                ", nationality='" + nationality + '\'' +
                ", icType='" + icType + '\'' +
                ", icNo='" + icNo + '\'' +
                ", passport='" + passport + '\'' +
                ", issuingCountry='" + issuingCountry + '\'' +
                ", expiryDate=" + expiryDate +
                ", salutation='" + salutation + '\'' +
                ", fullName='" + fullName + '\'' +
                ", dialingCode='" + dialingCode + '\'' +
                ", phoneNo='" + phoneNo + '\'' +
                ", email='" + email + '\'' +
                ", status='" + status + '\'' +
                ", registeredDate=" + registeredDate +
                '}';
    }
}
