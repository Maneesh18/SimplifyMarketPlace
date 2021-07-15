import dayjs from 'dayjs';
import { IWorker } from 'app/shared/model/worker.model';
import { AddressTag } from 'app/shared/model/enumerations/address-tag.model';

export interface IAddress {
  id?: string;
  streetAddress?: string;
  postalCode?: number;
  city?: string;
  stateProvince?: string;
  country?: string;
  tag?: AddressTag | null;
  createdBy?: string;
  createdAt?: string;
  updatedBy?: string;
  updatedAt?: string;
  worker?: IWorker | null;
}

export const defaultValue: Readonly<IAddress> = {};
