import dayjs from 'dayjs';
import { IOTP } from 'app/shared/model/otp.model';

export interface IOTPAttempt {
  id?: string;
  otpAttemptId?: number;
  email?: string;
  phone?: string;
  ip?: string;
  coookie?: string;
  createdBy?: string;
  createdAt?: string;
  otp?: IOTP | null;
}

export const defaultValue: Readonly<IOTPAttempt> = {};
