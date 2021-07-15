package com.simplify.marketplace.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.simplify.marketplace.IntegrationTest;
import com.simplify.marketplace.domain.PreferredCity;
import com.simplify.marketplace.repository.PreferredCityRepository;
import com.simplify.marketplace.service.dto.PreferredCityDTO;
import com.simplify.marketplace.service.mapper.PreferredCityMapper;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Integration tests for the {@link PreferredCityResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PreferredCityResourceIT {

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/preferred-cities";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private PreferredCityRepository preferredCityRepository;

    @Autowired
    private PreferredCityMapper preferredCityMapper;

    @Autowired
    private MockMvc restPreferredCityMockMvc;

    private PreferredCity preferredCity;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PreferredCity createEntity() {
        PreferredCity preferredCity = new PreferredCity().city(DEFAULT_CITY);
        return preferredCity;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PreferredCity createUpdatedEntity() {
        PreferredCity preferredCity = new PreferredCity().city(UPDATED_CITY);
        return preferredCity;
    }

    @BeforeEach
    public void initTest() {
        preferredCityRepository.deleteAll();
        preferredCity = createEntity();
    }

    @Test
    void createPreferredCity() throws Exception {
        int databaseSizeBeforeCreate = preferredCityRepository.findAll().size();
        // Create the PreferredCity
        PreferredCityDTO preferredCityDTO = preferredCityMapper.toDto(preferredCity);
        restPreferredCityMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(preferredCityDTO))
            )
            .andExpect(status().isCreated());

        // Validate the PreferredCity in the database
        List<PreferredCity> preferredCityList = preferredCityRepository.findAll();
        assertThat(preferredCityList).hasSize(databaseSizeBeforeCreate + 1);
        PreferredCity testPreferredCity = preferredCityList.get(preferredCityList.size() - 1);
        assertThat(testPreferredCity.getCity()).isEqualTo(DEFAULT_CITY);
    }

    @Test
    void createPreferredCityWithExistingId() throws Exception {
        // Create the PreferredCity with an existing ID
        preferredCity.setId("existing_id");
        PreferredCityDTO preferredCityDTO = preferredCityMapper.toDto(preferredCity);

        int databaseSizeBeforeCreate = preferredCityRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPreferredCityMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(preferredCityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PreferredCity in the database
        List<PreferredCity> preferredCityList = preferredCityRepository.findAll();
        assertThat(preferredCityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkCityIsRequired() throws Exception {
        int databaseSizeBeforeTest = preferredCityRepository.findAll().size();
        // set the field null
        preferredCity.setCity(null);

        // Create the PreferredCity, which fails.
        PreferredCityDTO preferredCityDTO = preferredCityMapper.toDto(preferredCity);

        restPreferredCityMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(preferredCityDTO))
            )
            .andExpect(status().isBadRequest());

        List<PreferredCity> preferredCityList = preferredCityRepository.findAll();
        assertThat(preferredCityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllPreferredCities() throws Exception {
        // Initialize the database
        preferredCityRepository.save(preferredCity);

        // Get all the preferredCityList
        restPreferredCityMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(preferredCity.getId())))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)));
    }

    @Test
    void getPreferredCity() throws Exception {
        // Initialize the database
        preferredCityRepository.save(preferredCity);

        // Get the preferredCity
        restPreferredCityMockMvc
            .perform(get(ENTITY_API_URL_ID, preferredCity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(preferredCity.getId()))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY));
    }

    @Test
    void getNonExistingPreferredCity() throws Exception {
        // Get the preferredCity
        restPreferredCityMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putNewPreferredCity() throws Exception {
        // Initialize the database
        preferredCityRepository.save(preferredCity);

        int databaseSizeBeforeUpdate = preferredCityRepository.findAll().size();

        // Update the preferredCity
        PreferredCity updatedPreferredCity = preferredCityRepository.findById(preferredCity.getId()).get();
        updatedPreferredCity.city(UPDATED_CITY);
        PreferredCityDTO preferredCityDTO = preferredCityMapper.toDto(updatedPreferredCity);

        restPreferredCityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, preferredCityDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(preferredCityDTO))
            )
            .andExpect(status().isOk());

        // Validate the PreferredCity in the database
        List<PreferredCity> preferredCityList = preferredCityRepository.findAll();
        assertThat(preferredCityList).hasSize(databaseSizeBeforeUpdate);
        PreferredCity testPreferredCity = preferredCityList.get(preferredCityList.size() - 1);
        assertThat(testPreferredCity.getCity()).isEqualTo(UPDATED_CITY);
    }

    @Test
    void putNonExistingPreferredCity() throws Exception {
        int databaseSizeBeforeUpdate = preferredCityRepository.findAll().size();
        preferredCity.setId(UUID.randomUUID().toString());

        // Create the PreferredCity
        PreferredCityDTO preferredCityDTO = preferredCityMapper.toDto(preferredCity);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPreferredCityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, preferredCityDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(preferredCityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PreferredCity in the database
        List<PreferredCity> preferredCityList = preferredCityRepository.findAll();
        assertThat(preferredCityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchPreferredCity() throws Exception {
        int databaseSizeBeforeUpdate = preferredCityRepository.findAll().size();
        preferredCity.setId(UUID.randomUUID().toString());

        // Create the PreferredCity
        PreferredCityDTO preferredCityDTO = preferredCityMapper.toDto(preferredCity);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPreferredCityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(preferredCityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PreferredCity in the database
        List<PreferredCity> preferredCityList = preferredCityRepository.findAll();
        assertThat(preferredCityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamPreferredCity() throws Exception {
        int databaseSizeBeforeUpdate = preferredCityRepository.findAll().size();
        preferredCity.setId(UUID.randomUUID().toString());

        // Create the PreferredCity
        PreferredCityDTO preferredCityDTO = preferredCityMapper.toDto(preferredCity);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPreferredCityMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(preferredCityDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PreferredCity in the database
        List<PreferredCity> preferredCityList = preferredCityRepository.findAll();
        assertThat(preferredCityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdatePreferredCityWithPatch() throws Exception {
        // Initialize the database
        preferredCityRepository.save(preferredCity);

        int databaseSizeBeforeUpdate = preferredCityRepository.findAll().size();

        // Update the preferredCity using partial update
        PreferredCity partialUpdatedPreferredCity = new PreferredCity();
        partialUpdatedPreferredCity.setId(preferredCity.getId());

        restPreferredCityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPreferredCity.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPreferredCity))
            )
            .andExpect(status().isOk());

        // Validate the PreferredCity in the database
        List<PreferredCity> preferredCityList = preferredCityRepository.findAll();
        assertThat(preferredCityList).hasSize(databaseSizeBeforeUpdate);
        PreferredCity testPreferredCity = preferredCityList.get(preferredCityList.size() - 1);
        assertThat(testPreferredCity.getCity()).isEqualTo(DEFAULT_CITY);
    }

    @Test
    void fullUpdatePreferredCityWithPatch() throws Exception {
        // Initialize the database
        preferredCityRepository.save(preferredCity);

        int databaseSizeBeforeUpdate = preferredCityRepository.findAll().size();

        // Update the preferredCity using partial update
        PreferredCity partialUpdatedPreferredCity = new PreferredCity();
        partialUpdatedPreferredCity.setId(preferredCity.getId());

        partialUpdatedPreferredCity.city(UPDATED_CITY);

        restPreferredCityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPreferredCity.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPreferredCity))
            )
            .andExpect(status().isOk());

        // Validate the PreferredCity in the database
        List<PreferredCity> preferredCityList = preferredCityRepository.findAll();
        assertThat(preferredCityList).hasSize(databaseSizeBeforeUpdate);
        PreferredCity testPreferredCity = preferredCityList.get(preferredCityList.size() - 1);
        assertThat(testPreferredCity.getCity()).isEqualTo(UPDATED_CITY);
    }

    @Test
    void patchNonExistingPreferredCity() throws Exception {
        int databaseSizeBeforeUpdate = preferredCityRepository.findAll().size();
        preferredCity.setId(UUID.randomUUID().toString());

        // Create the PreferredCity
        PreferredCityDTO preferredCityDTO = preferredCityMapper.toDto(preferredCity);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPreferredCityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, preferredCityDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(preferredCityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PreferredCity in the database
        List<PreferredCity> preferredCityList = preferredCityRepository.findAll();
        assertThat(preferredCityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchPreferredCity() throws Exception {
        int databaseSizeBeforeUpdate = preferredCityRepository.findAll().size();
        preferredCity.setId(UUID.randomUUID().toString());

        // Create the PreferredCity
        PreferredCityDTO preferredCityDTO = preferredCityMapper.toDto(preferredCity);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPreferredCityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(preferredCityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PreferredCity in the database
        List<PreferredCity> preferredCityList = preferredCityRepository.findAll();
        assertThat(preferredCityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamPreferredCity() throws Exception {
        int databaseSizeBeforeUpdate = preferredCityRepository.findAll().size();
        preferredCity.setId(UUID.randomUUID().toString());

        // Create the PreferredCity
        PreferredCityDTO preferredCityDTO = preferredCityMapper.toDto(preferredCity);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPreferredCityMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(preferredCityDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PreferredCity in the database
        List<PreferredCity> preferredCityList = preferredCityRepository.findAll();
        assertThat(preferredCityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deletePreferredCity() throws Exception {
        // Initialize the database
        preferredCityRepository.save(preferredCity);

        int databaseSizeBeforeDelete = preferredCityRepository.findAll().size();

        // Delete the preferredCity
        restPreferredCityMockMvc
            .perform(delete(ENTITY_API_URL_ID, preferredCity.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PreferredCity> preferredCityList = preferredCityRepository.findAll();
        assertThat(preferredCityList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
