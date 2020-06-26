export interface IPaymentStatus {
  id?: number;
  statusName?: string;
}

export class PaymentStatus implements IPaymentStatus {
  constructor(public id?: number, public statusName?: string) {}
}
