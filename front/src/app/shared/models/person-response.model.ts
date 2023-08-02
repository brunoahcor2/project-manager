export interface PersonResponseModel {
  id: number;
  name: string;
  cpf: string;
  employee: boolean;
  dateBirth: string | undefined;
  position: string | undefined;
  active: boolean;
}
