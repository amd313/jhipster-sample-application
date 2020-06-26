import { IEmployee } from 'app/shared/model/employee.model';
import { ICategory } from 'app/shared/model/category.model';

export interface IService {
  id?: number;
  name?: string;
  description?: string;
  employees?: IEmployee[];
  categories?: ICategory[];
}

export class Service implements IService {
  constructor(
    public id?: number,
    public name?: string,
    public description?: string,
    public employees?: IEmployee[],
    public categories?: ICategory[]
  ) {}
}
