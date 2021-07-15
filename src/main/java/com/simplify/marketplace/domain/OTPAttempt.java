package com.simplify.marketplace.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.validation.constraints.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A OTPAttempt.
 */
@Document(collection = "otp_attempt")
public class OTPAttempt implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("otp_attempt_id")
    private Integer otpAttemptId;

    @NotNull
    @Field("email")
    private String email;

    @NotNull
    @Field("phone")
    private String phone;

    @NotNull
    @Field("ip")
    private String ip;

    @NotNull
    @Field("coookie")
    private String coookie;

    @NotNull
    @Field("created_by")
    private String createdBy;

    @NotNull
    @Field("created_at")
    private LocalDate createdAt;

    @DBRef
    @Field("otp")
    @JsonIgnoreProperties(value = { "oTPAttempts", "worker" }, allowSetters = true)
    private OTP otp;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public OTPAttempt id(String id) {
        this.id = id;
        return this;
    }

    public Integer getOtpAttemptId() {
        return this.otpAttemptId;
    }

    public OTPAttempt otpAttemptId(Integer otpAttemptId) {
        this.otpAttemptId = otpAttemptId;
        return this;
    }

    public void setOtpAttemptId(Integer otpAttemptId) {
        this.otpAttemptId = otpAttemptId;
    }

    public String getEmail() {
        return this.email;
    }

    public OTPAttempt email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return this.phone;
    }

    public OTPAttempt phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIp() {
        return this.ip;
    }

    public OTPAttempt ip(String ip) {
        this.ip = ip;
        return this;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getCoookie() {
        return this.coookie;
    }

    public OTPAttempt coookie(String coookie) {
        this.coookie = coookie;
        return this;
    }

    public void setCoookie(String coookie) {
        this.coookie = coookie;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public OTPAttempt createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getCreatedAt() {
        return this.createdAt;
    }

    public OTPAttempt createdAt(LocalDate createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public OTP getOtp() {
        return this.otp;
    }

    public OTPAttempt otp(OTP oTP) {
        this.setOtp(oTP);
        return this;
    }

    public void setOtp(OTP oTP) {
        this.otp = oTP;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OTPAttempt)) {
            return false;
        }
        return id != null && id.equals(((OTPAttempt) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OTPAttempt{" +
            "id=" + getId() +
            ", otpAttemptId=" + getOtpAttemptId() +
            ", email='" + getEmail() + "'" +
            ", phone='" + getPhone() + "'" +
            ", ip='" + getIp() + "'" +
            ", coookie='" + getCoookie() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            "}";
    }
}
