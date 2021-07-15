import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IWorker } from 'app/shared/model/worker.model';
import { getEntities as getWorkers } from 'app/entities/worker/worker.reducer';
import { getEntity, updateEntity, createEntity, reset } from './employment.reducer';
import { IEmployment } from 'app/shared/model/employment.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const EmploymentUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const workers = useAppSelector(state => state.worker.entities);
  const employmentEntity = useAppSelector(state => state.employment.entity);
  const loading = useAppSelector(state => state.employment.loading);
  const updating = useAppSelector(state => state.employment.updating);
  const updateSuccess = useAppSelector(state => state.employment.updateSuccess);

  const handleClose = () => {
    props.history.push('/employment');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getWorkers({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...employmentEntity,
      ...values,
      worker: workers.find(it => it.id.toString() === values.workerId.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...employmentEntity,
          category: 'HealthCare',
          subCategory: 'Nurse',
          workerId: employmentEntity?.worker?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="simplifyMarketplaceApp.employment.home.createOrEditLabel" data-cy="EmploymentCreateUpdateHeading">
            <Translate contentKey="simplifyMarketplaceApp.employment.home.createOrEditLabel">Create or edit a Employment</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="employment-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('simplifyMarketplaceApp.employment.jobTitle')}
                id="employment-jobTitle"
                name="jobTitle"
                data-cy="jobTitle"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('simplifyMarketplaceApp.employment.companyName')}
                id="employment-companyName"
                name="companyName"
                data-cy="companyName"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('simplifyMarketplaceApp.employment.startDate')}
                id="employment-startDate"
                name="startDate"
                data-cy="startDate"
                type="date"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('simplifyMarketplaceApp.employment.endDate')}
                id="employment-endDate"
                name="endDate"
                data-cy="endDate"
                type="date"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('simplifyMarketplaceApp.employment.lastSalary')}
                id="employment-lastSalary"
                name="lastSalary"
                data-cy="lastSalary"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('simplifyMarketplaceApp.employment.description')}
                id="employment-description"
                name="description"
                data-cy="description"
                type="text"
              />
              <ValidatedField
                label={translate('simplifyMarketplaceApp.employment.category')}
                id="employment-category"
                name="category"
                data-cy="category"
                type="select"
              >
                <option value="HealthCare">{translate('simplifyMarketplaceApp.Category.HealthCare')}</option>
                <option value="Driver">{translate('simplifyMarketplaceApp.Category.Driver')}</option>
                <option value="Software">{translate('simplifyMarketplaceApp.Category.Software')}</option>
              </ValidatedField>
              <ValidatedField
                label={translate('simplifyMarketplaceApp.employment.subCategory')}
                id="employment-subCategory"
                name="subCategory"
                data-cy="subCategory"
                type="select"
              >
                <option value="Nurse">{translate('simplifyMarketplaceApp.SubCategory.Nurse')}</option>
                <option value="Receptionist">{translate('simplifyMarketplaceApp.SubCategory.Receptionist')}</option>
                <option value="Opthalmologist">{translate('simplifyMarketplaceApp.SubCategory.Opthalmologist')}</option>
                <option value="ClinicalTrialSpecialist">{translate('simplifyMarketplaceApp.SubCategory.ClinicalTrialSpecialist')}</option>
                <option value="TwoWheeler">{translate('simplifyMarketplaceApp.SubCategory.TwoWheeler')}</option>
                <option value="FourWheeler">{translate('simplifyMarketplaceApp.SubCategory.FourWheeler')}</option>
                <option value="HeavyVeichle">{translate('simplifyMarketplaceApp.SubCategory.HeavyVeichle')}</option>
                <option value="Developer">{translate('simplifyMarketplaceApp.SubCategory.Developer')}</option>
                <option value="QA">{translate('simplifyMarketplaceApp.SubCategory.QA')}</option>
                <option value="DevOps">{translate('simplifyMarketplaceApp.SubCategory.DevOps')}</option>
              </ValidatedField>
              <ValidatedField
                label={translate('simplifyMarketplaceApp.employment.createdBy')}
                id="employment-createdBy"
                name="createdBy"
                data-cy="createdBy"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('simplifyMarketplaceApp.employment.createdAt')}
                id="employment-createdAt"
                name="createdAt"
                data-cy="createdAt"
                type="date"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('simplifyMarketplaceApp.employment.updatedBy')}
                id="employment-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('simplifyMarketplaceApp.employment.updatedAt')}
                id="employment-updatedAt"
                name="updatedAt"
                data-cy="updatedAt"
                type="date"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                id="employment-worker"
                name="workerId"
                data-cy="worker"
                label={translate('simplifyMarketplaceApp.employment.worker')}
                type="select"
              >
                <option value="" key="0" />
                {workers
                  ? workers.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/employment" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default EmploymentUpdate;
