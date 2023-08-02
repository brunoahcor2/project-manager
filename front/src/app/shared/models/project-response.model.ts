import { PersonResponseModel } from "./person-response.model";

export interface ProjectResponseModel {
  id: number;
  name: string;
  dateStart: string | undefined;
  dateEstimatedEnd: string | undefined;
  dateEnd: string | undefined;
  description: string;
  status: string;
  budget: number;
  risk: string;
  persons: PersonResponseModel[];
}
