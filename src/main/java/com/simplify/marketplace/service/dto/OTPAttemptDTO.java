package com.simplify.marketplace.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.simplify.marketplace.domain.OTPAttempt} entity.
 */
public class OTPAttemptDTO implements Serializable {

    private String id;

    @NotNull
    private Integer otpAttemptId;

    @NotNull
    private String email;

    @NotNull
    private String phone;

    @NotNull
    private String ip;

    @NotNull
    private String coookie;

    @NotNull
    private String createdBy;

    @NotNull
    private LocalDate createdAt;

    private OTPDTO otp;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getOtpAttemptId() {
        return otpAttemptId;
    }

    public void setOtpAttemptId(Integer otpAttemptId) {
        this.otpAttemptId = otpAttemptId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getCoookie() {
        return coookie;
    }

    public void setCoookie(String coookie) {
        this.coookie = coookie;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public OTPDTO getOtp() {
        return otp;
    }

    public void setOtp(OTPDTO otp) {
        this.otp = otp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OTPAttemptDTO)) {
            return false;
        }

        OTPAttemptDTO oTPAttemptDTO = (OTPAttemptDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, oTPAttemptDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OTPAttemptDTO{" +
            "id='" + getId() + "'" +
            ", otpAttemptId=" + getOtpAttemptId() +
            ", email='" + getEmail() + "'" +
            ", phone='" + getPhone() + "'" +
            ", ip='" + getIp() + "'" +
            ", coookie='" + getCoookie() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", otp=" + getOtp() +
            "}";
    }
}
