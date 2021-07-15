package com.simplify.marketplace.web.rest;

import com.simplify.marketplace.repository.PreferredCityRepository;
import com.simplify.marketplace.service.PreferredCityService;
import com.simplify.marketplace.service.dto.PreferredCityDTO;
import com.simplify.marketplace.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.simplify.marketplace.domain.PreferredCity}.
 */
@RestController
@RequestMapping("/api")
public class PreferredCityResource {

    private final Logger log = LoggerFactory.getLogger(PreferredCityResource.class);

    private static final String ENTITY_NAME = "preferredCity";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PreferredCityService preferredCityService;

    private final PreferredCityRepository preferredCityRepository;

    public PreferredCityResource(PreferredCityService preferredCityService, PreferredCityRepository preferredCityRepository) {
        this.preferredCityService = preferredCityService;
        this.preferredCityRepository = preferredCityRepository;
    }

    /**
     * {@code POST  /preferred-cities} : Create a new preferredCity.
     *
     * @param preferredCityDTO the preferredCityDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new preferredCityDTO, or with status {@code 400 (Bad Request)} if the preferredCity has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/preferred-cities")
    public ResponseEntity<PreferredCityDTO> createPreferredCity(@Valid @RequestBody PreferredCityDTO preferredCityDTO)
        throws URISyntaxException {
        log.debug("REST request to save PreferredCity : {}", preferredCityDTO);
        if (preferredCityDTO.getId() != null) {
            throw new BadRequestAlertException("A new preferredCity cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PreferredCityDTO result = preferredCityService.save(preferredCityDTO);
        return ResponseEntity
            .created(new URI("/api/preferred-cities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /preferred-cities/:id} : Updates an existing preferredCity.
     *
     * @param id the id of the preferredCityDTO to save.
     * @param preferredCityDTO the preferredCityDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated preferredCityDTO,
     * or with status {@code 400 (Bad Request)} if the preferredCityDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the preferredCityDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/preferred-cities/{id}")
    public ResponseEntity<PreferredCityDTO> updatePreferredCity(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody PreferredCityDTO preferredCityDTO
    ) throws URISyntaxException {
        log.debug("REST request to update PreferredCity : {}, {}", id, preferredCityDTO);
        if (preferredCityDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, preferredCityDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!preferredCityRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PreferredCityDTO result = preferredCityService.save(preferredCityDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, preferredCityDTO.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /preferred-cities/:id} : Partial updates given fields of an existing preferredCity, field will ignore if it is null
     *
     * @param id the id of the preferredCityDTO to save.
     * @param preferredCityDTO the preferredCityDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated preferredCityDTO,
     * or with status {@code 400 (Bad Request)} if the preferredCityDTO is not valid,
     * or with status {@code 404 (Not Found)} if the preferredCityDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the preferredCityDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/preferred-cities/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<PreferredCityDTO> partialUpdatePreferredCity(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody PreferredCityDTO preferredCityDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update PreferredCity partially : {}, {}", id, preferredCityDTO);
        if (preferredCityDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, preferredCityDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!preferredCityRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PreferredCityDTO> result = preferredCityService.partialUpdate(preferredCityDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, preferredCityDTO.getId())
        );
    }

    /**
     * {@code GET  /preferred-cities} : get all the preferredCities.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of preferredCities in body.
     */
    @GetMapping("/preferred-cities")
    public ResponseEntity<List<PreferredCityDTO>> getAllPreferredCities(Pageable pageable) {
        log.debug("REST request to get a page of PreferredCities");
        Page<PreferredCityDTO> page = preferredCityService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /preferred-cities/:id} : get the "id" preferredCity.
     *
     * @param id the id of the preferredCityDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the preferredCityDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/preferred-cities/{id}")
    public ResponseEntity<PreferredCityDTO> getPreferredCity(@PathVariable String id) {
        log.debug("REST request to get PreferredCity : {}", id);
        Optional<PreferredCityDTO> preferredCityDTO = preferredCityService.findOne(id);
        return ResponseUtil.wrapOrNotFound(preferredCityDTO);
    }

    /**
     * {@code DELETE  /preferred-cities/:id} : delete the "id" preferredCity.
     *
     * @param id the id of the preferredCityDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/preferred-cities/{id}")
    public ResponseEntity<Void> deletePreferredCity(@PathVariable String id) {
        log.debug("REST request to delete PreferredCity : {}", id);
        preferredCityService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
