package com.simplify.marketplace.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.simplify.marketplace.IntegrationTest;
import com.simplify.marketplace.domain.Employment;
import com.simplify.marketplace.domain.enumeration.Category;
import com.simplify.marketplace.domain.enumeration.SubCategory;
import com.simplify.marketplace.repository.EmploymentRepository;
import com.simplify.marketplace.service.dto.EmploymentDTO;
import com.simplify.marketplace.service.mapper.EmploymentMapper;
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
 * Integration tests for the {@link EmploymentResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EmploymentResourceIT {

    private static final String DEFAULT_JOB_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_JOB_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_COMPANY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_LAST_SALARY = 1;
    private static final Integer UPDATED_LAST_SALARY = 2;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Category DEFAULT_CATEGORY = Category.HealthCare;
    private static final Category UPDATED_CATEGORY = Category.Driver;

    private static final SubCategory DEFAULT_SUB_CATEGORY = SubCategory.Nurse;
    private static final SubCategory UPDATED_SUB_CATEGORY = SubCategory.Receptionist;

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_AT = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_UPDATED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATED_AT = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/employments";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private EmploymentRepository employmentRepository;

    @Autowired
    private EmploymentMapper employmentMapper;

    @Autowired
    private MockMvc restEmploymentMockMvc;

    private Employment employment;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Employment createEntity() {
        Employment employment = new Employment()
            .jobTitle(DEFAULT_JOB_TITLE)
            .companyName(DEFAULT_COMPANY_NAME)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .lastSalary(DEFAULT_LAST_SALARY)
            .description(DEFAULT_DESCRIPTION)
            .category(DEFAULT_CATEGORY)
            .subCategory(DEFAULT_SUB_CATEGORY)
            .createdBy(DEFAULT_CREATED_BY)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedAt(DEFAULT_UPDATED_AT);
        return employment;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Employment createUpdatedEntity() {
        Employment employment = new Employment()
            .jobTitle(UPDATED_JOB_TITLE)
            .companyName(UPDATED_COMPANY_NAME)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .lastSalary(UPDATED_LAST_SALARY)
            .description(UPDATED_DESCRIPTION)
            .category(UPDATED_CATEGORY)
            .subCategory(UPDATED_SUB_CATEGORY)
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedAt(UPDATED_UPDATED_AT);
        return employment;
    }

    @BeforeEach
    public void initTest() {
        employmentRepository.deleteAll();
        employment = createEntity();
    }

    @Test
    void createEmployment() throws Exception {
        int databaseSizeBeforeCreate = employmentRepository.findAll().size();
        // Create the Employment
        EmploymentDTO employmentDTO = employmentMapper.toDto(employment);
        restEmploymentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(employmentDTO)))
            .andExpect(status().isCreated());

        // Validate the Employment in the database
        List<Employment> employmentList = employmentRepository.findAll();
        assertThat(employmentList).hasSize(databaseSizeBeforeCreate + 1);
        Employment testEmployment = employmentList.get(employmentList.size() - 1);
        assertThat(testEmployment.getJobTitle()).isEqualTo(DEFAULT_JOB_TITLE);
        assertThat(testEmployment.getCompanyName()).isEqualTo(DEFAULT_COMPANY_NAME);
        assertThat(testEmployment.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testEmployment.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testEmployment.getLastSalary()).isEqualTo(DEFAULT_LAST_SALARY);
        assertThat(testEmployment.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testEmployment.getCategory()).isEqualTo(DEFAULT_CATEGORY);
        assertThat(testEmployment.getSubCategory()).isEqualTo(DEFAULT_SUB_CATEGORY);
        assertThat(testEmployment.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testEmployment.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testEmployment.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testEmployment.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    void createEmploymentWithExistingId() throws Exception {
        // Create the Employment with an existing ID
        employment.setId("existing_id");
        EmploymentDTO employmentDTO = employmentMapper.toDto(employment);

        int databaseSizeBeforeCreate = employmentRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmploymentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(employmentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Employment in the database
        List<Employment> employmentList = employmentRepository.findAll();
        assertThat(employmentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkJobTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = employmentRepository.findAll().size();
        // set the field null
        employment.setJobTitle(null);

        // Create the Employment, which fails.
        EmploymentDTO employmentDTO = employmentMapper.toDto(employment);

        restEmploymentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(employmentDTO)))
            .andExpect(status().isBadRequest());

        List<Employment> employmentList = employmentRepository.findAll();
        assertThat(employmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkCompanyNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = employmentRepository.findAll().size();
        // set the field null
        employment.setCompanyName(null);

        // Create the Employment, which fails.
        EmploymentDTO employmentDTO = employmentMapper.toDto(employment);

        restEmploymentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(employmentDTO)))
            .andExpect(status().isBadRequest());

        List<Employment> employmentList = employmentRepository.findAll();
        assertThat(employmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkStartDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = employmentRepository.findAll().size();
        // set the field null
        employment.setStartDate(null);

        // Create the Employment, which fails.
        EmploymentDTO employmentDTO = employmentMapper.toDto(employment);

        restEmploymentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(employmentDTO)))
            .andExpect(status().isBadRequest());

        List<Employment> employmentList = employmentRepository.findAll();
        assertThat(employmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkEndDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = employmentRepository.findAll().size();
        // set the field null
        employment.setEndDate(null);

        // Create the Employment, which fails.
        EmploymentDTO employmentDTO = employmentMapper.toDto(employment);

        restEmploymentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(employmentDTO)))
            .andExpect(status().isBadRequest());

        List<Employment> employmentList = employmentRepository.findAll();
        assertThat(employmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkLastSalaryIsRequired() throws Exception {
        int databaseSizeBeforeTest = employmentRepository.findAll().size();
        // set the field null
        employment.setLastSalary(null);

        // Create the Employment, which fails.
        EmploymentDTO employmentDTO = employmentMapper.toDto(employment);

        restEmploymentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(employmentDTO)))
            .andExpect(status().isBadRequest());

        List<Employment> employmentList = employmentRepository.findAll();
        assertThat(employmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkCreatedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = employmentRepository.findAll().size();
        // set the field null
        employment.setCreatedBy(null);

        // Create the Employment, which fails.
        EmploymentDTO employmentDTO = employmentMapper.toDto(employment);

        restEmploymentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(employmentDTO)))
            .andExpect(status().isBadRequest());

        List<Employment> employmentList = employmentRepository.findAll();
        assertThat(employmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = employmentRepository.findAll().size();
        // set the field null
        employment.setCreatedAt(null);

        // Create the Employment, which fails.
        EmploymentDTO employmentDTO = employmentMapper.toDto(employment);

        restEmploymentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(employmentDTO)))
            .andExpect(status().isBadRequest());

        List<Employment> employmentList = employmentRepository.findAll();
        assertThat(employmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkUpdatedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = employmentRepository.findAll().size();
        // set the field null
        employment.setUpdatedBy(null);

        // Create the Employment, which fails.
        EmploymentDTO employmentDTO = employmentMapper.toDto(employment);

        restEmploymentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(employmentDTO)))
            .andExpect(status().isBadRequest());

        List<Employment> employmentList = employmentRepository.findAll();
        assertThat(employmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkUpdatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = employmentRepository.findAll().size();
        // set the field null
        employment.setUpdatedAt(null);

        // Create the Employment, which fails.
        EmploymentDTO employmentDTO = employmentMapper.toDto(employment);

        restEmploymentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(employmentDTO)))
            .andExpect(status().isBadRequest());

        List<Employment> employmentList = employmentRepository.findAll();
        assertThat(employmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllEmployments() throws Exception {
        // Initialize the database
        employmentRepository.save(employment);

        // Get all the employmentList
        restEmploymentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employment.getId())))
            .andExpect(jsonPath("$.[*].jobTitle").value(hasItem(DEFAULT_JOB_TITLE)))
            .andExpect(jsonPath("$.[*].companyName").value(hasItem(DEFAULT_COMPANY_NAME)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastSalary").value(hasItem(DEFAULT_LAST_SALARY)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY.toString())))
            .andExpect(jsonPath("$.[*].subCategory").value(hasItem(DEFAULT_SUB_CATEGORY.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));
    }

    @Test
    void getEmployment() throws Exception {
        // Initialize the database
        employmentRepository.save(employment);

        // Get the employment
        restEmploymentMockMvc
            .perform(get(ENTITY_API_URL_ID, employment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(employment.getId()))
            .andExpect(jsonPath("$.jobTitle").value(DEFAULT_JOB_TITLE))
            .andExpect(jsonPath("$.companyName").value(DEFAULT_COMPANY_NAME))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.lastSalary").value(DEFAULT_LAST_SALARY))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.category").value(DEFAULT_CATEGORY.toString()))
            .andExpect(jsonPath("$.subCategory").value(DEFAULT_SUB_CATEGORY.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()));
    }

    @Test
    void getNonExistingEmployment() throws Exception {
        // Get the employment
        restEmploymentMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putNewEmployment() throws Exception {
        // Initialize the database
        employmentRepository.save(employment);

        int databaseSizeBeforeUpdate = employmentRepository.findAll().size();

        // Update the employment
        Employment updatedEmployment = employmentRepository.findById(employment.getId()).get();
        updatedEmployment
            .jobTitle(UPDATED_JOB_TITLE)
            .companyName(UPDATED_COMPANY_NAME)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .lastSalary(UPDATED_LAST_SALARY)
            .description(UPDATED_DESCRIPTION)
            .category(UPDATED_CATEGORY)
            .subCategory(UPDATED_SUB_CATEGORY)
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedAt(UPDATED_UPDATED_AT);
        EmploymentDTO employmentDTO = employmentMapper.toDto(updatedEmployment);

        restEmploymentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, employmentDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employmentDTO))
            )
            .andExpect(status().isOk());

        // Validate the Employment in the database
        List<Employment> employmentList = employmentRepository.findAll();
        assertThat(employmentList).hasSize(databaseSizeBeforeUpdate);
        Employment testEmployment = employmentList.get(employmentList.size() - 1);
        assertThat(testEmployment.getJobTitle()).isEqualTo(UPDATED_JOB_TITLE);
        assertThat(testEmployment.getCompanyName()).isEqualTo(UPDATED_COMPANY_NAME);
        assertThat(testEmployment.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testEmployment.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testEmployment.getLastSalary()).isEqualTo(UPDATED_LAST_SALARY);
        assertThat(testEmployment.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testEmployment.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testEmployment.getSubCategory()).isEqualTo(UPDATED_SUB_CATEGORY);
        assertThat(testEmployment.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testEmployment.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testEmployment.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testEmployment.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    void putNonExistingEmployment() throws Exception {
        int databaseSizeBeforeUpdate = employmentRepository.findAll().size();
        employment.setId(UUID.randomUUID().toString());

        // Create the Employment
        EmploymentDTO employmentDTO = employmentMapper.toDto(employment);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmploymentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, employmentDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employmentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Employment in the database
        List<Employment> employmentList = employmentRepository.findAll();
        assertThat(employmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchEmployment() throws Exception {
        int databaseSizeBeforeUpdate = employmentRepository.findAll().size();
        employment.setId(UUID.randomUUID().toString());

        // Create the Employment
        EmploymentDTO employmentDTO = employmentMapper.toDto(employment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmploymentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employmentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Employment in the database
        List<Employment> employmentList = employmentRepository.findAll();
        assertThat(employmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamEmployment() throws Exception {
        int databaseSizeBeforeUpdate = employmentRepository.findAll().size();
        employment.setId(UUID.randomUUID().toString());

        // Create the Employment
        EmploymentDTO employmentDTO = employmentMapper.toDto(employment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmploymentMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(employmentDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Employment in the database
        List<Employment> employmentList = employmentRepository.findAll();
        assertThat(employmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateEmploymentWithPatch() throws Exception {
        // Initialize the database
        employmentRepository.save(employment);

        int databaseSizeBeforeUpdate = employmentRepository.findAll().size();

        // Update the employment using partial update
        Employment partialUpdatedEmployment = new Employment();
        partialUpdatedEmployment.setId(employment.getId());

        partialUpdatedEmployment
            .jobTitle(UPDATED_JOB_TITLE)
            .companyName(UPDATED_COMPANY_NAME)
            .startDate(UPDATED_START_DATE)
            .description(UPDATED_DESCRIPTION)
            .subCategory(UPDATED_SUB_CATEGORY)
            .createdBy(UPDATED_CREATED_BY)
            .updatedBy(UPDATED_UPDATED_BY);

        restEmploymentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmployment.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmployment))
            )
            .andExpect(status().isOk());

        // Validate the Employment in the database
        List<Employment> employmentList = employmentRepository.findAll();
        assertThat(employmentList).hasSize(databaseSizeBeforeUpdate);
        Employment testEmployment = employmentList.get(employmentList.size() - 1);
        assertThat(testEmployment.getJobTitle()).isEqualTo(UPDATED_JOB_TITLE);
        assertThat(testEmployment.getCompanyName()).isEqualTo(UPDATED_COMPANY_NAME);
        assertThat(testEmployment.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testEmployment.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testEmployment.getLastSalary()).isEqualTo(DEFAULT_LAST_SALARY);
        assertThat(testEmployment.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testEmployment.getCategory()).isEqualTo(DEFAULT_CATEGORY);
        assertThat(testEmployment.getSubCategory()).isEqualTo(UPDATED_SUB_CATEGORY);
        assertThat(testEmployment.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testEmployment.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testEmployment.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testEmployment.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    void fullUpdateEmploymentWithPatch() throws Exception {
        // Initialize the database
        employmentRepository.save(employment);

        int databaseSizeBeforeUpdate = employmentRepository.findAll().size();

        // Update the employment using partial update
        Employment partialUpdatedEmployment = new Employment();
        partialUpdatedEmployment.setId(employment.getId());

        partialUpdatedEmployment
            .jobTitle(UPDATED_JOB_TITLE)
            .companyName(UPDATED_COMPANY_NAME)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .lastSalary(UPDATED_LAST_SALARY)
            .description(UPDATED_DESCRIPTION)
            .category(UPDATED_CATEGORY)
            .subCategory(UPDATED_SUB_CATEGORY)
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedAt(UPDATED_UPDATED_AT);

        restEmploymentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmployment.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmployment))
            )
            .andExpect(status().isOk());

        // Validate the Employment in the database
        List<Employment> employmentList = employmentRepository.findAll();
        assertThat(employmentList).hasSize(databaseSizeBeforeUpdate);
        Employment testEmployment = employmentList.get(employmentList.size() - 1);
        assertThat(testEmployment.getJobTitle()).isEqualTo(UPDATED_JOB_TITLE);
        assertThat(testEmployment.getCompanyName()).isEqualTo(UPDATED_COMPANY_NAME);
        assertThat(testEmployment.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testEmployment.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testEmployment.getLastSalary()).isEqualTo(UPDATED_LAST_SALARY);
        assertThat(testEmployment.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testEmployment.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testEmployment.getSubCategory()).isEqualTo(UPDATED_SUB_CATEGORY);
        assertThat(testEmployment.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testEmployment.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testEmployment.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testEmployment.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    void patchNonExistingEmployment() throws Exception {
        int databaseSizeBeforeUpdate = employmentRepository.findAll().size();
        employment.setId(UUID.randomUUID().toString());

        // Create the Employment
        EmploymentDTO employmentDTO = employmentMapper.toDto(employment);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmploymentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, employmentDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(employmentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Employment in the database
        List<Employment> employmentList = employmentRepository.findAll();
        assertThat(employmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchEmployment() throws Exception {
        int databaseSizeBeforeUpdate = employmentRepository.findAll().size();
        employment.setId(UUID.randomUUID().toString());

        // Create the Employment
        EmploymentDTO employmentDTO = employmentMapper.toDto(employment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmploymentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(employmentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Employment in the database
        List<Employment> employmentList = employmentRepository.findAll();
        assertThat(employmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamEmployment() throws Exception {
        int databaseSizeBeforeUpdate = employmentRepository.findAll().size();
        employment.setId(UUID.randomUUID().toString());

        // Create the Employment
        EmploymentDTO employmentDTO = employmentMapper.toDto(employment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmploymentMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(employmentDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Employment in the database
        List<Employment> employmentList = employmentRepository.findAll();
        assertThat(employmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteEmployment() throws Exception {
        // Initialize the database
        employmentRepository.save(employment);

        int databaseSizeBeforeDelete = employmentRepository.findAll().size();

        // Delete the employment
        restEmploymentMockMvc
            .perform(delete(ENTITY_API_URL_ID, employment.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Employment> employmentList = employmentRepository.findAll();
        assertThat(employmentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
