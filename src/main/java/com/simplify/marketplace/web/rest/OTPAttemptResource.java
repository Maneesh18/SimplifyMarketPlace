package com.simplify.marketplace.web.rest;

import com.simplify.marketplace.repository.OTPAttemptRepository;
import com.simplify.marketplace.service.OTPAttemptService;
import com.simplify.marketplace.service.dto.OTPAttemptDTO;
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
 * REST controller for managing {@link com.simplify.marketplace.domain.OTPAttempt}.
 */
@RestController
@RequestMapping("/api")
public class OTPAttemptResource {

    private final Logger log = LoggerFactory.getLogger(OTPAttemptResource.class);

    private static final String ENTITY_NAME = "oTPAttempt";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OTPAttemptService oTPAttemptService;

    private final OTPAttemptRepository oTPAttemptRepository;

    public OTPAttemptResource(OTPAttemptService oTPAttemptService, OTPAttemptRepository oTPAttemptRepository) {
        this.oTPAttemptService = oTPAttemptService;
        this.oTPAttemptRepository = oTPAttemptRepository;
    }

    /**
     * {@code POST  /otp-attempts} : Create a new oTPAttempt.
     *
     * @param oTPAttemptDTO the oTPAttemptDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new oTPAttemptDTO, or with status {@code 400 (Bad Request)} if the oTPAttempt has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/otp-attempts")
    public ResponseEntity<OTPAttemptDTO> createOTPAttempt(@Valid @RequestBody OTPAttemptDTO oTPAttemptDTO) throws URISyntaxException {
        log.debug("REST request to save OTPAttempt : {}", oTPAttemptDTO);
        if (oTPAttemptDTO.getId() != null) {
            throw new BadRequestAlertException("A new oTPAttempt cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OTPAttemptDTO result = oTPAttemptService.save(oTPAttemptDTO);
        return ResponseEntity
            .created(new URI("/api/otp-attempts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /otp-attempts/:id} : Updates an existing oTPAttempt.
     *
     * @param id the id of the oTPAttemptDTO to save.
     * @param oTPAttemptDTO the oTPAttemptDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated oTPAttemptDTO,
     * or with status {@code 400 (Bad Request)} if the oTPAttemptDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the oTPAttemptDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/otp-attempts/{id}")
    public ResponseEntity<OTPAttemptDTO> updateOTPAttempt(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody OTPAttemptDTO oTPAttemptDTO
    ) throws URISyntaxException {
        log.debug("REST request to update OTPAttempt : {}, {}", id, oTPAttemptDTO);
        if (oTPAttemptDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, oTPAttemptDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!oTPAttemptRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        OTPAttemptDTO result = oTPAttemptService.save(oTPAttemptDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, oTPAttemptDTO.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /otp-attempts/:id} : Partial updates given fields of an existing oTPAttempt, field will ignore if it is null
     *
     * @param id the id of the oTPAttemptDTO to save.
     * @param oTPAttemptDTO the oTPAttemptDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated oTPAttemptDTO,
     * or with status {@code 400 (Bad Request)} if the oTPAttemptDTO is not valid,
     * or with status {@code 404 (Not Found)} if the oTPAttemptDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the oTPAttemptDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/otp-attempts/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<OTPAttemptDTO> partialUpdateOTPAttempt(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody OTPAttemptDTO oTPAttemptDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update OTPAttempt partially : {}, {}", id, oTPAttemptDTO);
        if (oTPAttemptDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, oTPAttemptDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!oTPAttemptRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OTPAttemptDTO> result = oTPAttemptService.partialUpdate(oTPAttemptDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, oTPAttemptDTO.getId())
        );
    }

    /**
     * {@code GET  /otp-attempts} : get all the oTPAttempts.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of oTPAttempts in body.
     */
    @GetMapping("/otp-attempts")
    public ResponseEntity<List<OTPAttemptDTO>> getAllOTPAttempts(Pageable pageable) {
        log.debug("REST request to get a page of OTPAttempts");
        Page<OTPAttemptDTO> page = oTPAttemptService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /otp-attempts/:id} : get the "id" oTPAttempt.
     *
     * @param id the id of the oTPAttemptDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the oTPAttemptDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/otp-attempts/{id}")
    public ResponseEntity<OTPAttemptDTO> getOTPAttempt(@PathVariable String id) {
        log.debug("REST request to get OTPAttempt : {}", id);
        Optional<OTPAttemptDTO> oTPAttemptDTO = oTPAttemptService.findOne(id);
        return ResponseUtil.wrapOrNotFound(oTPAttemptDTO);
    }

    /**
     * {@code DELETE  /otp-attempts/:id} : delete the "id" oTPAttempt.
     *
     * @param id the id of the oTPAttemptDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/otp-attempts/{id}")
    public ResponseEntity<Void> deleteOTPAttempt(@PathVariable String id) {
        log.debug("REST request to delete OTPAttempt : {}", id);
        oTPAttemptService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
