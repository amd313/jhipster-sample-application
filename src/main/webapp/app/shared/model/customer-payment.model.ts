export interface ICustomerPayment {
  id?: number;
  status?: number;
  amount?: number;
}

export class CustomerPayment implements ICustomerPayment {
  constructor(public id?: number, public status?: number, public amount?: number) {}
}
