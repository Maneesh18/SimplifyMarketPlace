package com.simplify.marketplace.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.simplify.marketplace.IntegrationTest;
import com.simplify.marketplace.domain.Education;
import com.simplify.marketplace.repository.EducationRepository;
import com.simplify.marketplace.service.dto.EducationDTO;
import com.simplify.marketplace.service.mapper.EducationMapper;
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
 * Integration tests for the {@link EducationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EducationResourceIT {

    private static final String DEFAULT_DEGREE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DEGREE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_INSTITUTE = "AAAAAAAAAA";
    private static final String UPDATED_INSTITUTE = "BBBBBBBBBB";

    private static final Integer DEFAULT_YEAR_OF_PASSING = 1;
    private static final Integer UPDATED_YEAR_OF_PASSING = 2;

    private static final Float DEFAULT_MARKS = 1F;
    private static final Float UPDATED_MARKS = 2F;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_AT = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_UPDATED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATED_AT = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/educations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private EducationRepository educationRepository;

    @Autowired
    private EducationMapper educationMapper;

    @Autowired
    private MockMvc restEducationMockMvc;

    private Education education;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Education createEntity() {
        Education education = new Education()
            .degreeName(DEFAULT_DEGREE_NAME)
            .institute(DEFAULT_INSTITUTE)
            .yearOfPassing(DEFAULT_YEAR_OF_PASSING)
            .marks(DEFAULT_MARKS)
            .description(DEFAULT_DESCRIPTION)
            .createdBy(DEFAULT_CREATED_BY)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedAt(DEFAULT_UPDATED_AT);
        return education;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Education createUpdatedEntity() {
        Education education = new Education()
            .degreeName(UPDATED_DEGREE_NAME)
            .institute(UPDATED_INSTITUTE)
            .yearOfPassing(UPDATED_YEAR_OF_PASSING)
            .marks(UPDATED_MARKS)
            .description(UPDATED_DESCRIPTION)
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedAt(UPDATED_UPDATED_AT);
        return education;
    }

    @BeforeEach
    public void initTest() {
        educationRepository.deleteAll();
        education = createEntity();
    }

    @Test
    void createEducation() throws Exception {
        int databaseSizeBeforeCreate = educationRepository.findAll().size();
        // Create the Education
        EducationDTO educationDTO = educationMapper.toDto(education);
        restEducationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(educationDTO)))
            .andExpect(status().isCreated());

        // Validate the Education in the database
        List<Education> educationList = educationRepository.findAll();
        assertThat(educationList).hasSize(databaseSizeBeforeCreate + 1);
        Education testEducation = educationList.get(educationList.size() - 1);
        assertThat(testEducation.getDegreeName()).isEqualTo(DEFAULT_DEGREE_NAME);
        assertThat(testEducation.getInstitute()).isEqualTo(DEFAULT_INSTITUTE);
        assertThat(testEducation.getYearOfPassing()).isEqualTo(DEFAULT_YEAR_OF_PASSING);
        assertThat(testEducation.getMarks()).isEqualTo(DEFAULT_MARKS);
        assertThat(testEducation.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testEducation.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testEducation.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testEducation.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testEducation.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    void createEducationWithExistingId() throws Exception {
        // Create the Education with an existing ID
        education.setId("existing_id");
        EducationDTO educationDTO = educationMapper.toDto(education);

        int databaseSizeBeforeCreate = educationRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEducationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(educationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Education in the database
        List<Education> educationList = educationRepository.findAll();
        assertThat(educationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkDegreeNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = educationRepository.findAll().size();
        // set the field null
        education.setDegreeName(null);

        // Create the Education, which fails.
        EducationDTO educationDTO = educationMapper.toDto(education);

        restEducationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(educationDTO)))
            .andExpect(status().isBadRequest());

        List<Education> educationList = educationRepository.findAll();
        assertThat(educationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkInstituteIsRequired() throws Exception {
        int databaseSizeBeforeTest = educationRepository.findAll().size();
        // set the field null
        education.setInstitute(null);

        // Create the Education, which fails.
        EducationDTO educationDTO = educationMapper.toDto(education);

        restEducationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(educationDTO)))
            .andExpect(status().isBadRequest());

        List<Education> educationList = educationRepository.findAll();
        assertThat(educationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkYearOfPassingIsRequired() throws Exception {
        int databaseSizeBeforeTest = educationRepository.findAll().size();
        // set the field null
        education.setYearOfPassing(null);

        // Create the Education, which fails.
        EducationDTO educationDTO = educationMapper.toDto(education);

        restEducationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(educationDTO)))
            .andExpect(status().isBadRequest());

        List<Education> educationList = educationRepository.findAll();
        assertThat(educationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkMarksIsRequired() throws Exception {
        int databaseSizeBeforeTest = educationRepository.findAll().size();
        // set the field null
        education.setMarks(null);

        // Create the Education, which fails.
        EducationDTO educationDTO = educationMapper.toDto(education);

        restEducationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(educationDTO)))
            .andExpect(status().isBadRequest());

        List<Education> educationList = educationRepository.findAll();
        assertThat(educationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkCreatedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = educationRepository.findAll().size();
        // set the field null
        education.setCreatedBy(null);

        // Create the Education, which fails.
        EducationDTO educationDTO = educationMapper.toDto(education);

        restEducationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(educationDTO)))
            .andExpect(status().isBadRequest());

        List<Education> educationList = educationRepository.findAll();
        assertThat(educationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = educationRepository.findAll().size();
        // set the field null
        education.setCreatedAt(null);

        // Create the Education, which fails.
        EducationDTO educationDTO = educationMapper.toDto(education);

        restEducationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(educationDTO)))
            .andExpect(status().isBadRequest());

        List<Education> educationList = educationRepository.findAll();
        assertThat(educationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkUpdatedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = educationRepository.findAll().size();
        // set the field null
        education.setUpdatedBy(null);

        // Create the Education, which fails.
        EducationDTO educationDTO = educationMapper.toDto(education);

        restEducationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(educationDTO)))
            .andExpect(status().isBadRequest());

        List<Education> educationList = educationRepository.findAll();
        assertThat(educationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkUpdatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = educationRepository.findAll().size();
        // set the field null
        education.setUpdatedAt(null);

        // Create the Education, which fails.
        EducationDTO educationDTO = educationMapper.toDto(education);

        restEducationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(educationDTO)))
            .andExpect(status().isBadRequest());

        List<Education> educationList = educationRepository.findAll();
        assertThat(educationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllEducations() throws Exception {
        // Initialize the database
        educationRepository.save(education);

        // Get all the educationList
        restEducationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(education.getId())))
            .andExpect(jsonPath("$.[*].degreeName").value(hasItem(DEFAULT_DEGREE_NAME)))
            .andExpect(jsonPath("$.[*].institute").value(hasItem(DEFAULT_INSTITUTE)))
            .andExpect(jsonPath("$.[*].yearOfPassing").value(hasItem(DEFAULT_YEAR_OF_PASSING)))
            .andExpect(jsonPath("$.[*].marks").value(hasItem(DEFAULT_MARKS.doubleValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));
    }

    @Test
    void getEducation() throws Exception {
        // Initialize the database
        educationRepository.save(education);

        // Get the education
        restEducationMockMvc
            .perform(get(ENTITY_API_URL_ID, education.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(education.getId()))
            .andExpect(jsonPath("$.degreeName").value(DEFAULT_DEGREE_NAME))
            .andExpect(jsonPath("$.institute").value(DEFAULT_INSTITUTE))
            .andExpect(jsonPath("$.yearOfPassing").value(DEFAULT_YEAR_OF_PASSING))
            .andExpect(jsonPath("$.marks").value(DEFAULT_MARKS.doubleValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()));
    }

    @Test
    void getNonExistingEducation() throws Exception {
        // Get the education
        restEducationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putNewEducation() throws Exception {
        // Initialize the database
        educationRepository.save(education);

        int databaseSizeBeforeUpdate = educationRepository.findAll().size();

        // Update the education
        Education updatedEducation = educationRepository.findById(education.getId()).get();
        updatedEducation
            .degreeName(UPDATED_DEGREE_NAME)
            .institute(UPDATED_INSTITUTE)
            .yearOfPassing(UPDATED_YEAR_OF_PASSING)
            .marks(UPDATED_MARKS)
            .description(UPDATED_DESCRIPTION)
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedAt(UPDATED_UPDATED_AT);
        EducationDTO educationDTO = educationMapper.toDto(updatedEducation);

        restEducationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, educationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(educationDTO))
            )
            .andExpect(status().isOk());

        // Validate the Education in the database
        List<Education> educationList = educationRepository.findAll();
        assertThat(educationList).hasSize(databaseSizeBeforeUpdate);
        Education testEducation = educationList.get(educationList.size() - 1);
        assertThat(testEducation.getDegreeName()).isEqualTo(UPDATED_DEGREE_NAME);
        assertThat(testEducation.getInstitute()).isEqualTo(UPDATED_INSTITUTE);
        assertThat(testEducation.getYearOfPassing()).isEqualTo(UPDATED_YEAR_OF_PASSING);
        assertThat(testEducation.getMarks()).isEqualTo(UPDATED_MARKS);
        assertThat(testEducation.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testEducation.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testEducation.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testEducation.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testEducation.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    void putNonExistingEducation() throws Exception {
        int databaseSizeBeforeUpdate = educationRepository.findAll().size();
        education.setId(UUID.randomUUID().toString());

        // Create the Education
        EducationDTO educationDTO = educationMapper.toDto(education);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEducationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, educationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(educationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Education in the database
        List<Education> educationList = educationRepository.findAll();
        assertThat(educationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchEducation() throws Exception {
        int databaseSizeBeforeUpdate = educationRepository.findAll().size();
        education.setId(UUID.randomUUID().toString());

        // Create the Education
        EducationDTO educationDTO = educationMapper.toDto(education);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEducationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(educationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Education in the database
        List<Education> educationList = educationRepository.findAll();
        assertThat(educationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamEducation() throws Exception {
        int databaseSizeBeforeUpdate = educationRepository.findAll().size();
        education.setId(UUID.randomUUID().toString());

        // Create the Education
        EducationDTO educationDTO = educationMapper.toDto(education);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEducationMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(educationDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Education in the database
        List<Education> educationList = educationRepository.findAll();
        assertThat(educationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateEducationWithPatch() throws Exception {
        // Initialize the database
        educationRepository.save(education);

        int databaseSizeBeforeUpdate = educationRepository.findAll().size();

        // Update the education using partial update
        Education partialUpdatedEducation = new Education();
        partialUpdatedEducation.setId(education.getId());

        partialUpdatedEducation
            .marks(UPDATED_MARKS)
            .description(UPDATED_DESCRIPTION)
            .createdAt(UPDATED_CREATED_AT)
            .updatedBy(UPDATED_UPDATED_BY);

        restEducationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEducation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEducation))
            )
            .andExpect(status().isOk());

        // Validate the Education in the database
        List<Education> educationList = educationRepository.findAll();
        assertThat(educationList).hasSize(databaseSizeBeforeUpdate);
        Education testEducation = educationList.get(educationList.size() - 1);
        assertThat(testEducation.getDegreeName()).isEqualTo(DEFAULT_DEGREE_NAME);
        assertThat(testEducation.getInstitute()).isEqualTo(DEFAULT_INSTITUTE);
        assertThat(testEducation.getYearOfPassing()).isEqualTo(DEFAULT_YEAR_OF_PASSING);
        assertThat(testEducation.getMarks()).isEqualTo(UPDATED_MARKS);
        assertThat(testEducation.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testEducation.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testEducation.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testEducation.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testEducation.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    void fullUpdateEducationWithPatch() throws Exception {
        // Initialize the database
        educationRepository.save(education);

        int databaseSizeBeforeUpdate = educationRepository.findAll().size();

        // Update the education using partial update
        Education partialUpdatedEducation = new Education();
        partialUpdatedEducation.setId(education.getId());

        partialUpdatedEducation
            .degreeName(UPDATED_DEGREE_NAME)
            .institute(UPDATED_INSTITUTE)
            .yearOfPassing(UPDATED_YEAR_OF_PASSING)
            .marks(UPDATED_MARKS)
            .description(UPDATED_DESCRIPTION)
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedAt(UPDATED_UPDATED_AT);

        restEducationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEducation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEducation))
            )
            .andExpect(status().isOk());

        // Validate the Education in the database
        List<Education> educationList = educationRepository.findAll();
        assertThat(educationList).hasSize(databaseSizeBeforeUpdate);
        Education testEducation = educationList.get(educationList.size() - 1);
        assertThat(testEducation.getDegreeName()).isEqualTo(UPDATED_DEGREE_NAME);
        assertThat(testEducation.getInstitute()).isEqualTo(UPDATED_INSTITUTE);
        assertThat(testEducation.getYearOfPassing()).isEqualTo(UPDATED_YEAR_OF_PASSING);
        assertThat(testEducation.getMarks()).isEqualTo(UPDATED_MARKS);
        assertThat(testEducation.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testEducation.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testEducation.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testEducation.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testEducation.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    void patchNonExistingEducation() throws Exception {
        int databaseSizeBeforeUpdate = educationRepository.findAll().size();
        education.setId(UUID.randomUUID().toString());

        // Create the Education
        EducationDTO educationDTO = educationMapper.toDto(education);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEducationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, educationDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(educationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Education in the database
        List<Education> educationList = educationRepository.findAll();
        assertThat(educationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchEducation() throws Exception {
        int databaseSizeBeforeUpdate = educationRepository.findAll().size();
        education.setId(UUID.randomUUID().toString());

        // Create the Education
        EducationDTO educationDTO = educationMapper.toDto(education);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEducationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(educationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Education in the database
        List<Education> educationList = educationRepository.findAll();
        assertThat(educationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamEducation() throws Exception {
        int databaseSizeBeforeUpdate = educationRepository.findAll().size();
        education.setId(UUID.randomUUID().toString());

        // Create the Education
        EducationDTO educationDTO = educationMapper.toDto(education);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEducationMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(educationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Education in the database
        List<Education> educationList = educationRepository.findAll();
        assertThat(educationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteEducation() throws Exception {
        // Initialize the database
        educationRepository.save(education);

        int databaseSizeBeforeDelete = educationRepository.findAll().size();

        // Delete the education
        restEducationMockMvc
            .perform(delete(ENTITY_API_URL_ID, education.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Education> educationList = educationRepository.findAll();
        assertThat(educationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
