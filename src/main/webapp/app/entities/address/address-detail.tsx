import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './address.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const AddressDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const addressEntity = useAppSelector(state => state.address.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="addressDetailsHeading">
          <Translate contentKey="simplifyMarketplaceApp.address.detail.title">Address</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{addressEntity.id}</dd>
          <dt>
            <span id="streetAddress">
              <Translate contentKey="simplifyMarketplaceApp.address.streetAddress">Street Address</Translate>
            </span>
          </dt>
          <dd>{addressEntity.streetAddress}</dd>
          <dt>
            <span id="postalCode">
              <Translate contentKey="simplifyMarketplaceApp.address.postalCode">Postal Code</Translate>
            </span>
          </dt>
          <dd>{addressEntity.postalCode}</dd>
          <dt>
            <span id="city">
              <Translate contentKey="simplifyMarketplaceApp.address.city">City</Translate>
            </span>
          </dt>
          <dd>{addressEntity.city}</dd>
          <dt>
            <span id="stateProvince">
              <Translate contentKey="simplifyMarketplaceApp.address.stateProvince">State Province</Translate>
            </span>
          </dt>
          <dd>{addressEntity.stateProvince}</dd>
          <dt>
            <span id="country">
              <Translate contentKey="simplifyMarketplaceApp.address.country">Country</Translate>
            </span>
          </dt>
          <dd>{addressEntity.country}</dd>
          <dt>
            <span id="tag">
              <Translate contentKey="simplifyMarketplaceApp.address.tag">Tag</Translate>
            </span>
          </dt>
          <dd>{addressEntity.tag}</dd>
          <dt>
            <span id="createdBy">
              <Translate contentKey="simplifyMarketplaceApp.address.createdBy">Created By</Translate>
            </span>
          </dt>
          <dd>{addressEntity.createdBy}</dd>
          <dt>
            <span id="createdAt">
              <Translate contentKey="simplifyMarketplaceApp.address.createdAt">Created At</Translate>
            </span>
          </dt>
          <dd>
            {addressEntity.createdAt ? <TextFormat value={addressEntity.createdAt} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="simplifyMarketplaceApp.address.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{addressEntity.updatedBy}</dd>
          <dt>
            <span id="updatedAt">
              <Translate contentKey="simplifyMarketplaceApp.address.updatedAt">Updated At</Translate>
            </span>
          </dt>
          <dd>
            {addressEntity.updatedAt ? <TextFormat value={addressEntity.updatedAt} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <Translate contentKey="simplifyMarketplaceApp.address.worker">Worker</Translate>
          </dt>
          <dd>{addressEntity.worker ? addressEntity.worker.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/address" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/address/${addressEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default AddressDetail;
