package com.simplify.marketplace.web.rest;

import com.simplify.marketplace.repository.OTPRepository;
import com.simplify.marketplace.service.OTPService;
import com.simplify.marketplace.service.dto.OTPDTO;
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
 * REST controller for managing {@link com.simplify.marketplace.domain.OTP}.
 */
@RestController
@RequestMapping("/api")
public class OTPResource {

    private final Logger log = LoggerFactory.getLogger(OTPResource.class);

    private static final String ENTITY_NAME = "oTP";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OTPService oTPService;

    private final OTPRepository oTPRepository;

    public OTPResource(OTPService oTPService, OTPRepository oTPRepository) {
        this.oTPService = oTPService;
        this.oTPRepository = oTPRepository;
    }

    /**
     * {@code POST  /otps} : Create a new oTP.
     *
     * @param oTPDTO the oTPDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new oTPDTO, or with status {@code 400 (Bad Request)} if the oTP has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/otps")
    public ResponseEntity<OTPDTO> createOTP(@Valid @RequestBody OTPDTO oTPDTO) throws URISyntaxException {
        log.debug("REST request to save OTP : {}", oTPDTO);
        if (oTPDTO.getId() != null) {
            throw new BadRequestAlertException("A new oTP cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OTPDTO result = oTPService.save(oTPDTO);
        return ResponseEntity
            .created(new URI("/api/otps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /otps/:id} : Updates an existing oTP.
     *
     * @param id the id of the oTPDTO to save.
     * @param oTPDTO the oTPDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated oTPDTO,
     * or with status {@code 400 (Bad Request)} if the oTPDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the oTPDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/otps/{id}")
    public ResponseEntity<OTPDTO> updateOTP(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody OTPDTO oTPDTO
    ) throws URISyntaxException {
        log.debug("REST request to update OTP : {}, {}", id, oTPDTO);
        if (oTPDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, oTPDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!oTPRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        OTPDTO result = oTPService.save(oTPDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, oTPDTO.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /otps/:id} : Partial updates given fields of an existing oTP, field will ignore if it is null
     *
     * @param id the id of the oTPDTO to save.
     * @param oTPDTO the oTPDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated oTPDTO,
     * or with status {@code 400 (Bad Request)} if the oTPDTO is not valid,
     * or with status {@code 404 (Not Found)} if the oTPDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the oTPDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/otps/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<OTPDTO> partialUpdateOTP(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody OTPDTO oTPDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update OTP partially : {}, {}", id, oTPDTO);
        if (oTPDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, oTPDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!oTPRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OTPDTO> result = oTPService.partialUpdate(oTPDTO);

        return ResponseUtil.wrapOrNotFound(result, HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, oTPDTO.getId()));
    }

    /**
     * {@code GET  /otps} : get all the oTPS.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of oTPS in body.
     */
    @GetMapping("/otps")
    public ResponseEntity<List<OTPDTO>> getAllOTPS(Pageable pageable) {
        log.debug("REST request to get a page of OTPS");
        Page<OTPDTO> page = oTPService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /otps/:id} : get the "id" oTP.
     *
     * @param id the id of the oTPDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the oTPDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/otps/{id}")
    public ResponseEntity<OTPDTO> getOTP(@PathVariable String id) {
        log.debug("REST request to get OTP : {}", id);
        Optional<OTPDTO> oTPDTO = oTPService.findOne(id);
        return ResponseUtil.wrapOrNotFound(oTPDTO);
    }

    /**
     * {@code DELETE  /otps/:id} : delete the "id" oTP.
     *
     * @param id the id of the oTPDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/otps/{id}")
    public ResponseEntity<Void> deleteOTP(@PathVariable String id) {
        log.debug("REST request to delete OTP : {}", id);
        oTPService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
