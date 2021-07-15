package com.simplify.marketplace.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.simplify.marketplace.IntegrationTest;
import com.simplify.marketplace.domain.Worker;
import com.simplify.marketplace.repository.WorkerRepository;
import com.simplify.marketplace.service.dto.WorkerDTO;
import com.simplify.marketplace.service.mapper.WorkerMapper;
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
 * Integration tests for the {@link WorkerResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class WorkerResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/workers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private WorkerRepository workerRepository;

    @Autowired
    private WorkerMapper workerMapper;

    @Autowired
    private MockMvc restWorkerMockMvc;

    private Worker worker;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Worker createEntity() {
        Worker worker = new Worker()
            .name(DEFAULT_NAME)
            .email(DEFAULT_EMAIL)
            .phone(DEFAULT_PHONE)
            .description(DEFAULT_DESCRIPTION)
            .createdBy(DEFAULT_CREATED_BY)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedAt(DEFAULT_UPDATED_AT);
        return worker;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Worker createUpdatedEntity() {
        Worker worker = new Worker()
            .name(UPDATED_NAME)
            .email(UPDATED_EMAIL)
            .phone(UPDATED_PHONE)
            .description(UPDATED_DESCRIPTION)
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedAt(UPDATED_UPDATED_AT);
        return worker;
    }

    @BeforeEach
    public void initTest() {
        workerRepository.deleteAll();
        worker = createEntity();
    }

    @Test
    void createWorker() throws Exception {
        int databaseSizeBeforeCreate = workerRepository.findAll().size();
        // Create the Worker
        WorkerDTO workerDTO = workerMapper.toDto(worker);
        restWorkerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(workerDTO)))
            .andExpect(status().isCreated());

        // Validate the Worker in the database
        List<Worker> workerList = workerRepository.findAll();
        assertThat(workerList).hasSize(databaseSizeBeforeCreate + 1);
        Worker testWorker = workerList.get(workerList.size() - 1);
        assertThat(testWorker.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testWorker.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testWorker.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testWorker.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testWorker.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testWorker.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testWorker.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testWorker.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    void createWorkerWithExistingId() throws Exception {
        // Create the Worker with an existing ID
        worker.setId("existing_id");
        WorkerDTO workerDTO = workerMapper.toDto(worker);

        int databaseSizeBeforeCreate = workerRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restWorkerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(workerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Worker in the database
        List<Worker> workerList = workerRepository.findAll();
        assertThat(workerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = workerRepository.findAll().size();
        // set the field null
        worker.setName(null);

        // Create the Worker, which fails.
        WorkerDTO workerDTO = workerMapper.toDto(worker);

        restWorkerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(workerDTO)))
            .andExpect(status().isBadRequest());

        List<Worker> workerList = workerRepository.findAll();
        assertThat(workerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = workerRepository.findAll().size();
        // set the field null
        worker.setEmail(null);

        // Create the Worker, which fails.
        WorkerDTO workerDTO = workerMapper.toDto(worker);

        restWorkerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(workerDTO)))
            .andExpect(status().isBadRequest());

        List<Worker> workerList = workerRepository.findAll();
        assertThat(workerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkPhoneIsRequired() throws Exception {
        int databaseSizeBeforeTest = workerRepository.findAll().size();
        // set the field null
        worker.setPhone(null);

        // Create the Worker, which fails.
        WorkerDTO workerDTO = workerMapper.toDto(worker);

        restWorkerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(workerDTO)))
            .andExpect(status().isBadRequest());

        List<Worker> workerList = workerRepository.findAll();
        assertThat(workerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkCreatedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = workerRepository.findAll().size();
        // set the field null
        worker.setCreatedBy(null);

        // Create the Worker, which fails.
        WorkerDTO workerDTO = workerMapper.toDto(worker);

        restWorkerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(workerDTO)))
            .andExpect(status().isBadRequest());

        List<Worker> workerList = workerRepository.findAll();
        assertThat(workerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = workerRepository.findAll().size();
        // set the field null
        worker.setCreatedAt(null);

        // Create the Worker, which fails.
        WorkerDTO workerDTO = workerMapper.toDto(worker);

        restWorkerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(workerDTO)))
            .andExpect(status().isBadRequest());

        List<Worker> workerList = workerRepository.findAll();
        assertThat(workerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkUpdatedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = workerRepository.findAll().size();
        // set the field null
        worker.setUpdatedBy(null);

        // Create the Worker, which fails.
        WorkerDTO workerDTO = workerMapper.toDto(worker);

        restWorkerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(workerDTO)))
            .andExpect(status().isBadRequest());

        List<Worker> workerList = workerRepository.findAll();
        assertThat(workerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkUpdatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = workerRepository.findAll().size();
        // set the field null
        worker.setUpdatedAt(null);

        // Create the Worker, which fails.
        WorkerDTO workerDTO = workerMapper.toDto(worker);

        restWorkerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(workerDTO)))
            .andExpect(status().isBadRequest());

        List<Worker> workerList = workerRepository.findAll();
        assertThat(workerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllWorkers() throws Exception {
        // Initialize the database
        workerRepository.save(worker);

        // Get all the workerList
        restWorkerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(worker.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));
    }

    @Test
    void getWorker() throws Exception {
        // Initialize the database
        workerRepository.save(worker);

        // Get the worker
        restWorkerMockMvc
            .perform(get(ENTITY_API_URL_ID, worker.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(worker.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()));
    }

    @Test
    void getNonExistingWorker() throws Exception {
        // Get the worker
        restWorkerMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putNewWorker() throws Exception {
        // Initialize the database
        workerRepository.save(worker);

        int databaseSizeBeforeUpdate = workerRepository.findAll().size();

        // Update the worker
        Worker updatedWorker = workerRepository.findById(worker.getId()).get();
        updatedWorker
            .name(UPDATED_NAME)
            .email(UPDATED_EMAIL)
            .phone(UPDATED_PHONE)
            .description(UPDATED_DESCRIPTION)
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedAt(UPDATED_UPDATED_AT);
        WorkerDTO workerDTO = workerMapper.toDto(updatedWorker);

        restWorkerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, workerDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(workerDTO))
            )
            .andExpect(status().isOk());

        // Validate the Worker in the database
        List<Worker> workerList = workerRepository.findAll();
        assertThat(workerList).hasSize(databaseSizeBeforeUpdate);
        Worker testWorker = workerList.get(workerList.size() - 1);
        assertThat(testWorker.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testWorker.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testWorker.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testWorker.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testWorker.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testWorker.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testWorker.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testWorker.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    void putNonExistingWorker() throws Exception {
        int databaseSizeBeforeUpdate = workerRepository.findAll().size();
        worker.setId(UUID.randomUUID().toString());

        // Create the Worker
        WorkerDTO workerDTO = workerMapper.toDto(worker);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWorkerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, workerDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(workerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Worker in the database
        List<Worker> workerList = workerRepository.findAll();
        assertThat(workerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchWorker() throws Exception {
        int databaseSizeBeforeUpdate = workerRepository.findAll().size();
        worker.setId(UUID.randomUUID().toString());

        // Create the Worker
        WorkerDTO workerDTO = workerMapper.toDto(worker);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWorkerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(workerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Worker in the database
        List<Worker> workerList = workerRepository.findAll();
        assertThat(workerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamWorker() throws Exception {
        int databaseSizeBeforeUpdate = workerRepository.findAll().size();
        worker.setId(UUID.randomUUID().toString());

        // Create the Worker
        WorkerDTO workerDTO = workerMapper.toDto(worker);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWorkerMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(workerDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Worker in the database
        List<Worker> workerList = workerRepository.findAll();
        assertThat(workerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateWorkerWithPatch() throws Exception {
        // Initialize the database
        workerRepository.save(worker);

        int databaseSizeBeforeUpdate = workerRepository.findAll().size();

        // Update the worker using partial update
        Worker partialUpdatedWorker = new Worker();
        partialUpdatedWorker.setId(worker.getId());

        partialUpdatedWorker
            .name(UPDATED_NAME)
            .phone(UPDATED_PHONE)
            .description(UPDATED_DESCRIPTION)
            .createdBy(UPDATED_CREATED_BY)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedAt(UPDATED_UPDATED_AT);

        restWorkerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWorker.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedWorker))
            )
            .andExpect(status().isOk());

        // Validate the Worker in the database
        List<Worker> workerList = workerRepository.findAll();
        assertThat(workerList).hasSize(databaseSizeBeforeUpdate);
        Worker testWorker = workerList.get(workerList.size() - 1);
        assertThat(testWorker.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testWorker.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testWorker.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testWorker.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testWorker.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testWorker.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testWorker.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testWorker.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    void fullUpdateWorkerWithPatch() throws Exception {
        // Initialize the database
        workerRepository.save(worker);

        int databaseSizeBeforeUpdate = workerRepository.findAll().size();

        // Update the worker using partial update
        Worker partialUpdatedWorker = new Worker();
        partialUpdatedWorker.setId(worker.getId());

        partialUpdatedWorker
            .name(UPDATED_NAME)
            .email(UPDATED_EMAIL)
            .phone(UPDATED_PHONE)
            .description(UPDATED_DESCRIPTION)
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedAt(UPDATED_UPDATED_AT);

        restWorkerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWorker.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedWorker))
            )
            .andExpect(status().isOk());

        // Validate the Worker in the database
        List<Worker> workerList = workerRepository.findAll();
        assertThat(workerList).hasSize(databaseSizeBeforeUpdate);
        Worker testWorker = workerList.get(workerList.size() - 1);
        assertThat(testWorker.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testWorker.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testWorker.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testWorker.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testWorker.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testWorker.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testWorker.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testWorker.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    void patchNonExistingWorker() throws Exception {
        int databaseSizeBeforeUpdate = workerRepository.findAll().size();
        worker.setId(UUID.randomUUID().toString());

        // Create the Worker
        WorkerDTO workerDTO = workerMapper.toDto(worker);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWorkerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, workerDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(workerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Worker in the database
        List<Worker> workerList = workerRepository.findAll();
        assertThat(workerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchWorker() throws Exception {
        int databaseSizeBeforeUpdate = workerRepository.findAll().size();
        worker.setId(UUID.randomUUID().toString());

        // Create the Worker
        WorkerDTO workerDTO = workerMapper.toDto(worker);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWorkerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(workerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Worker in the database
        List<Worker> workerList = workerRepository.findAll();
        assertThat(workerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamWorker() throws Exception {
        int databaseSizeBeforeUpdate = workerRepository.findAll().size();
        worker.setId(UUID.randomUUID().toString());

        // Create the Worker
        WorkerDTO workerDTO = workerMapper.toDto(worker);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWorkerMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(workerDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Worker in the database
        List<Worker> workerList = workerRepository.findAll();
        assertThat(workerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteWorker() throws Exception {
        // Initialize the database
        workerRepository.save(worker);

        int databaseSizeBeforeDelete = workerRepository.findAll().size();

        // Delete the worker
        restWorkerMockMvc
            .perform(delete(ENTITY_API_URL_ID, worker.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Worker> workerList = workerRepository.findAll();
        assertThat(workerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
