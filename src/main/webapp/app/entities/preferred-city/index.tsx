import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import PreferredCity from './preferred-city';
import PreferredCityDetail from './preferred-city-detail';
import PreferredCityUpdate from './preferred-city-update';
import PreferredCityDeleteDialog from './preferred-city-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={PreferredCityUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={PreferredCityUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={PreferredCityDetail} />
      <ErrorBoundaryRoute path={match.url} component={PreferredCity} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={PreferredCityDeleteDialog} />
  </>
);

export default Routes;
