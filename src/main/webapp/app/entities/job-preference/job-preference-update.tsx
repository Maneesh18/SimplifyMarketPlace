import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IWorker } from 'app/shared/model/worker.model';
import { getEntities as getWorkers } from 'app/entities/worker/worker.reducer';
import { getEntity, updateEntity, createEntity, reset } from './job-preference.reducer';
import { IJobPreference } from 'app/shared/model/job-preference.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const JobPreferenceUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const workers = useAppSelector(state => state.worker.entities);
  const jobPreferenceEntity = useAppSelector(state => state.jobPreference.entity);
  const loading = useAppSelector(state => state.jobPreference.loading);
  const updating = useAppSelector(state => state.jobPreference.updating);
  const updateSuccess = useAppSelector(state => state.jobPreference.updateSuccess);

  const handleClose = () => {
    props.history.push('/job-preference');
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
      ...jobPreferenceEntity,
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
          ...jobPreferenceEntity,
          category: 'HealthCare',
          subCategory: 'Nurse',
          engagementType: 'PartTime',
          locationType: 'RemoteOnly',
          workerId: jobPreferenceEntity?.worker?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="simplifyMarketplaceApp.jobPreference.home.createOrEditLabel" data-cy="JobPreferenceCreateUpdateHeading">
            <Translate contentKey="simplifyMarketplaceApp.jobPreference.home.createOrEditLabel">Create or edit a JobPreference</Translate>
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
                  id="job-preference-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('simplifyMarketplaceApp.jobPreference.category')}
                id="job-preference-category"
                name="category"
                data-cy="category"
                type="select"
              >
                <option value="HealthCare">{translate('simplifyMarketplaceApp.Category.HealthCare')}</option>
                <option value="Driver">{translate('simplifyMarketplaceApp.Category.Driver')}</option>
                <option value="Software">{translate('simplifyMarketplaceApp.Category.Software')}</option>
              </ValidatedField>
              <ValidatedField
                label={translate('simplifyMarketplaceApp.jobPreference.subCategory')}
                id="job-preference-subCategory"
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
                label={translate('simplifyMarketplaceApp.jobPreference.hourlyRate')}
                id="job-preference-hourlyRate"
                name="hourlyRate"
                data-cy="hourlyRate"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('simplifyMarketplaceApp.jobPreference.dailyRate')}
                id="job-preference-dailyRate"
                name="dailyRate"
                data-cy="dailyRate"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('simplifyMarketplaceApp.jobPreference.monthlyRate')}
                id="job-preference-monthlyRate"
                name="monthlyRate"
                data-cy="monthlyRate"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('simplifyMarketplaceApp.jobPreference.hourPerDay')}
                id="job-preference-hourPerDay"
                name="hourPerDay"
                data-cy="hourPerDay"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('simplifyMarketplaceApp.jobPreference.hourPerWeek')}
                id="job-preference-hourPerWeek"
                name="hourPerWeek"
                data-cy="hourPerWeek"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('simplifyMarketplaceApp.jobPreference.engagementType')}
                id="job-preference-engagementType"
                name="engagementType"
                data-cy="engagementType"
                type="select"
              >
                <option value="PartTime">{translate('simplifyMarketplaceApp.EngagementType.PartTime')}</option>
                <option value="FullTime">{translate('simplifyMarketplaceApp.EngagementType.FullTime')}</option>
                <option value="Freelancing">{translate('simplifyMarketplaceApp.EngagementType.Freelancing')}</option>
                <option value="SelfEmployed">{translate('simplifyMarketplaceApp.EngagementType.SelfEmployed')}</option>
              </ValidatedField>
              <ValidatedField
                label={translate('simplifyMarketplaceApp.jobPreference.locationType')}
                id="job-preference-locationType"
                name="locationType"
                data-cy="locationType"
                type="select"
              >
                <option value="RemoteOnly">{translate('simplifyMarketplaceApp.LocationType.RemoteOnly')}</option>
                <option value="OfficeOnly">{translate('simplifyMarketplaceApp.LocationType.OfficeOnly')}</option>
                <option value="Both">{translate('simplifyMarketplaceApp.LocationType.Both')}</option>
              </ValidatedField>
              <ValidatedField
                label={translate('simplifyMarketplaceApp.jobPreference.createdBy')}
                id="job-preference-createdBy"
                name="createdBy"
                data-cy="createdBy"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('simplifyMarketplaceApp.jobPreference.createdAt')}
                id="job-preference-createdAt"
                name="createdAt"
                data-cy="createdAt"
                type="date"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('simplifyMarketplaceApp.jobPreference.updatedBy')}
                id="job-preference-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('simplifyMarketplaceApp.jobPreference.updatedAt')}
                id="job-preference-updatedAt"
                name="updatedAt"
                data-cy="updatedAt"
                type="date"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                id="job-preference-worker"
                name="workerId"
                data-cy="worker"
                label={translate('simplifyMarketplaceApp.jobPreference.worker')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/job-preference" replace color="info">
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

export default JobPreferenceUpdate;
