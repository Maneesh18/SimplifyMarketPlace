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
 * A Education.
 */
@Document(collection = "education")
public class Education implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("degree_name")
    private String degreeName;

    @NotNull
    @Field("institute")
    private String institute;

    @NotNull
    @Field("year_of_passing")
    private Integer yearOfPassing;

    @NotNull
    @Field("marks")
    private Float marks;

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

    public Education id(String id) {
        this.id = id;
        return this;
    }

    public String getDegreeName() {
        return this.degreeName;
    }

    public Education degreeName(String degreeName) {
        this.degreeName = degreeName;
        return this;
    }

    public void setDegreeName(String degreeName) {
        this.degreeName = degreeName;
    }

    public String getInstitute() {
        return this.institute;
    }

    public Education institute(String institute) {
        this.institute = institute;
        return this;
    }

    public void setInstitute(String institute) {
        this.institute = institute;
    }

    public Integer getYearOfPassing() {
        return this.yearOfPassing;
    }

    public Education yearOfPassing(Integer yearOfPassing) {
        this.yearOfPassing = yearOfPassing;
        return this;
    }

    public void setYearOfPassing(Integer yearOfPassing) {
        this.yearOfPassing = yearOfPassing;
    }

    public Float getMarks() {
        return this.marks;
    }

    public Education marks(Float marks) {
        this.marks = marks;
        return this;
    }

    public void setMarks(Float marks) {
        this.marks = marks;
    }

    public String getDescription() {
        return this.description;
    }

    public Education description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public Education createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getCreatedAt() {
        return this.createdAt;
    }

    public Education createdAt(LocalDate createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public Education updatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalDate getUpdatedAt() {
        return this.updatedAt;
    }

    public Education updatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Worker getWorker() {
        return this.worker;
    }

    public Education worker(Worker worker) {
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
        if (!(o instanceof Education)) {
            return false;
        }
        return id != null && id.equals(((Education) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Education{" +
            "id=" + getId() +
            ", degreeName='" + getDegreeName() + "'" +
            ", institute='" + getInstitute() + "'" +
            ", yearOfPassing=" + getYearOfPassing() +
            ", marks=" + getMarks() +
            ", description='" + getDescription() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            "}";
    }
}
