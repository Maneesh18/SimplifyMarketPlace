package com.simplify.marketplace.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.simplify.marketplace.domain.enumeration.Category;
import com.simplify.marketplace.domain.enumeration.EngagementType;
import com.simplify.marketplace.domain.enumeration.LocationType;
import com.simplify.marketplace.domain.enumeration.SubCategory;
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
 * A JobPreference.
 */
@Document(collection = "job_preference")
public class JobPreference implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("category")
    private Category category;

    @NotNull
    @Field("sub_category")
    private SubCategory subCategory;

    @NotNull
    @Field("hourly_rate")
    private Integer hourlyRate;

    @NotNull
    @Field("daily_rate")
    private Integer dailyRate;

    @NotNull
    @Field("monthly_rate")
    private Integer monthlyRate;

    @NotNull
    @Field("hour_per_day")
    private Integer hourPerDay;

    @NotNull
    @Field("hour_per_week")
    private Integer hourPerWeek;

    @Field("engagement_type")
    private EngagementType engagementType;

    @Field("location_type")
    private LocationType locationType;

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
    @Field("preferredCity")
    @JsonIgnoreProperties(value = { "jobPreference" }, allowSetters = true)
    private Set<PreferredCity> preferredCities = new HashSet<>();

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

    public JobPreference id(String id) {
        this.id = id;
        return this;
    }

    public Category getCategory() {
        return this.category;
    }

    public JobPreference category(Category category) {
        this.category = category;
        return this;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public SubCategory getSubCategory() {
        return this.subCategory;
    }

    public JobPreference subCategory(SubCategory subCategory) {
        this.subCategory = subCategory;
        return this;
    }

    public void setSubCategory(SubCategory subCategory) {
        this.subCategory = subCategory;
    }

    public Integer getHourlyRate() {
        return this.hourlyRate;
    }

    public JobPreference hourlyRate(Integer hourlyRate) {
        this.hourlyRate = hourlyRate;
        return this;
    }

    public void setHourlyRate(Integer hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public Integer getDailyRate() {
        return this.dailyRate;
    }

    public JobPreference dailyRate(Integer dailyRate) {
        this.dailyRate = dailyRate;
        return this;
    }

    public void setDailyRate(Integer dailyRate) {
        this.dailyRate = dailyRate;
    }

    public Integer getMonthlyRate() {
        return this.monthlyRate;
    }

    public JobPreference monthlyRate(Integer monthlyRate) {
        this.monthlyRate = monthlyRate;
        return this;
    }

    public void setMonthlyRate(Integer monthlyRate) {
        this.monthlyRate = monthlyRate;
    }

    public Integer getHourPerDay() {
        return this.hourPerDay;
    }

    public JobPreference hourPerDay(Integer hourPerDay) {
        this.hourPerDay = hourPerDay;
        return this;
    }

    public void setHourPerDay(Integer hourPerDay) {
        this.hourPerDay = hourPerDay;
    }

    public Integer getHourPerWeek() {
        return this.hourPerWeek;
    }

    public JobPreference hourPerWeek(Integer hourPerWeek) {
        this.hourPerWeek = hourPerWeek;
        return this;
    }

    public void setHourPerWeek(Integer hourPerWeek) {
        this.hourPerWeek = hourPerWeek;
    }

    public EngagementType getEngagementType() {
        return this.engagementType;
    }

    public JobPreference engagementType(EngagementType engagementType) {
        this.engagementType = engagementType;
        return this;
    }

    public void setEngagementType(EngagementType engagementType) {
        this.engagementType = engagementType;
    }

    public LocationType getLocationType() {
        return this.locationType;
    }

    public JobPreference locationType(LocationType locationType) {
        this.locationType = locationType;
        return this;
    }

    public void setLocationType(LocationType locationType) {
        this.locationType = locationType;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public JobPreference createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getCreatedAt() {
        return this.createdAt;
    }

    public JobPreference createdAt(LocalDate createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public JobPreference updatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalDate getUpdatedAt() {
        return this.updatedAt;
    }

    public JobPreference updatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Set<PreferredCity> getPreferredCities() {
        return this.preferredCities;
    }

    public JobPreference preferredCities(Set<PreferredCity> preferredCities) {
        this.setPreferredCities(preferredCities);
        return this;
    }

    public JobPreference addPreferredCity(PreferredCity preferredCity) {
        this.preferredCities.add(preferredCity);
        preferredCity.setJobPreference(this);
        return this;
    }

    public JobPreference removePreferredCity(PreferredCity preferredCity) {
        this.preferredCities.remove(preferredCity);
        preferredCity.setJobPreference(null);
        return this;
    }

    public void setPreferredCities(Set<PreferredCity> preferredCities) {
        if (this.preferredCities != null) {
            this.preferredCities.forEach(i -> i.setJobPreference(null));
        }
        if (preferredCities != null) {
            preferredCities.forEach(i -> i.setJobPreference(this));
        }
        this.preferredCities = preferredCities;
    }

    public Worker getWorker() {
        return this.worker;
    }

    public JobPreference worker(Worker worker) {
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
        if (!(o instanceof JobPreference)) {
            return false;
        }
        return id != null && id.equals(((JobPreference) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "JobPreference{" +
            "id=" + getId() +
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
            "}";
    }
}
