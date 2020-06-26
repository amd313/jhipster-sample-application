import { IEmployee } from 'app/shared/model/employee.model';
import { IService } from 'app/shared/model/service.model';

export interface IServicePrice {
  id?: number;
  amount?: number;
  employee?: IEmployee;
  service?: IService;
}

export class ServicePrice implements IServicePrice {
  constructor(public id?: number, public amount?: number, public employee?: IEmployee, public service?: IService) {}
}
