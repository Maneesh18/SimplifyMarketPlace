import { loadingBarReducer as loadingBar } from 'react-redux-loading-bar';

import locale, { LocaleState } from './locale';
import authentication, { AuthenticationState } from './authentication';
import applicationProfile, { ApplicationProfileState } from './application-profile';

import administration, { AdministrationState } from 'app/modules/administration/administration.reducer';
import userManagement, { UserManagementState } from 'app/modules/administration/user-management/user-management.reducer';
import register, { RegisterState } from 'app/modules/account/register/register.reducer';
import activate, { ActivateState } from 'app/modules/account/activate/activate.reducer';
import password, { PasswordState } from 'app/modules/account/password/password.reducer';
import settings, { SettingsState } from 'app/modules/account/settings/settings.reducer';
import passwordReset, { PasswordResetState } from 'app/modules/account/password-reset/password-reset.reducer';
// prettier-ignore
import oTP from 'app/entities/otp/otp.reducer';
// prettier-ignore
import oTPAttempt from 'app/entities/otp-attempt/otp-attempt.reducer';
// prettier-ignore
import worker from 'app/entities/worker/worker.reducer';
// prettier-ignore
import jobPreference from 'app/entities/job-preference/job-preference.reducer';
// prettier-ignore
import preferredCity from 'app/entities/preferred-city/preferred-city.reducer';
// prettier-ignore
import education from 'app/entities/education/education.reducer';
// prettier-ignore
import employment from 'app/entities/employment/employment.reducer';
// prettier-ignore
import address from 'app/entities/address/address.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const rootReducer = {
  authentication,
  locale,
  applicationProfile,
  administration,
  userManagement,
  register,
  activate,
  passwordReset,
  password,
  settings,
  oTP,
  oTPAttempt,
  worker,
  jobPreference,
  preferredCity,
  education,
  employment,
  address,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
  loadingBar,
};

export default rootReducer;
