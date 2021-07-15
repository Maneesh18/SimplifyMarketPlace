import dayjs from 'dayjs';
import { IWorker } from 'app/shared/model/worker.model';
import { Category } from 'app/shared/model/enumerations/category.model';
import { SubCategory } from 'app/shared/model/enumerations/sub-category.model';

export interface IEmployment {
  id?: string;
  jobTitle?: string;
  companyName?: string;
  startDate?: string;
  endDate?: string;
  lastSalary?: number;
  description?: string | null;
  category?: Category | null;
  subCategory?: SubCategory | null;
  createdBy?: string;
  createdAt?: string;
  updatedBy?: string;
  updatedAt?: string;
  worker?: IWorker | null;
}

export const defaultValue: Readonly<IEmployment> = {};
