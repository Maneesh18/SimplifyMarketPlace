import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './preferred-city.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const PreferredCityDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const preferredCityEntity = useAppSelector(state => state.preferredCity.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="preferredCityDetailsHeading">
          <Translate contentKey="simplifyMarketplaceApp.preferredCity.detail.title">PreferredCity</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{preferredCityEntity.id}</dd>
          <dt>
            <span id="city">
              <Translate contentKey="simplifyMarketplaceApp.preferredCity.city">City</Translate>
            </span>
          </dt>
          <dd>{preferredCityEntity.city}</dd>
          <dt>
            <Translate contentKey="simplifyMarketplaceApp.preferredCity.jobPreference">Job Preference</Translate>
          </dt>
          <dd>{preferredCityEntity.jobPreference ? preferredCityEntity.jobPreference.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/preferred-city" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/preferred-city/${preferredCityEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default PreferredCityDetail;
