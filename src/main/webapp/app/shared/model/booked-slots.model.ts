import { Moment } from 'moment';
import { ICustomer } from 'app/shared/model/customer.model';
import { IService } from 'app/shared/model/service.model';
import { IServicePrice } from 'app/shared/model/service-price.model';
import { IEmployee } from 'app/shared/model/employee.model';
import { ICustomerPayment } from 'app/shared/model/customer-payment.model';

export interface IBookedSlots {
  id?: number;
  slot?: Moment;
  customerName?: string;
  employeeName?: string;
  serviceName?: string;
  categoryName?: string;
  status?: number;
  paymentStatus?: number;
  customer?: ICustomer;
  service?: IService;
  servicePrice?: IServicePrice;
  employee?: IEmployee;
  payment?: ICustomerPayment;
}

export class BookedSlots implements IBookedSlots {
  constructor(
    public id?: number,
    public slot?: Moment,
    public customerName?: string,
    public employeeName?: string,
    public serviceName?: string,
    public categoryName?: string,
    public status?: number,
    public paymentStatus?: number,
    public customer?: ICustomer,
    public service?: IService,
    public servicePrice?: IServicePrice,
    public employee?: IEmployee,
    public payment?: ICustomerPayment
  ) {}
}
