import React from 'react';
import { Switch } from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import OTP from './otp';
import OTPAttempt from './otp-attempt';
import Worker from './worker';
import JobPreference from './job-preference';
import PreferredCity from './preferred-city';
import Education from './education';
import Employment from './employment';
import Address from './address';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}otp`} component={OTP} />
      <ErrorBoundaryRoute path={`${match.url}otp-attempt`} component={OTPAttempt} />
      <ErrorBoundaryRoute path={`${match.url}worker`} component={Worker} />
      <ErrorBoundaryRoute path={`${match.url}job-preference`} component={JobPreference} />
      <ErrorBoundaryRoute path={`${match.url}preferred-city`} component={PreferredCity} />
      <ErrorBoundaryRoute path={`${match.url}education`} component={Education} />
      <ErrorBoundaryRoute path={`${match.url}employment`} component={Employment} />
      <ErrorBoundaryRoute path={`${match.url}address`} component={Address} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
