import dayjs from 'dayjs';
import { IOTPAttempt } from 'app/shared/model/otp-attempt.model';
import { IWorker } from 'app/shared/model/worker.model';
import { OtpType } from 'app/shared/model/enumerations/otp-type.model';
import { OtpStatus } from 'app/shared/model/enumerations/otp-status.model';

export interface IOTP {
  id?: string;
  otpId?: number;
  email?: string;
  phone?: string;
  type?: OtpType | null;
  expiryTime?: string;
  status?: OtpStatus | null;
  createdBy?: string;
  createdAt?: string;
  updatedBy?: string;
  updatedAt?: string;
  oTPAttempts?: IOTPAttempt[] | null;
  worker?: IWorker | null;
}

export const defaultValue: Readonly<IOTP> = {};
