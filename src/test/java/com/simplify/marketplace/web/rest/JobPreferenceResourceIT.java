package com.simplify.marketplace.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.simplify.marketplace.IntegrationTest;
import com.simplify.marketplace.domain.JobPreference;
import com.simplify.marketplace.domain.enumeration.Category;
import com.simplify.marketplace.domain.enumeration.EngagementType;
import com.simplify.marketplace.domain.enumeration.LocationType;
import com.simplify.marketplace.domain.enumeration.SubCategory;
import com.simplify.marketplace.repository.JobPreferenceRepository;
import com.simplify.marketplace.service.dto.JobPreferenceDTO;
import com.simplify.marketplace.service.mapper.JobPreferenceMapper;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link JobPreferenceResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class JobPreferenceResourceIT {

    private static final Category DEFAULT_CATEGORY = Category.HealthCare;
    private static final Category UPDATED_CATEGORY = Category.Driver;

    private static final SubCategory DEFAULT_SUB_CATEGORY = SubCategory.Nurse;
    private static final SubCategory UPDATED_SUB_CATEGORY = SubCategory.Receptionist;

    private static final Integer DEFAULT_HOURLY_RATE = 1;
    private static final Integer UPDATED_HOURLY_RATE = 2;

    private static final Integer DEFAULT_DAILY_RATE = 1;
    private static final Integer UPDATED_DAILY_RATE = 2;

    private static final Integer DEFAULT_MONTHLY_RATE = 1;
    private static final Integer UPDATED_MONTHLY_RATE = 2;

    private static final Integer DEFAULT_HOUR_PER_DAY = 1;
    private static final Integer UPDATED_HOUR_PER_DAY = 2;

    private static final Integer DEFAULT_HOUR_PER_WEEK = 1;
    private static final Integer UPDATED_HOUR_PER_WEEK = 2;

    private static final EngagementType DEFAULT_ENGAGEMENT_TYPE = EngagementType.PartTime;
    private static final EngagementType UPDATED_ENGAGEMENT_TYPE = EngagementType.FullTime;

    private static final LocationType DEFAULT_LOCATION_TYPE = LocationType.RemoteOnly;
    private static final LocationType UPDATED_LOCATION_TYPE = LocationType.OfficeOnly;

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_AT = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_UPDATED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATED_AT = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/job-preferences";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private JobPreferenceRepository jobPreferenceRepository;

    @Autowired
    private JobPreferenceMapper jobPreferenceMapper;

    @Autowired
    private MockMvc restJobPreferenceMockMvc;

    private JobPreference jobPreference;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static JobPreference createEntity() {
        JobPreference jobPreference = new JobPreference()
            .category(DEFAULT_CATEGORY)
            .subCategory(DEFAULT_SUB_CATEGORY)
            .hourlyRate(DEFAULT_HOURLY_RATE)
            .dailyRate(DEFAULT_DAILY_RATE)
            .monthlyRate(DEFAULT_MONTHLY_RATE)
            .hourPerDay(DEFAULT_HOUR_PER_DAY)
            .hourPerWeek(DEFAULT_HOUR_PER_WEEK)
            .engagementType(DEFAULT_ENGAGEMENT_TYPE)
            .locationType(DEFAULT_LOCATION_TYPE)
            .createdBy(DEFAULT_CREATED_BY)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedAt(DEFAULT_UPDATED_AT);
        return jobPreference;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static JobPreference createUpdatedEntity() {
        JobPreference jobPreference = new JobPreference()
            .category(UPDATED_CATEGORY)
            .subCategory(UPDATED_SUB_CATEGORY)
            .hourlyRate(UPDATED_HOURLY_RATE)
            .dailyRate(UPDATED_DAILY_RATE)
            .monthlyRate(UPDATED_MONTHLY_RATE)
            .hourPerDay(UPDATED_HOUR_PER_DAY)
            .hourPerWeek(UPDATED_HOUR_PER_WEEK)
            .engagementType(UPDATED_ENGAGEMENT_TYPE)
            .locationType(UPDATED_LOCATION_TYPE)
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedAt(UPDATED_UPDATED_AT);
        return jobPreference;
    }

    @BeforeEach
    public void initTest() {
        jobPreferenceRepository.deleteAll();
        jobPreference = createEntity();
    }

    @Test
    void createJobPreference() throws Exception {
        int databaseSizeBeforeCreate = jobPreferenceRepository.findAll().size();
        // Create the JobPreference
        JobPreferenceDTO jobPreferenceDTO = jobPreferenceMapper.toDto(jobPreference);
        restJobPreferenceMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(jobPreferenceDTO))
            )
            .andExpect(status().isCreated());

        // Validate the JobPreference in the database
        List<JobPreference> jobPreferenceList = jobPreferenceRepository.findAll();
        assertThat(jobPreferenceList).hasSize(databaseSizeBeforeCreate + 1);
        JobPreference testJobPreference = jobPreferenceList.get(jobPreferenceList.size() - 1);
        assertThat(testJobPreference.getCategory()).isEqualTo(DEFAULT_CATEGORY);
        assertThat(testJobPreference.getSubCategory()).isEqualTo(DEFAULT_SUB_CATEGORY);
        assertThat(testJobPreference.getHourlyRate()).isEqualTo(DEFAULT_HOURLY_RATE);
        assertThat(testJobPreference.getDailyRate()).isEqualTo(DEFAULT_DAILY_RATE);
        assertThat(testJobPreference.getMonthlyRate()).isEqualTo(DEFAULT_MONTHLY_RATE);
        assertThat(testJobPreference.getHourPerDay()).isEqualTo(DEFAULT_HOUR_PER_DAY);
        assertThat(testJobPreference.getHourPerWeek()).isEqualTo(DEFAULT_HOUR_PER_WEEK);
        assertThat(testJobPreference.getEngagementType()).isEqualTo(DEFAULT_ENGAGEMENT_TYPE);
        assertThat(testJobPreference.getLocationType()).isEqualTo(DEFAULT_LOCATION_TYPE);
        assertThat(testJobPreference.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testJobPreference.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testJobPreference.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testJobPreference.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    void createJobPreferenceWithExistingId() throws Exception {
        // Create the JobPreference with an existing ID
        jobPreference.setId("existing_id");
        JobPreferenceDTO jobPreferenceDTO = jobPreferenceMapper.toDto(jobPreference);

        int databaseSizeBeforeCreate = jobPreferenceRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restJobPreferenceMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(jobPreferenceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the JobPreference in the database
        List<JobPreference> jobPreferenceList = jobPreferenceRepository.findAll();
        assertThat(jobPreferenceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkCategoryIsRequired() throws Exception {
        int databaseSizeBeforeTest = jobPreferenceRepository.findAll().size();
        // set the field null
        jobPreference.setCategory(null);

        // Create the JobPreference, which fails.
        JobPreferenceDTO jobPreferenceDTO = jobPreferenceMapper.toDto(jobPreference);

        restJobPreferenceMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(jobPreferenceDTO))
            )
            .andExpect(status().isBadRequest());

        List<JobPreference> jobPreferenceList = jobPreferenceRepository.findAll();
        assertThat(jobPreferenceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkSubCategoryIsRequired() throws Exception {
        int databaseSizeBeforeTest = jobPreferenceRepository.findAll().size();
        // set the field null
        jobPreference.setSubCategory(null);

        // Create the JobPreference, which fails.
        JobPreferenceDTO jobPreferenceDTO = jobPreferenceMapper.toDto(jobPreference);

        restJobPreferenceMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(jobPreferenceDTO))
            )
            .andExpect(status().isBadRequest());

        List<JobPreference> jobPreferenceList = jobPreferenceRepository.findAll();
        assertThat(jobPreferenceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkHourlyRateIsRequired() throws Exception {
        int databaseSizeBeforeTest = jobPreferenceRepository.findAll().size();
        // set the field null
        jobPreference.setHourlyRate(null);

        // Create the JobPreference, which fails.
        JobPreferenceDTO jobPreferenceDTO = jobPreferenceMapper.toDto(jobPreference);

        restJobPreferenceMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(jobPreferenceDTO))
            )
            .andExpect(status().isBadRequest());

        List<JobPreference> jobPreferenceList = jobPreferenceRepository.findAll();
        assertThat(jobPreferenceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkDailyRateIsRequired() throws Exception {
        int databaseSizeBeforeTest = jobPreferenceRepository.findAll().size();
        // set the field null
        jobPreference.setDailyRate(null);

        // Create the JobPreference, which fails.
        JobPreferenceDTO jobPreferenceDTO = jobPreferenceMapper.toDto(jobPreference);

        restJobPreferenceMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(jobPreferenceDTO))
            )
            .andExpect(status().isBadRequest());

        List<JobPreference> jobPreferenceList = jobPreferenceRepository.findAll();
        assertThat(jobPreferenceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkMonthlyRateIsRequired() throws Exception {
        int databaseSizeBeforeTest = jobPreferenceRepository.findAll().size();
        // set the field null
        jobPreference.setMonthlyRate(null);

        // Create the JobPreference, which fails.
        JobPreferenceDTO jobPreferenceDTO = jobPreferenceMapper.toDto(jobPreference);

        restJobPreferenceMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(jobPreferenceDTO))
            )
            .andExpect(status().isBadRequest());

        List<JobPreference> jobPreferenceList = jobPreferenceRepository.findAll();
        assertThat(jobPreferenceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkHourPerDayIsRequired() throws Exception {
        int databaseSizeBeforeTest = jobPreferenceRepository.findAll().size();
        // set the field null
        jobPreference.setHourPerDay(null);

        // Create the JobPreference, which fails.
        JobPreferenceDTO jobPreferenceDTO = jobPreferenceMapper.toDto(jobPreference);

        restJobPreferenceMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(jobPreferenceDTO))
            )
            .andExpect(status().isBadRequest());

        List<JobPreference> jobPreferenceList = jobPreferenceRepository.findAll();
        assertThat(jobPreferenceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkHourPerWeekIsRequired() throws Exception {
        int databaseSizeBeforeTest = jobPreferenceRepository.findAll().size();
        // set the field null
        jobPreference.setHourPerWeek(null);

        // Create the JobPreference, which fails.
        JobPreferenceDTO jobPreferenceDTO = jobPreferenceMapper.toDto(jobPreference);

        restJobPreferenceMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(jobPreferenceDTO))
            )
            .andExpect(status().isBadRequest());

        List<JobPreference> jobPreferenceList = jobPreferenceRepository.findAll();
        assertThat(jobPreferenceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkCreatedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = jobPreferenceRepository.findAll().size();
        // set the field null
        jobPreference.setCreatedBy(null);

        // Create the JobPreference, which fails.
        JobPreferenceDTO jobPreferenceDTO = jobPreferenceMapper.toDto(jobPreference);

        restJobPreferenceMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(jobPreferenceDTO))
            )
            .andExpect(status().isBadRequest());

        List<JobPreference> jobPreferenceList = jobPreferenceRepository.findAll();
        assertThat(jobPreferenceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = jobPreferenceRepository.findAll().size();
        // set the field null
        jobPreference.setCreatedAt(null);

        // Create the JobPreference, which fails.
        JobPreferenceDTO jobPreferenceDTO = jobPreferenceMapper.toDto(jobPreference);

        restJobPreferenceMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(jobPreferenceDTO))
            )
            .andExpect(status().isBadRequest());

        List<JobPreference> jobPreferenceList = jobPreferenceRepository.findAll();
        assertThat(jobPreferenceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkUpdatedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = jobPreferenceRepository.findAll().size();
        // set the field null
        jobPreference.setUpdatedBy(null);

        // Create the JobPreference, which fails.
        JobPreferenceDTO jobPreferenceDTO = jobPreferenceMapper.toDto(jobPreference);

        restJobPreferenceMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(jobPreferenceDTO))
            )
            .andExpect(status().isBadRequest());

        List<JobPreference> jobPreferenceList = jobPreferenceRepository.findAll();
        assertThat(jobPreferenceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkUpdatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = jobPreferenceRepository.findAll().size();
        // set the field null
        jobPreference.setUpdatedAt(null);

        // Create the JobPreference, which fails.
        JobPreferenceDTO jobPreferenceDTO = jobPreferenceMapper.toDto(jobPreference);

        restJobPreferenceMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(jobPreferenceDTO))
            )
            .andExpect(status().isBadRequest());

        List<JobPreference> jobPreferenceList = jobPreferenceRepository.findAll();
        assertThat(jobPreferenceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllJobPreferences() throws Exception {
        // Initialize the database
        jobPreferenceRepository.save(jobPreference);

        // Get all the jobPreferenceList
        restJobPreferenceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jobPreference.getId())))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY.toString())))
            .andExpect(jsonPath("$.[*].subCategory").value(hasItem(DEFAULT_SUB_CATEGORY.toString())))
            .andExpect(jsonPath("$.[*].hourlyRate").value(hasItem(DEFAULT_HOURLY_RATE)))
            .andExpect(jsonPath("$.[*].dailyRate").value(hasItem(DEFAULT_DAILY_RATE)))
            .andExpect(jsonPath("$.[*].monthlyRate").value(hasItem(DEFAULT_MONTHLY_RATE)))
            .andExpect(jsonPath("$.[*].hourPerDay").value(hasItem(DEFAULT_HOUR_PER_DAY)))
            .andExpect(jsonPath("$.[*].hourPerWeek").value(hasItem(DEFAULT_HOUR_PER_WEEK)))
            .andExpect(jsonPath("$.[*].engagementType").value(hasItem(DEFAULT_ENGAGEMENT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].locationType").value(hasItem(DEFAULT_LOCATION_TYPE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));
    }

    @Test
    void getJobPreference() throws Exception {
        // Initialize the database
        jobPreferenceRepository.save(jobPreference);

        // Get the jobPreference
        restJobPreferenceMockMvc
            .perform(get(ENTITY_API_URL_ID, jobPreference.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(jobPreference.getId()))
            .andExpect(jsonPath("$.category").value(DEFAULT_CATEGORY.toString()))
            .andExpect(jsonPath("$.subCategory").value(DEFAULT_SUB_CATEGORY.toString()))
            .andExpect(jsonPath("$.hourlyRate").value(DEFAULT_HOURLY_RATE))
            .andExpect(jsonPath("$.dailyRate").value(DEFAULT_DAILY_RATE))
            .andExpect(jsonPath("$.monthlyRate").value(DEFAULT_MONTHLY_RATE))
            .andExpect(jsonPath("$.hourPerDay").value(DEFAULT_HOUR_PER_DAY))
            .andExpect(jsonPath("$.hourPerWeek").value(DEFAULT_HOUR_PER_WEEK))
            .andExpect(jsonPath("$.engagementType").value(DEFAULT_ENGAGEMENT_TYPE.toString()))
            .andExpect(jsonPath("$.locationType").value(DEFAULT_LOCATION_TYPE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()));
    }

    @Test
    void getNonExistingJobPreference() throws Exception {
        // Get the jobPreference
        restJobPreferenceMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putNewJobPreference() throws Exception {
        // Initialize the database
        jobPreferenceRepository.save(jobPreference);

        int databaseSizeBeforeUpdate = jobPreferenceRepository.findAll().size();

        // Update the jobPreference
        JobPreference updatedJobPreference = jobPreferenceRepository.findById(jobPreference.getId()).get();
        updatedJobPreference
            .category(UPDATED_CATEGORY)
            .subCategory(UPDATED_SUB_CATEGORY)
            .hourlyRate(UPDATED_HOURLY_RATE)
            .dailyRate(UPDATED_DAILY_RATE)
            .monthlyRate(UPDATED_MONTHLY_RATE)
            .hourPerDay(UPDATED_HOUR_PER_DAY)
            .hourPerWeek(UPDATED_HOUR_PER_WEEK)
            .engagementType(UPDATED_ENGAGEMENT_TYPE)
            .locationType(UPDATED_LOCATION_TYPE)
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedAt(UPDATED_UPDATED_AT);
        JobPreferenceDTO jobPreferenceDTO = jobPreferenceMapper.toDto(updatedJobPreference);

        restJobPreferenceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, jobPreferenceDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(jobPreferenceDTO))
            )
            .andExpect(status().isOk());

        // Validate the JobPreference in the database
        List<JobPreference> jobPreferenceList = jobPreferenceRepository.findAll();
        assertThat(jobPreferenceList).hasSize(databaseSizeBeforeUpdate);
        JobPreference testJobPreference = jobPreferenceList.get(jobPreferenceList.size() - 1);
        assertThat(testJobPreference.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testJobPreference.getSubCategory()).isEqualTo(UPDATED_SUB_CATEGORY);
        assertThat(testJobPreference.getHourlyRate()).isEqualTo(UPDATED_HOURLY_RATE);
        assertThat(testJobPreference.getDailyRate()).isEqualTo(UPDATED_DAILY_RATE);
        assertThat(testJobPreference.getMonthlyRate()).isEqualTo(UPDATED_MONTHLY_RATE);
        assertThat(testJobPreference.getHourPerDay()).isEqualTo(UPDATED_HOUR_PER_DAY);
        assertThat(testJobPreference.getHourPerWeek()).isEqualTo(UPDATED_HOUR_PER_WEEK);
        assertThat(testJobPreference.getEngagementType()).isEqualTo(UPDATED_ENGAGEMENT_TYPE);
        assertThat(testJobPreference.getLocationType()).isEqualTo(UPDATED_LOCATION_TYPE);
        assertThat(testJobPreference.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testJobPreference.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testJobPreference.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testJobPreference.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    void putNonExistingJobPreference() throws Exception {
        int databaseSizeBeforeUpdate = jobPreferenceRepository.findAll().size();
        jobPreference.setId(UUID.randomUUID().toString());

        // Create the JobPreference
        JobPreferenceDTO jobPreferenceDTO = jobPreferenceMapper.toDto(jobPreference);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJobPreferenceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, jobPreferenceDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(jobPreferenceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the JobPreference in the database
        List<JobPreference> jobPreferenceList = jobPreferenceRepository.findAll();
        assertThat(jobPreferenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchJobPreference() throws Exception {
        int databaseSizeBeforeUpdate = jobPreferenceRepository.findAll().size();
        jobPreference.setId(UUID.randomUUID().toString());

        // Create the JobPreference
        JobPreferenceDTO jobPreferenceDTO = jobPreferenceMapper.toDto(jobPreference);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJobPreferenceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(jobPreferenceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the JobPreference in the database
        List<JobPreference> jobPreferenceList = jobPreferenceRepository.findAll();
        assertThat(jobPreferenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamJobPreference() throws Exception {
        int databaseSizeBeforeUpdate = jobPreferenceRepository.findAll().size();
        jobPreference.setId(UUID.randomUUID().toString());

        // Create the JobPreference
        JobPreferenceDTO jobPreferenceDTO = jobPreferenceMapper.toDto(jobPreference);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJobPreferenceMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(jobPreferenceDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the JobPreference in the database
        List<JobPreference> jobPreferenceList = jobPreferenceRepository.findAll();
        assertThat(jobPreferenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateJobPreferenceWithPatch() throws Exception {
        // Initialize the database
        jobPreferenceRepository.save(jobPreference);

        int databaseSizeBeforeUpdate = jobPreferenceRepository.findAll().size();

        // Update the jobPreference using partial update
        JobPreference partialUpdatedJobPreference = new JobPreference();
        partialUpdatedJobPreference.setId(jobPreference.getId());

        partialUpdatedJobPreference
            .dailyRate(UPDATED_DAILY_RATE)
            .monthlyRate(UPDATED_MONTHLY_RATE)
            .engagementType(UPDATED_ENGAGEMENT_TYPE)
            .updatedAt(UPDATED_UPDATED_AT);

        restJobPreferenceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedJobPreference.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedJobPreference))
            )
            .andExpect(status().isOk());

        // Validate the JobPreference in the database
        List<JobPreference> jobPreferenceList = jobPreferenceRepository.findAll();
        assertThat(jobPreferenceList).hasSize(databaseSizeBeforeUpdate);
        JobPreference testJobPreference = jobPreferenceList.get(jobPreferenceList.size() - 1);
        assertThat(testJobPreference.getCategory()).isEqualTo(DEFAULT_CATEGORY);
        assertThat(testJobPreference.getSubCategory()).isEqualTo(DEFAULT_SUB_CATEGORY);
        assertThat(testJobPreference.getHourlyRate()).isEqualTo(DEFAULT_HOURLY_RATE);
        assertThat(testJobPreference.getDailyRate()).isEqualTo(UPDATED_DAILY_RATE);
        assertThat(testJobPreference.getMonthlyRate()).isEqualTo(UPDATED_MONTHLY_RATE);
        assertThat(testJobPreference.getHourPerDay()).isEqualTo(DEFAULT_HOUR_PER_DAY);
        assertThat(testJobPreference.getHourPerWeek()).isEqualTo(DEFAULT_HOUR_PER_WEEK);
        assertThat(testJobPreference.getEngagementType()).isEqualTo(UPDATED_ENGAGEMENT_TYPE);
        assertThat(testJobPreference.getLocationType()).isEqualTo(DEFAULT_LOCATION_TYPE);
        assertThat(testJobPreference.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testJobPreference.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testJobPreference.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testJobPreference.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    void fullUpdateJobPreferenceWithPatch() throws Exception {
        // Initialize the database
        jobPreferenceRepository.save(jobPreference);

        int databaseSizeBeforeUpdate = jobPreferenceRepository.findAll().size();

        // Update the jobPreference using partial update
        JobPreference partialUpdatedJobPreference = new JobPreference();
        partialUpdatedJobPreference.setId(jobPreference.getId());

        partialUpdatedJobPreference
            .category(UPDATED_CATEGORY)
            .subCategory(UPDATED_SUB_CATEGORY)
            .hourlyRate(UPDATED_HOURLY_RATE)
            .dailyRate(UPDATED_DAILY_RATE)
            .monthlyRate(UPDATED_MONTHLY_RATE)
            .hourPerDay(UPDATED_HOUR_PER_DAY)
            .hourPerWeek(UPDATED_HOUR_PER_WEEK)
            .engagementType(UPDATED_ENGAGEMENT_TYPE)
            .locationType(UPDATED_LOCATION_TYPE)
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedAt(UPDATED_UPDATED_AT);

        restJobPreferenceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedJobPreference.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedJobPreference))
            )
            .andExpect(status().isOk());

        // Validate the JobPreference in the database
        List<JobPreference> jobPreferenceList = jobPreferenceRepository.findAll();
        assertThat(jobPreferenceList).hasSize(databaseSizeBeforeUpdate);
        JobPreference testJobPreference = jobPreferenceList.get(jobPreferenceList.size() - 1);
        assertThat(testJobPreference.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testJobPreference.getSubCategory()).isEqualTo(UPDATED_SUB_CATEGORY);
        assertThat(testJobPreference.getHourlyRate()).isEqualTo(UPDATED_HOURLY_RATE);
        assertThat(testJobPreference.getDailyRate()).isEqualTo(UPDATED_DAILY_RATE);
        assertThat(testJobPreference.getMonthlyRate()).isEqualTo(UPDATED_MONTHLY_RATE);
        assertThat(testJobPreference.getHourPerDay()).isEqualTo(UPDATED_HOUR_PER_DAY);
        assertThat(testJobPreference.getHourPerWeek()).isEqualTo(UPDATED_HOUR_PER_WEEK);
        assertThat(testJobPreference.getEngagementType()).isEqualTo(UPDATED_ENGAGEMENT_TYPE);
        assertThat(testJobPreference.getLocationType()).isEqualTo(UPDATED_LOCATION_TYPE);
        assertThat(testJobPreference.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testJobPreference.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testJobPreference.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testJobPreference.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    void patchNonExistingJobPreference() throws Exception {
        int databaseSizeBeforeUpdate = jobPreferenceRepository.findAll().size();
        jobPreference.setId(UUID.randomUUID().toString());

        // Create the JobPreference
        JobPreferenceDTO jobPreferenceDTO = jobPreferenceMapper.toDto(jobPreference);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJobPreferenceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, jobPreferenceDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(jobPreferenceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the JobPreference in the database
        List<JobPreference> jobPreferenceList = jobPreferenceRepository.findAll();
        assertThat(jobPreferenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchJobPreference() throws Exception {
        int databaseSizeBeforeUpdate = jobPreferenceRepository.findAll().size();
        jobPreference.setId(UUID.randomUUID().toString());

        // Create the JobPreference
        JobPreferenceDTO jobPreferenceDTO = jobPreferenceMapper.toDto(jobPreference);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJobPreferenceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(jobPreferenceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the JobPreference in the database
        List<JobPreference> jobPreferenceList = jobPreferenceRepository.findAll();
        assertThat(jobPreferenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamJobPreference() throws Exception {
        int databaseSizeBeforeUpdate = jobPreferenceRepository.findAll().size();
        jobPreference.setId(UUID.randomUUID().toString());

        // Create the JobPreference
        JobPreferenceDTO jobPreferenceDTO = jobPreferenceMapper.toDto(jobPreference);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJobPreferenceMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(jobPreferenceDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the JobPreference in the database
        List<JobPreference> jobPreferenceList = jobPreferenceRepository.findAll();
        assertThat(jobPreferenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteJobPreference() throws Exception {
        // Initialize the database
        jobPreferenceRepository.save(jobPreference);

        int databaseSizeBeforeDelete = jobPreferenceRepository.findAll().size();

        // Delete the jobPreference
        restJobPreferenceMockMvc
            .perform(delete(ENTITY_API_URL_ID, jobPreference.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<JobPreference> jobPreferenceList = jobPreferenceRepository.findAll();
        assertThat(jobPreferenceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
