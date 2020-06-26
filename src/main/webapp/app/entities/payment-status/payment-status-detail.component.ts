import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPaymentStatus } from 'app/shared/model/payment-status.model';

@Component({
  selector: 'jhi-payment-status-detail',
  templateUrl: './payment-status-detail.component.html',
})
export class PaymentStatusDetailComponent implements OnInit {
  paymentStatus: IPaymentStatus | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ paymentStatus }) => (this.paymentStatus = paymentStatus));
  }

  previousState(): void {
    window.history.back();
  }
}
