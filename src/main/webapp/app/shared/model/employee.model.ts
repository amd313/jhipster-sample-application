import { Moment } from 'moment';
import { IService } from 'app/shared/model/service.model';

export interface IEmployee {
  id?: number;
  firstName?: string;
  lastName?: string;
  email?: string;
  phoneNumber?: string;
  hireDate?: Moment;
  salary?: number;
  commissionPct?: number;
  bookable?: boolean;
  services?: IService[];
}

export class Employee implements IEmployee {
  constructor(
    public id?: number,
    public firstName?: string,
    public lastName?: string,
    public email?: string,
    public phoneNumber?: string,
    public hireDate?: Moment,
    public salary?: number,
    public commissionPct?: number,
    public bookable?: boolean,
    public services?: IService[]
  ) {
    this.bookable = this.bookable || false;
  }
}
