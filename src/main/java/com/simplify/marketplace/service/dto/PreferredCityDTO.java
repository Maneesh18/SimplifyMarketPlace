package com.simplify.marketplace.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.simplify.marketplace.domain.PreferredCity} entity.
 */
public class PreferredCityDTO implements Serializable {

    private String id;

    @NotNull
    private String city;

    private JobPreferenceDTO jobPreference;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public JobPreferenceDTO getJobPreference() {
        return jobPreference;
    }

    public void setJobPreference(JobPreferenceDTO jobPreference) {
        this.jobPreference = jobPreference;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PreferredCityDTO)) {
            return false;
        }

        PreferredCityDTO preferredCityDTO = (PreferredCityDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, preferredCityDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PreferredCityDTO{" +
            "id='" + getId() + "'" +
            ", city='" + getCity() + "'" +
            ", jobPreference=" + getJobPreference() +
            "}";
    }
}
