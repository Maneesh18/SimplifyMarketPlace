import dayjs from 'dayjs';
import { IEducation } from 'app/shared/model/education.model';
import { IEmployment } from 'app/shared/model/employment.model';
import { IJobPreference } from 'app/shared/model/job-preference.model';
import { IAddress } from 'app/shared/model/address.model';
import { IOTP } from 'app/shared/model/otp.model';

export interface IWorker {
  id?: string;
  name?: string;
  email?: string;
  phone?: string;
  description?: string | null;
  createdBy?: string;
  createdAt?: string;
  updatedBy?: string;
  updatedAt?: string;
  educations?: IEducation[] | null;
  employments?: IEmployment[] | null;
  jobPreferences?: IJobPreference[] | null;
  addresses?: IAddress[] | null;
  oTPS?: IOTP[] | null;
}

export const defaultValue: Readonly<IWorker> = {};
