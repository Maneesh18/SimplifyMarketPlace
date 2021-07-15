import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IJobPreference } from 'app/shared/model/job-preference.model';
import { getEntities as getJobPreferences } from 'app/entities/job-preference/job-preference.reducer';
import { getEntity, updateEntity, createEntity, reset } from './preferred-city.reducer';
import { IPreferredCity } from 'app/shared/model/preferred-city.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const PreferredCityUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const jobPreferences = useAppSelector(state => state.jobPreference.entities);
  const preferredCityEntity = useAppSelector(state => state.preferredCity.entity);
  const loading = useAppSelector(state => state.preferredCity.loading);
  const updating = useAppSelector(state => state.preferredCity.updating);
  const updateSuccess = useAppSelector(state => state.preferredCity.updateSuccess);

  const handleClose = () => {
    props.history.push('/preferred-city');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getJobPreferences({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...preferredCityEntity,
      ...values,
      jobPreference: jobPreferences.find(it => it.id.toString() === values.jobPreferenceId.toString()),
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
          ...preferredCityEntity,
          jobPreferenceId: preferredCityEntity?.jobPreference?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="simplifyMarketplaceApp.preferredCity.home.createOrEditLabel" data-cy="PreferredCityCreateUpdateHeading">
            <Translate contentKey="simplifyMarketplaceApp.preferredCity.home.createOrEditLabel">Create or edit a PreferredCity</Translate>
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
                  id="preferred-city-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('simplifyMarketplaceApp.preferredCity.city')}
                id="preferred-city-city"
                name="city"
                data-cy="city"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                id="preferred-city-jobPreference"
                name="jobPreferenceId"
                data-cy="jobPreference"
                label={translate('simplifyMarketplaceApp.preferredCity.jobPreference')}
                type="select"
              >
                <option value="" key="0" />
                {jobPreferences
                  ? jobPreferences.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/preferred-city" replace color="info">
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

export default PreferredCityUpdate;
