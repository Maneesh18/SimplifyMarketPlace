package com.simplify.marketplace.service.dto;

import com.simplify.marketplace.domain.enumeration.Category;
import com.simplify.marketplace.domain.enumeration.EngagementType;
import com.simplify.marketplace.domain.enumeration.LocationType;
import com.simplify.marketplace.domain.enumeration.SubCategory;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.simplify.marketplace.domain.JobPreference} entity.
 */
public class JobPreferenceDTO implements Serializable {

    private String id;

    @NotNull
    private Category category;

    @NotNull
    private SubCategory subCategory;

    @NotNull
    private Integer hourlyRate;

    @NotNull
    private Integer dailyRate;

    @NotNull
    private Integer monthlyRate;

    @NotNull
    private Integer hourPerDay;

    @NotNull
    private Integer hourPerWeek;

    private EngagementType engagementType;

    private LocationType locationType;

    @NotNull
    private String createdBy;

    @NotNull
    private LocalDate createdAt;

    @NotNull
    private String updatedBy;

    @NotNull
    private LocalDate updatedAt;

    private WorkerDTO worker;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public SubCategory getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(SubCategory subCategory) {
        this.subCategory = subCategory;
    }

    public Integer getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(Integer hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public Integer getDailyRate() {
        return dailyRate;
    }

    public void setDailyRate(Integer dailyRate) {
        this.dailyRate = dailyRate;
    }

    public Integer getMonthlyRate() {
        return monthlyRate;
    }

    public void setMonthlyRate(Integer monthlyRate) {
        this.monthlyRate = monthlyRate;
    }

    public Integer getHourPerDay() {
        return hourPerDay;
    }

    public void setHourPerDay(Integer hourPerDay) {
        this.hourPerDay = hourPerDay;
    }

    public Integer getHourPerWeek() {
        return hourPerWeek;
    }

    public void setHourPerWeek(Integer hourPerWeek) {
        this.hourPerWeek = hourPerWeek;
    }

    public EngagementType getEngagementType() {
        return engagementType;
    }

    public void setEngagementType(EngagementType engagementType) {
        this.engagementType = engagementType;
    }

    public LocationType getLocationType() {
        return locationType;
    }

    public void setLocationType(LocationType locationType) {
        this.locationType = locationType;
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

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }

    public WorkerDTO getWorker() {
        return worker;
    }

    public void setWorker(WorkerDTO worker) {
        this.worker = worker;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof JobPreferenceDTO)) {
            return false;
        }

        JobPreferenceDTO jobPreferenceDTO = (JobPreferenceDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, jobPreferenceDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "JobPreferenceDTO{" +
            "id='" + getId() + "'" +
            ", category='" + getCategory() + "'" +
            ", subCategory='" + getSubCategory() + "'" +
            ", hourlyRate=" + getHourlyRate() +
            ", dailyRate=" + getDailyRate() +
            ", monthlyRate=" + getMonthlyRate() +
            ", hourPerDay=" + getHourPerDay() +
            ", hourPerWeek=" + getHourPerWeek() +
            ", engagementType='" + getEngagementType() + "'" +
            ", locationType='" + getLocationType() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", worker=" + getWorker() +
            "}";
    }
}
