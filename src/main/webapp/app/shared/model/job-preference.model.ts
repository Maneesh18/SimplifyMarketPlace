import dayjs from 'dayjs';
import { IPreferredCity } from 'app/shared/model/preferred-city.model';
import { IWorker } from 'app/shared/model/worker.model';
import { Category } from 'app/shared/model/enumerations/category.model';
import { SubCategory } from 'app/shared/model/enumerations/sub-category.model';
import { EngagementType } from 'app/shared/model/enumerations/engagement-type.model';
import { LocationType } from 'app/shared/model/enumerations/location-type.model';

export interface IJobPreference {
  id?: string;
  category?: Category;
  subCategory?: SubCategory;
  hourlyRate?: number;
  dailyRate?: number;
  monthlyRate?: number;
  hourPerDay?: number;
  hourPerWeek?: number;
  engagementType?: EngagementType | null;
  locationType?: LocationType | null;
  createdBy?: string;
  createdAt?: string;
  updatedBy?: string;
  updatedAt?: string;
  preferredCities?: IPreferredCity[] | null;
  worker?: IWorker | null;
}

export const defaultValue: Readonly<IJobPreference> = {};
