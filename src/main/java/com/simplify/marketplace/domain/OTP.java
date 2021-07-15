package com.simplify.marketplace.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.simplify.marketplace.domain.enumeration.OtpStatus;
import com.simplify.marketplace.domain.enumeration.OtpType;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.validation.constraints.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A OTP.
 */
@Document(collection = "otp")
public class OTP implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("otp_id")
    private Integer otpId;

    @NotNull
    @Field("email")
    private String email;

    @NotNull
    @Field("phone")
    private String phone;

    @Field("type")
    private OtpType type;

    @NotNull
    @Field("expiry_time")
    private LocalDate expiryTime;

    @Field("status")
    private OtpStatus status;

    @NotNull
    @Field("created_by")
    private String createdBy;

    @NotNull
    @Field("created_at")
    private LocalDate createdAt;

    @NotNull
    @Field("updated_by")
    private String updatedBy;

    @NotNull
    @Field("updated_at")
    private LocalDate updatedAt;

    @DBRef
    @Field("oTPAttempt")
    @JsonIgnoreProperties(value = { "otp" }, allowSetters = true)
    private Set<OTPAttempt> oTPAttempts = new HashSet<>();

    @DBRef
    @Field("worker")
    @JsonIgnoreProperties(value = { "educations", "employments", "jobPreferences", "addresses", "oTPS" }, allowSetters = true)
    private Worker worker;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public OTP id(String id) {
        this.id = id;
        return this;
    }

    public Integer getOtpId() {
        return this.otpId;
    }

    public OTP otpId(Integer otpId) {
        this.otpId = otpId;
        return this;
    }

    public void setOtpId(Integer otpId) {
        this.otpId = otpId;
    }

    public String getEmail() {
        return this.email;
    }

    public OTP email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return this.phone;
    }

    public OTP phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public OtpType getType() {
        return this.type;
    }

    public OTP type(OtpType type) {
        this.type = type;
        return this;
    }

    public void setType(OtpType type) {
        this.type = type;
    }

    public LocalDate getExpiryTime() {
        return this.expiryTime;
    }

    public OTP expiryTime(LocalDate expiryTime) {
        this.expiryTime = expiryTime;
        return this;
    }

    public void setExpiryTime(LocalDate expiryTime) {
        this.expiryTime = expiryTime;
    }

    public OtpStatus getStatus() {
        return this.status;
    }

    public OTP status(OtpStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(OtpStatus status) {
        this.status = status;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public OTP createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getCreatedAt() {
        return this.createdAt;
    }

    public OTP createdAt(LocalDate createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public OTP updatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalDate getUpdatedAt() {
        return this.updatedAt;
    }

    public OTP updatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Set<OTPAttempt> getOTPAttempts() {
        return this.oTPAttempts;
    }

    public OTP oTPAttempts(Set<OTPAttempt> oTPAttempts) {
        this.setOTPAttempts(oTPAttempts);
        return this;
    }

    public OTP addOTPAttempt(OTPAttempt oTPAttempt) {
        this.oTPAttempts.add(oTPAttempt);
        oTPAttempt.setOtp(this);
        return this;
    }

    public OTP removeOTPAttempt(OTPAttempt oTPAttempt) {
        this.oTPAttempts.remove(oTPAttempt);
        oTPAttempt.setOtp(null);
        return this;
    }

    public void setOTPAttempts(Set<OTPAttempt> oTPAttempts) {
        if (this.oTPAttempts != null) {
            this.oTPAttempts.forEach(i -> i.setOtp(null));
        }
        if (oTPAttempts != null) {
            oTPAttempts.forEach(i -> i.setOtp(this));
        }
        this.oTPAttempts = oTPAttempts;
    }

    public Worker getWorker() {
        return this.worker;
    }

    public OTP worker(Worker worker) {
        this.setWorker(worker);
        return this;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OTP)) {
            return false;
        }
        return id != null && id.equals(((OTP) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OTP{" +
            "id=" + getId() +
            ", otpId=" + getOtpId() +
            ", email='" + getEmail() + "'" +
            ", phone='" + getPhone() + "'" +
            ", type='" + getType() + "'" +
            ", expiryTime='" + getExpiryTime() + "'" +
            ", status='" + getStatus() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            "}";
    }
}
