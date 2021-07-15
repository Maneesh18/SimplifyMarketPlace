package com.simplify.marketplace.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.simplify.marketplace.IntegrationTest;
import com.simplify.marketplace.domain.OTPAttempt;
import com.simplify.marketplace.repository.OTPAttemptRepository;
import com.simplify.marketplace.service.dto.OTPAttemptDTO;
import com.simplify.marketplace.service.mapper.OTPAttemptMapper;
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
 * Integration tests for the {@link OTPAttemptResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OTPAttemptResourceIT {

    private static final Integer DEFAULT_OTP_ATTEMPT_ID = 1;
    private static final Integer UPDATED_OTP_ATTEMPT_ID = 2;

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_IP = "AAAAAAAAAA";
    private static final String UPDATED_IP = "BBBBBBBBBB";

    private static final String DEFAULT_COOOKIE = "AAAAAAAAAA";
    private static final String UPDATED_COOOKIE = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_AT = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/otp-attempts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private OTPAttemptRepository oTPAttemptRepository;

    @Autowired
    private OTPAttemptMapper oTPAttemptMapper;

    @Autowired
    private MockMvc restOTPAttemptMockMvc;

    private OTPAttempt oTPAttempt;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OTPAttempt createEntity() {
        OTPAttempt oTPAttempt = new OTPAttempt()
            .otpAttemptId(DEFAULT_OTP_ATTEMPT_ID)
            .email(DEFAULT_EMAIL)
            .phone(DEFAULT_PHONE)
            .ip(DEFAULT_IP)
            .coookie(DEFAULT_COOOKIE)
            .createdBy(DEFAULT_CREATED_BY)
            .createdAt(DEFAULT_CREATED_AT);
        return oTPAttempt;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OTPAttempt createUpdatedEntity() {
        OTPAttempt oTPAttempt = new OTPAttempt()
            .otpAttemptId(UPDATED_OTP_ATTEMPT_ID)
            .email(UPDATED_EMAIL)
            .phone(UPDATED_PHONE)
            .ip(UPDATED_IP)
            .coookie(UPDATED_COOOKIE)
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT);
        return oTPAttempt;
    }

    @BeforeEach
    public void initTest() {
        oTPAttemptRepository.deleteAll();
        oTPAttempt = createEntity();
    }

    @Test
    void createOTPAttempt() throws Exception {
        int databaseSizeBeforeCreate = oTPAttemptRepository.findAll().size();
        // Create the OTPAttempt
        OTPAttemptDTO oTPAttemptDTO = oTPAttemptMapper.toDto(oTPAttempt);
        restOTPAttemptMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(oTPAttemptDTO)))
            .andExpect(status().isCreated());

        // Validate the OTPAttempt in the database
        List<OTPAttempt> oTPAttemptList = oTPAttemptRepository.findAll();
        assertThat(oTPAttemptList).hasSize(databaseSizeBeforeCreate + 1);
        OTPAttempt testOTPAttempt = oTPAttemptList.get(oTPAttemptList.size() - 1);
        assertThat(testOTPAttempt.getOtpAttemptId()).isEqualTo(DEFAULT_OTP_ATTEMPT_ID);
        assertThat(testOTPAttempt.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testOTPAttempt.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testOTPAttempt.getIp()).isEqualTo(DEFAULT_IP);
        assertThat(testOTPAttempt.getCoookie()).isEqualTo(DEFAULT_COOOKIE);
        assertThat(testOTPAttempt.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testOTPAttempt.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
    }

    @Test
    void createOTPAttemptWithExistingId() throws Exception {
        // Create the OTPAttempt with an existing ID
        oTPAttempt.setId("existing_id");
        OTPAttemptDTO oTPAttemptDTO = oTPAttemptMapper.toDto(oTPAttempt);

        int databaseSizeBeforeCreate = oTPAttemptRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOTPAttemptMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(oTPAttemptDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OTPAttempt in the database
        List<OTPAttempt> oTPAttemptList = oTPAttemptRepository.findAll();
        assertThat(oTPAttemptList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkOtpAttemptIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = oTPAttemptRepository.findAll().size();
        // set the field null
        oTPAttempt.setOtpAttemptId(null);

        // Create the OTPAttempt, which fails.
        OTPAttemptDTO oTPAttemptDTO = oTPAttemptMapper.toDto(oTPAttempt);

        restOTPAttemptMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(oTPAttemptDTO)))
            .andExpect(status().isBadRequest());

        List<OTPAttempt> oTPAttemptList = oTPAttemptRepository.findAll();
        assertThat(oTPAttemptList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = oTPAttemptRepository.findAll().size();
        // set the field null
        oTPAttempt.setEmail(null);

        // Create the OTPAttempt, which fails.
        OTPAttemptDTO oTPAttemptDTO = oTPAttemptMapper.toDto(oTPAttempt);

        restOTPAttemptMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(oTPAttemptDTO)))
            .andExpect(status().isBadRequest());

        List<OTPAttempt> oTPAttemptList = oTPAttemptRepository.findAll();
        assertThat(oTPAttemptList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkPhoneIsRequired() throws Exception {
        int databaseSizeBeforeTest = oTPAttemptRepository.findAll().size();
        // set the field null
        oTPAttempt.setPhone(null);

        // Create the OTPAttempt, which fails.
        OTPAttemptDTO oTPAttemptDTO = oTPAttemptMapper.toDto(oTPAttempt);

        restOTPAttemptMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(oTPAttemptDTO)))
            .andExpect(status().isBadRequest());

        List<OTPAttempt> oTPAttemptList = oTPAttemptRepository.findAll();
        assertThat(oTPAttemptList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkIpIsRequired() throws Exception {
        int databaseSizeBeforeTest = oTPAttemptRepository.findAll().size();
        // set the field null
        oTPAttempt.setIp(null);

        // Create the OTPAttempt, which fails.
        OTPAttemptDTO oTPAttemptDTO = oTPAttemptMapper.toDto(oTPAttempt);

        restOTPAttemptMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(oTPAttemptDTO)))
            .andExpect(status().isBadRequest());

        List<OTPAttempt> oTPAttemptList = oTPAttemptRepository.findAll();
        assertThat(oTPAttemptList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkCoookieIsRequired() throws Exception {
        int databaseSizeBeforeTest = oTPAttemptRepository.findAll().size();
        // set the field null
        oTPAttempt.setCoookie(null);

        // Create the OTPAttempt, which fails.
        OTPAttemptDTO oTPAttemptDTO = oTPAttemptMapper.toDto(oTPAttempt);

        restOTPAttemptMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(oTPAttemptDTO)))
            .andExpect(status().isBadRequest());

        List<OTPAttempt> oTPAttemptList = oTPAttemptRepository.findAll();
        assertThat(oTPAttemptList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkCreatedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = oTPAttemptRepository.findAll().size();
        // set the field null
        oTPAttempt.setCreatedBy(null);

        // Create the OTPAttempt, which fails.
        OTPAttemptDTO oTPAttemptDTO = oTPAttemptMapper.toDto(oTPAttempt);

        restOTPAttemptMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(oTPAttemptDTO)))
            .andExpect(status().isBadRequest());

        List<OTPAttempt> oTPAttemptList = oTPAttemptRepository.findAll();
        assertThat(oTPAttemptList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = oTPAttemptRepository.findAll().size();
        // set the field null
        oTPAttempt.setCreatedAt(null);

        // Create the OTPAttempt, which fails.
        OTPAttemptDTO oTPAttemptDTO = oTPAttemptMapper.toDto(oTPAttempt);

        restOTPAttemptMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(oTPAttemptDTO)))
            .andExpect(status().isBadRequest());

        List<OTPAttempt> oTPAttemptList = oTPAttemptRepository.findAll();
        assertThat(oTPAttemptList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllOTPAttempts() throws Exception {
        // Initialize the database
        oTPAttemptRepository.save(oTPAttempt);

        // Get all the oTPAttemptList
        restOTPAttemptMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(oTPAttempt.getId())))
            .andExpect(jsonPath("$.[*].otpAttemptId").value(hasItem(DEFAULT_OTP_ATTEMPT_ID)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].ip").value(hasItem(DEFAULT_IP)))
            .andExpect(jsonPath("$.[*].coookie").value(hasItem(DEFAULT_COOOKIE)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())));
    }

    @Test
    void getOTPAttempt() throws Exception {
        // Initialize the database
        oTPAttemptRepository.save(oTPAttempt);

        // Get the oTPAttempt
        restOTPAttemptMockMvc
            .perform(get(ENTITY_API_URL_ID, oTPAttempt.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(oTPAttempt.getId()))
            .andExpect(jsonPath("$.otpAttemptId").value(DEFAULT_OTP_ATTEMPT_ID))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE))
            .andExpect(jsonPath("$.ip").value(DEFAULT_IP))
            .andExpect(jsonPath("$.coookie").value(DEFAULT_COOOKIE))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()));
    }

    @Test
    void getNonExistingOTPAttempt() throws Exception {
        // Get the oTPAttempt
        restOTPAttemptMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putNewOTPAttempt() throws Exception {
        // Initialize the database
        oTPAttemptRepository.save(oTPAttempt);

        int databaseSizeBeforeUpdate = oTPAttemptRepository.findAll().size();

        // Update the oTPAttempt
        OTPAttempt updatedOTPAttempt = oTPAttemptRepository.findById(oTPAttempt.getId()).get();
        updatedOTPAttempt
            .otpAttemptId(UPDATED_OTP_ATTEMPT_ID)
            .email(UPDATED_EMAIL)
            .phone(UPDATED_PHONE)
            .ip(UPDATED_IP)
            .coookie(UPDATED_COOOKIE)
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT);
        OTPAttemptDTO oTPAttemptDTO = oTPAttemptMapper.toDto(updatedOTPAttempt);

        restOTPAttemptMockMvc
            .perform(
                put(ENTITY_API_URL_ID, oTPAttemptDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(oTPAttemptDTO))
            )
            .andExpect(status().isOk());

        // Validate the OTPAttempt in the database
        List<OTPAttempt> oTPAttemptList = oTPAttemptRepository.findAll();
        assertThat(oTPAttemptList).hasSize(databaseSizeBeforeUpdate);
        OTPAttempt testOTPAttempt = oTPAttemptList.get(oTPAttemptList.size() - 1);
        assertThat(testOTPAttempt.getOtpAttemptId()).isEqualTo(UPDATED_OTP_ATTEMPT_ID);
        assertThat(testOTPAttempt.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testOTPAttempt.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testOTPAttempt.getIp()).isEqualTo(UPDATED_IP);
        assertThat(testOTPAttempt.getCoookie()).isEqualTo(UPDATED_COOOKIE);
        assertThat(testOTPAttempt.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testOTPAttempt.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
    }

    @Test
    void putNonExistingOTPAttempt() throws Exception {
        int databaseSizeBeforeUpdate = oTPAttemptRepository.findAll().size();
        oTPAttempt.setId(UUID.randomUUID().toString());

        // Create the OTPAttempt
        OTPAttemptDTO oTPAttemptDTO = oTPAttemptMapper.toDto(oTPAttempt);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOTPAttemptMockMvc
            .perform(
                put(ENTITY_API_URL_ID, oTPAttemptDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(oTPAttemptDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OTPAttempt in the database
        List<OTPAttempt> oTPAttemptList = oTPAttemptRepository.findAll();
        assertThat(oTPAttemptList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchOTPAttempt() throws Exception {
        int databaseSizeBeforeUpdate = oTPAttemptRepository.findAll().size();
        oTPAttempt.setId(UUID.randomUUID().toString());

        // Create the OTPAttempt
        OTPAttemptDTO oTPAttemptDTO = oTPAttemptMapper.toDto(oTPAttempt);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOTPAttemptMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(oTPAttemptDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OTPAttempt in the database
        List<OTPAttempt> oTPAttemptList = oTPAttemptRepository.findAll();
        assertThat(oTPAttemptList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamOTPAttempt() throws Exception {
        int databaseSizeBeforeUpdate = oTPAttemptRepository.findAll().size();
        oTPAttempt.setId(UUID.randomUUID().toString());

        // Create the OTPAttempt
        OTPAttemptDTO oTPAttemptDTO = oTPAttemptMapper.toDto(oTPAttempt);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOTPAttemptMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(oTPAttemptDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the OTPAttempt in the database
        List<OTPAttempt> oTPAttemptList = oTPAttemptRepository.findAll();
        assertThat(oTPAttemptList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateOTPAttemptWithPatch() throws Exception {
        // Initialize the database
        oTPAttemptRepository.save(oTPAttempt);

        int databaseSizeBeforeUpdate = oTPAttemptRepository.findAll().size();

        // Update the oTPAttempt using partial update
        OTPAttempt partialUpdatedOTPAttempt = new OTPAttempt();
        partialUpdatedOTPAttempt.setId(oTPAttempt.getId());

        partialUpdatedOTPAttempt
            .email(UPDATED_EMAIL)
            .phone(UPDATED_PHONE)
            .ip(UPDATED_IP)
            .coookie(UPDATED_COOOKIE)
            .createdAt(UPDATED_CREATED_AT);

        restOTPAttemptMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOTPAttempt.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOTPAttempt))
            )
            .andExpect(status().isOk());

        // Validate the OTPAttempt in the database
        List<OTPAttempt> oTPAttemptList = oTPAttemptRepository.findAll();
        assertThat(oTPAttemptList).hasSize(databaseSizeBeforeUpdate);
        OTPAttempt testOTPAttempt = oTPAttemptList.get(oTPAttemptList.size() - 1);
        assertThat(testOTPAttempt.getOtpAttemptId()).isEqualTo(DEFAULT_OTP_ATTEMPT_ID);
        assertThat(testOTPAttempt.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testOTPAttempt.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testOTPAttempt.getIp()).isEqualTo(UPDATED_IP);
        assertThat(testOTPAttempt.getCoookie()).isEqualTo(UPDATED_COOOKIE);
        assertThat(testOTPAttempt.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testOTPAttempt.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
    }

    @Test
    void fullUpdateOTPAttemptWithPatch() throws Exception {
        // Initialize the database
        oTPAttemptRepository.save(oTPAttempt);

        int databaseSizeBeforeUpdate = oTPAttemptRepository.findAll().size();

        // Update the oTPAttempt using partial update
        OTPAttempt partialUpdatedOTPAttempt = new OTPAttempt();
        partialUpdatedOTPAttempt.setId(oTPAttempt.getId());

        partialUpdatedOTPAttempt
            .otpAttemptId(UPDATED_OTP_ATTEMPT_ID)
            .email(UPDATED_EMAIL)
            .phone(UPDATED_PHONE)
            .ip(UPDATED_IP)
            .coookie(UPDATED_COOOKIE)
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT);

        restOTPAttemptMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOTPAttempt.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOTPAttempt))
            )
            .andExpect(status().isOk());

        // Validate the OTPAttempt in the database
        List<OTPAttempt> oTPAttemptList = oTPAttemptRepository.findAll();
        assertThat(oTPAttemptList).hasSize(databaseSizeBeforeUpdate);
        OTPAttempt testOTPAttempt = oTPAttemptList.get(oTPAttemptList.size() - 1);
        assertThat(testOTPAttempt.getOtpAttemptId()).isEqualTo(UPDATED_OTP_ATTEMPT_ID);
        assertThat(testOTPAttempt.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testOTPAttempt.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testOTPAttempt.getIp()).isEqualTo(UPDATED_IP);
        assertThat(testOTPAttempt.getCoookie()).isEqualTo(UPDATED_COOOKIE);
        assertThat(testOTPAttempt.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testOTPAttempt.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
    }

    @Test
    void patchNonExistingOTPAttempt() throws Exception {
        int databaseSizeBeforeUpdate = oTPAttemptRepository.findAll().size();
        oTPAttempt.setId(UUID.randomUUID().toString());

        // Create the OTPAttempt
        OTPAttemptDTO oTPAttemptDTO = oTPAttemptMapper.toDto(oTPAttempt);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOTPAttemptMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, oTPAttemptDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(oTPAttemptDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OTPAttempt in the database
        List<OTPAttempt> oTPAttemptList = oTPAttemptRepository.findAll();
        assertThat(oTPAttemptList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchOTPAttempt() throws Exception {
        int databaseSizeBeforeUpdate = oTPAttemptRepository.findAll().size();
        oTPAttempt.setId(UUID.randomUUID().toString());

        // Create the OTPAttempt
        OTPAttemptDTO oTPAttemptDTO = oTPAttemptMapper.toDto(oTPAttempt);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOTPAttemptMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(oTPAttemptDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OTPAttempt in the database
        List<OTPAttempt> oTPAttemptList = oTPAttemptRepository.findAll();
        assertThat(oTPAttemptList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamOTPAttempt() throws Exception {
        int databaseSizeBeforeUpdate = oTPAttemptRepository.findAll().size();
        oTPAttempt.setId(UUID.randomUUID().toString());

        // Create the OTPAttempt
        OTPAttemptDTO oTPAttemptDTO = oTPAttemptMapper.toDto(oTPAttempt);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOTPAttemptMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(oTPAttemptDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OTPAttempt in the database
        List<OTPAttempt> oTPAttemptList = oTPAttemptRepository.findAll();
        assertThat(oTPAttemptList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteOTPAttempt() throws Exception {
        // Initialize the database
        oTPAttemptRepository.save(oTPAttempt);

        int databaseSizeBeforeDelete = oTPAttemptRepository.findAll().size();

        // Delete the oTPAttempt
        restOTPAttemptMockMvc
            .perform(delete(ENTITY_API_URL_ID, oTPAttempt.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OTPAttempt> oTPAttemptList = oTPAttemptRepository.findAll();
        assertThat(oTPAttemptList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
