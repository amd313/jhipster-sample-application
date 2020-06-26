import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPaymentStatus } from 'app/shared/model/payment-status.model';
import { PaymentStatusService } from './payment-status.service';

@Component({
  templateUrl: './payment-status-delete-dialog.component.html',
})
export class PaymentStatusDeleteDialogComponent {
  paymentStatus?: IPaymentStatus;

  constructor(
    protected paymentStatusService: PaymentStatusService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.paymentStatusService.delete(id).subscribe(() => {
      this.eventManager.broadcast('paymentStatusListModification');
      this.activeModal.close();
    });
  }
}
