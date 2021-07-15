package com.simplify.marketplace.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
 * A Worker.
 */
@Document(collection = "worker")
public class Worker implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("name")
    private String name;

    @NotNull
    @Field("email")
    private String email;

    @NotNull
    @Field("phone")
    private String phone;

    @Field("description")
    private String description;

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
    @Field("education")
    @JsonIgnoreProperties(value = { "worker" }, allowSetters = true)
    private Set<Education> educations = new HashSet<>();

    @DBRef
    @Field("employment")
    @JsonIgnoreProperties(value = { "worker" }, allowSetters = true)
    private Set<Employment> employments = new HashSet<>();

    @DBRef
    @Field("jobPreference")
    @JsonIgnoreProperties(value = { "preferredCities", "worker" }, allowSetters = true)
    private Set<JobPreference> jobPreferences = new HashSet<>();

    @DBRef
    @Field("address")
    @JsonIgnoreProperties(value = { "worker" }, allowSetters = true)
    private Set<Address> addresses = new HashSet<>();

    @DBRef
    @Field("oTP")
    @JsonIgnoreProperties(value = { "oTPAttempts", "worker" }, allowSetters = true)
    private Set<OTP> oTPS = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Worker id(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public Worker name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return this.email;
    }

    public Worker email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return this.phone;
    }

    public Worker phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDescription() {
        return this.description;
    }

    public Worker description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public Worker createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getCreatedAt() {
        return this.createdAt;
    }

    public Worker createdAt(LocalDate createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public Worker updatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalDate getUpdatedAt() {
        return this.updatedAt;
    }

    public Worker updatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Set<Education> getEducations() {
        return this.educations;
    }

    public Worker educations(Set<Education> educations) {
        this.setEducations(educations);
        return this;
    }

    public Worker addEducation(Education education) {
        this.educations.add(education);
        education.setWorker(this);
        return this;
    }

    public Worker removeEducation(Education education) {
        this.educations.remove(education);
        education.setWorker(null);
        return this;
    }

    public void setEducations(Set<Education> educations) {
        if (this.educations != null) {
            this.educations.forEach(i -> i.setWorker(null));
        }
        if (educations != null) {
            educations.forEach(i -> i.setWorker(this));
        }
        this.educations = educations;
    }

    public Set<Employment> getEmployments() {
        return this.employments;
    }

    public Worker employments(Set<Employment> employments) {
        this.setEmployments(employments);
        return this;
    }

    public Worker addEmployment(Employment employment) {
        this.employments.add(employment);
        employment.setWorker(this);
        return this;
    }

    public Worker removeEmployment(Employment employment) {
        this.employments.remove(employment);
        employment.setWorker(null);
        return this;
    }

    public void setEmployments(Set<Employment> employments) {
        if (this.employments != null) {
            this.employments.forEach(i -> i.setWorker(null));
        }
        if (employments != null) {
            employments.forEach(i -> i.setWorker(this));
        }
        this.employments = employments;
    }

    public Set<JobPreference> getJobPreferences() {
        return this.jobPreferences;
    }

    public Worker jobPreferences(Set<JobPreference> jobPreferences) {
        this.setJobPreferences(jobPreferences);
        return this;
    }

    public Worker addJobPreference(JobPreference jobPreference) {
        this.jobPreferences.add(jobPreference);
        jobPreference.setWorker(this);
        return this;
    }

    public Worker removeJobPreference(JobPreference jobPreference) {
        this.jobPreferences.remove(jobPreference);
        jobPreference.setWorker(null);
        return this;
    }

    public void setJobPreferences(Set<JobPreference> jobPreferences) {
        if (this.jobPreferences != null) {
            this.jobPreferences.forEach(i -> i.setWorker(null));
        }
        if (jobPreferences != null) {
            jobPreferences.forEach(i -> i.setWorker(this));
        }
        this.jobPreferences = jobPreferences;
    }

    public Set<Address> getAddresses() {
        return this.addresses;
    }

    public Worker addresses(Set<Address> addresses) {
        this.setAddresses(addresses);
        return this;
    }

    public Worker addAddress(Address address) {
        this.addresses.add(address);
        address.setWorker(this);
        return this;
    }

    public Worker removeAddress(Address address) {
        this.addresses.remove(address);
        address.setWorker(null);
        return this;
    }

    public void setAddresses(Set<Address> addresses) {
        if (this.addresses != null) {
            this.addresses.forEach(i -> i.setWorker(null));
        }
        if (addresses != null) {
            addresses.forEach(i -> i.setWorker(this));
        }
        this.addresses = addresses;
    }

    public Set<OTP> getOTPS() {
        return this.oTPS;
    }

    public Worker oTPS(Set<OTP> oTPS) {
        this.setOTPS(oTPS);
        return this;
    }

    public Worker addOTP(OTP oTP) {
        this.oTPS.add(oTP);
        oTP.setWorker(this);
        return this;
    }

    public Worker removeOTP(OTP oTP) {
        this.oTPS.remove(oTP);
        oTP.setWorker(null);
        return this;
    }

    public void setOTPS(Set<OTP> oTPS) {
        if (this.oTPS != null) {
            this.oTPS.forEach(i -> i.setWorker(null));
        }
        if (oTPS != null) {
            oTPS.forEach(i -> i.setWorker(this));
        }
        this.oTPS = oTPS;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Worker)) {
            return false;
        }
        return id != null && id.equals(((Worker) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Worker{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", email='" + getEmail() + "'" +
            ", phone='" + getPhone() + "'" +
            ", description='" + getDescription() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            "}";
    }
}
