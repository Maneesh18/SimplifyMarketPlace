package com.simplify.marketplace.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.validation.constraints.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A PreferredCity.
 */
@Document(collection = "preferred_city")
public class PreferredCity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("city")
    private String city;

    @DBRef
    @Field("jobPreference")
    @JsonIgnoreProperties(value = { "preferredCities", "worker" }, allowSetters = true)
    private JobPreference jobPreference;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public PreferredCity id(String id) {
        this.id = id;
        return this;
    }

    public String getCity() {
        return this.city;
    }

    public PreferredCity city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public JobPreference getJobPreference() {
        return this.jobPreference;
    }

    public PreferredCity jobPreference(JobPreference jobPreference) {
        this.setJobPreference(jobPreference);
        return this;
    }

    public void setJobPreference(JobPreference jobPreference) {
        this.jobPreference = jobPreference;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PreferredCity)) {
            return false;
        }
        return id != null && id.equals(((PreferredCity) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PreferredCity{" +
            "id=" + getId() +
            ", city='" + getCity() + "'" +
            "}";
    }
}
