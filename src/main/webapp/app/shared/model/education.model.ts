import dayjs from 'dayjs';
import { IWorker } from 'app/shared/model/worker.model';

export interface IEducation {
  id?: string;
  degreeName?: string;
  institute?: string;
  yearOfPassing?: number;
  marks?: number;
  description?: string | null;
  createdBy?: string;
  createdAt?: string;
  updatedBy?: string;
  updatedAt?: string;
  worker?: IWorker | null;
}

export const defaultValue: Readonly<IEducation> = {};
