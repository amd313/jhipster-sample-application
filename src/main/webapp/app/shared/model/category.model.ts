import { IService } from 'app/shared/model/service.model';

export interface ICategory {
  id?: number;
  name?: string;
  description?: string;
  services?: IService[];
}

export class Category implements ICategory {
  constructor(public id?: number, public name?: string, public description?: string, public services?: IService[]) {}
}
