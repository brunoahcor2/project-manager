import { PersonRequestModel } from './person-request.model';

export class ProjectRequestModel {
  id: number | undefined;
  name: string | undefined;
  dateStart: string | undefined;
  dateEstimatedEnd: string | undefined;
  dateEnd: string | undefined;
  description: string | undefined;
  status: string | undefined;
  budget: number | undefined;
  risk: string | undefined;
  persons: PersonRequestModel[] | undefined;
}
