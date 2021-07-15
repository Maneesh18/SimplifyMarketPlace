import { IJobPreference } from 'app/shared/model/job-preference.model';

export interface IPreferredCity {
  id?: string;
  city?: string;
  jobPreference?: IJobPreference | null;
}

export const defaultValue: Readonly<IPreferredCity> = {};
