package com.simplify.marketplace.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.simplify.marketplace.IntegrationTest;
import com.simplify.marketplace.domain.OTP;
import com.simplify.marketplace.domain.enumeration.OtpStatus;
import com.simplify.marketplace.domain.enumeration.OtpType;
import com.simplify.marketplace.repository.OTPRepository;
import com.simplify.marketplace.service.dto.OTPDTO;
import com.simplify.marketplace.service.mapper.OTPMapper;
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
 * Integration tests for the {@link OTPResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OTPResourceIT {

    private static final Integer DEFAULT_OTP_ID = 1;
    private static final Integer UPDATED_OTP_ID = 2;

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final OtpType DEFAULT_TYPE = OtpType.Email;
    private static final OtpType UPDATED_TYPE = OtpType.Phone;

    private static final LocalDate DEFAULT_EXPIRY_TIME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_EXPIRY_TIME = LocalDate.now(ZoneId.systemDefault());

    private static final OtpStatus DEFAULT_STATUS = OtpStatus.Init;
    private static final OtpStatus UPDATED_STATUS = OtpStatus.Verified;

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_AT = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_UPDATED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATED_AT = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/otps";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private OTPRepository oTPRepository;

    @Autowired
    private OTPMapper oTPMapper;

    @Autowired
    private MockMvc restOTPMockMvc;

    private OTP oTP;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OTP createEntity() {
        OTP oTP = new OTP()
            .otpId(DEFAULT_OTP_ID)
            .email(DEFAULT_EMAIL)
            .phone(DEFAULT_PHONE)
            .type(DEFAULT_TYPE)
            .expiryTime(DEFAULT_EXPIRY_TIME)
            .status(DEFAULT_STATUS)
            .createdBy(DEFAULT_CREATED_BY)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedAt(DEFAULT_UPDATED_AT);
        return oTP;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OTP createUpdatedEntity() {
        OTP oTP = new OTP()
            .otpId(UPDATED_OTP_ID)
            .email(UPDATED_EMAIL)
            .phone(UPDATED_PHONE)
            .type(UPDATED_TYPE)
            .expiryTime(UPDATED_EXPIRY_TIME)
            .status(UPDATED_STATUS)
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedAt(UPDATED_UPDATED_AT);
        return oTP;
    }

    @BeforeEach
    public void initTest() {
        oTPRepository.deleteAll();
        oTP = createEntity();
    }

    @Test
    void createOTP() throws Exception {
        int databaseSizeBeforeCreate = oTPRepository.findAll().size();
        // Create the OTP
        OTPDTO oTPDTO = oTPMapper.toDto(oTP);
        restOTPMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(oTPDTO)))
            .andExpect(status().isCreated());

        // Validate the OTP in the database
        List<OTP> oTPList = oTPRepository.findAll();
        assertThat(oTPList).hasSize(databaseSizeBeforeCreate + 1);
        OTP testOTP = oTPList.get(oTPList.size() - 1);
        assertThat(testOTP.getOtpId()).isEqualTo(DEFAULT_OTP_ID);
        assertThat(testOTP.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testOTP.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testOTP.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testOTP.getExpiryTime()).isEqualTo(DEFAULT_EXPIRY_TIME);
        assertThat(testOTP.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testOTP.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testOTP.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testOTP.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testOTP.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    void createOTPWithExistingId() throws Exception {
        // Create the OTP with an existing ID
        oTP.setId("existing_id");
        OTPDTO oTPDTO = oTPMapper.toDto(oTP);

        int databaseSizeBeforeCreate = oTPRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOTPMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(oTPDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OTP in the database
        List<OTP> oTPList = oTPRepository.findAll();
        assertThat(oTPList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkOtpIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = oTPRepository.findAll().size();
        // set the field null
        oTP.setOtpId(null);

        // Create the OTP, which fails.
        OTPDTO oTPDTO = oTPMapper.toDto(oTP);

        restOTPMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(oTPDTO)))
            .andExpect(status().isBadRequest());

        List<OTP> oTPList = oTPRepository.findAll();
        assertThat(oTPList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = oTPRepository.findAll().size();
        // set the field null
        oTP.setEmail(null);

        // Create the OTP, which fails.
        OTPDTO oTPDTO = oTPMapper.toDto(oTP);

        restOTPMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(oTPDTO)))
            .andExpect(status().isBadRequest());

        List<OTP> oTPList = oTPRepository.findAll();
        assertThat(oTPList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkPhoneIsRequired() throws Exception {
        int databaseSizeBeforeTest = oTPRepository.findAll().size();
        // set the field null
        oTP.setPhone(null);

        // Create the OTP, which fails.
        OTPDTO oTPDTO = oTPMapper.toDto(oTP);

        restOTPMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(oTPDTO)))
            .andExpect(status().isBadRequest());

        List<OTP> oTPList = oTPRepository.findAll();
        assertThat(oTPList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkExpiryTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = oTPRepository.findAll().size();
        // set the field null
        oTP.setExpiryTime(null);

        // Create the OTP, which fails.
        OTPDTO oTPDTO = oTPMapper.toDto(oTP);

        restOTPMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(oTPDTO)))
            .andExpect(status().isBadRequest());

        List<OTP> oTPList = oTPRepository.findAll();
        assertThat(oTPList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkCreatedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = oTPRepository.findAll().size();
        // set the field null
        oTP.setCreatedBy(null);

        // Create the OTP, which fails.
        OTPDTO oTPDTO = oTPMapper.toDto(oTP);

        restOTPMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(oTPDTO)))
            .andExpect(status().isBadRequest());

        List<OTP> oTPList = oTPRepository.findAll();
        assertThat(oTPList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = oTPRepository.findAll().size();
        // set the field null
        oTP.setCreatedAt(null);

        // Create the OTP, which fails.
        OTPDTO oTPDTO = oTPMapper.toDto(oTP);

        restOTPMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(oTPDTO)))
            .andExpect(status().isBadRequest());

        List<OTP> oTPList = oTPRepository.findAll();
        assertThat(oTPList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkUpdatedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = oTPRepository.findAll().size();
        // set the field null
        oTP.setUpdatedBy(null);

        // Create the OTP, which fails.
        OTPDTO oTPDTO = oTPMapper.toDto(oTP);

        restOTPMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(oTPDTO)))
            .andExpect(status().isBadRequest());

        List<OTP> oTPList = oTPRepository.findAll();
        assertThat(oTPList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkUpdatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = oTPRepository.findAll().size();
        // set the field null
        oTP.setUpdatedAt(null);

        // Create the OTP, which fails.
        OTPDTO oTPDTO = oTPMapper.toDto(oTP);

        restOTPMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(oTPDTO)))
            .andExpect(status().isBadRequest());

        List<OTP> oTPList = oTPRepository.findAll();
        assertThat(oTPList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllOTPS() throws Exception {
        // Initialize the database
        oTPRepository.save(oTP);

        // Get all the oTPList
        restOTPMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(oTP.getId())))
            .andExpect(jsonPath("$.[*].otpId").value(hasItem(DEFAULT_OTP_ID)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].expiryTime").value(hasItem(DEFAULT_EXPIRY_TIME.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));
    }

    @Test
    void getOTP() throws Exception {
        // Initialize the database
        oTPRepository.save(oTP);

        // Get the oTP
        restOTPMockMvc
            .perform(get(ENTITY_API_URL_ID, oTP.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(oTP.getId()))
            .andExpect(jsonPath("$.otpId").value(DEFAULT_OTP_ID))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.expiryTime").value(DEFAULT_EXPIRY_TIME.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()));
    }

    @Test
    void getNonExistingOTP() throws Exception {
        // Get the oTP
        restOTPMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putNewOTP() throws Exception {
        // Initialize the database
        oTPRepository.save(oTP);

        int databaseSizeBeforeUpdate = oTPRepository.findAll().size();

        // Update the oTP
        OTP updatedOTP = oTPRepository.findById(oTP.getId()).get();
        updatedOTP
            .otpId(UPDATED_OTP_ID)
            .email(UPDATED_EMAIL)
            .phone(UPDATED_PHONE)
            .type(UPDATED_TYPE)
            .expiryTime(UPDATED_EXPIRY_TIME)
            .status(UPDATED_STATUS)
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedAt(UPDATED_UPDATED_AT);
        OTPDTO oTPDTO = oTPMapper.toDto(updatedOTP);

        restOTPMockMvc
            .perform(
                put(ENTITY_API_URL_ID, oTPDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(oTPDTO))
            )
            .andExpect(status().isOk());

        // Validate the OTP in the database
        List<OTP> oTPList = oTPRepository.findAll();
        assertThat(oTPList).hasSize(databaseSizeBeforeUpdate);
        OTP testOTP = oTPList.get(oTPList.size() - 1);
        assertThat(testOTP.getOtpId()).isEqualTo(UPDATED_OTP_ID);
        assertThat(testOTP.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testOTP.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testOTP.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testOTP.getExpiryTime()).isEqualTo(UPDATED_EXPIRY_TIME);
        assertThat(testOTP.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testOTP.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testOTP.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testOTP.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testOTP.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    void putNonExistingOTP() throws Exception {
        int databaseSizeBeforeUpdate = oTPRepository.findAll().size();
        oTP.setId(UUID.randomUUID().toString());

        // Create the OTP
        OTPDTO oTPDTO = oTPMapper.toDto(oTP);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOTPMockMvc
            .perform(
                put(ENTITY_API_URL_ID, oTPDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(oTPDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OTP in the database
        List<OTP> oTPList = oTPRepository.findAll();
        assertThat(oTPList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchOTP() throws Exception {
        int databaseSizeBeforeUpdate = oTPRepository.findAll().size();
        oTP.setId(UUID.randomUUID().toString());

        // Create the OTP
        OTPDTO oTPDTO = oTPMapper.toDto(oTP);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOTPMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(oTPDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OTP in the database
        List<OTP> oTPList = oTPRepository.findAll();
        assertThat(oTPList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamOTP() throws Exception {
        int databaseSizeBeforeUpdate = oTPRepository.findAll().size();
        oTP.setId(UUID.randomUUID().toString());

        // Create the OTP
        OTPDTO oTPDTO = oTPMapper.toDto(oTP);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOTPMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(oTPDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the OTP in the database
        List<OTP> oTPList = oTPRepository.findAll();
        assertThat(oTPList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateOTPWithPatch() throws Exception {
        // Initialize the database
        oTPRepository.save(oTP);

        int databaseSizeBeforeUpdate = oTPRepository.findAll().size();

        // Update the oTP using partial update
        OTP partialUpdatedOTP = new OTP();
        partialUpdatedOTP.setId(oTP.getId());

        partialUpdatedOTP.phone(UPDATED_PHONE).type(UPDATED_TYPE).expiryTime(UPDATED_EXPIRY_TIME).updatedAt(UPDATED_UPDATED_AT);

        restOTPMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOTP.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOTP))
            )
            .andExpect(status().isOk());

        // Validate the OTP in the database
        List<OTP> oTPList = oTPRepository.findAll();
        assertThat(oTPList).hasSize(databaseSizeBeforeUpdate);
        OTP testOTP = oTPList.get(oTPList.size() - 1);
        assertThat(testOTP.getOtpId()).isEqualTo(DEFAULT_OTP_ID);
        assertThat(testOTP.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testOTP.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testOTP.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testOTP.getExpiryTime()).isEqualTo(UPDATED_EXPIRY_TIME);
        assertThat(testOTP.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testOTP.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testOTP.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testOTP.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testOTP.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    void fullUpdateOTPWithPatch() throws Exception {
        // Initialize the database
        oTPRepository.save(oTP);

        int databaseSizeBeforeUpdate = oTPRepository.findAll().size();

        // Update the oTP using partial update
        OTP partialUpdatedOTP = new OTP();
        partialUpdatedOTP.setId(oTP.getId());

        partialUpdatedOTP
            .otpId(UPDATED_OTP_ID)
            .email(UPDATED_EMAIL)
            .phone(UPDATED_PHONE)
            .type(UPDATED_TYPE)
            .expiryTime(UPDATED_EXPIRY_TIME)
            .status(UPDATED_STATUS)
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedAt(UPDATED_UPDATED_AT);

        restOTPMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOTP.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOTP))
            )
            .andExpect(status().isOk());

        // Validate the OTP in the database
        List<OTP> oTPList = oTPRepository.findAll();
        assertThat(oTPList).hasSize(databaseSizeBeforeUpdate);
        OTP testOTP = oTPList.get(oTPList.size() - 1);
        assertThat(testOTP.getOtpId()).isEqualTo(UPDATED_OTP_ID);
        assertThat(testOTP.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testOTP.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testOTP.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testOTP.getExpiryTime()).isEqualTo(UPDATED_EXPIRY_TIME);
        assertThat(testOTP.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testOTP.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testOTP.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testOTP.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testOTP.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    void patchNonExistingOTP() throws Exception {
        int databaseSizeBeforeUpdate = oTPRepository.findAll().size();
        oTP.setId(UUID.randomUUID().toString());

        // Create the OTP
        OTPDTO oTPDTO = oTPMapper.toDto(oTP);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOTPMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, oTPDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(oTPDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OTP in the database
        List<OTP> oTPList = oTPRepository.findAll();
        assertThat(oTPList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchOTP() throws Exception {
        int databaseSizeBeforeUpdate = oTPRepository.findAll().size();
        oTP.setId(UUID.randomUUID().toString());

        // Create the OTP
        OTPDTO oTPDTO = oTPMapper.toDto(oTP);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOTPMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(oTPDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OTP in the database
        List<OTP> oTPList = oTPRepository.findAll();
        assertThat(oTPList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamOTP() throws Exception {
        int databaseSizeBeforeUpdate = oTPRepository.findAll().size();
        oTP.setId(UUID.randomUUID().toString());

        // Create the OTP
        OTPDTO oTPDTO = oTPMapper.toDto(oTP);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOTPMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(oTPDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the OTP in the database
        List<OTP> oTPList = oTPRepository.findAll();
        assertThat(oTPList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteOTP() throws Exception {
        // Initialize the database
        oTPRepository.save(oTP);

        int databaseSizeBeforeDelete = oTPRepository.findAll().size();

        // Delete the oTP
        restOTPMockMvc.perform(delete(ENTITY_API_URL_ID, oTP.getId()).accept(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OTP> oTPList = oTPRepository.findAll();
        assertThat(oTPList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
