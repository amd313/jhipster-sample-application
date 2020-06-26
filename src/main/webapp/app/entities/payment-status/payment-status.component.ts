import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPaymentStatus } from 'app/shared/model/payment-status.model';
import { PaymentStatusService } from './payment-status.service';
import { PaymentStatusDeleteDialogComponent } from './payment-status-delete-dialog.component';

@Component({
  selector: 'jhi-payment-status',
  templateUrl: './payment-status.component.html',
})
export class PaymentStatusComponent implements OnInit, OnDestroy {
  paymentStatuses?: IPaymentStatus[];
  eventSubscriber?: Subscription;

  constructor(
    protected paymentStatusService: PaymentStatusService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.paymentStatusService.query().subscribe((res: HttpResponse<IPaymentStatus[]>) => (this.paymentStatuses = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInPaymentStatuses();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IPaymentStatus): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInPaymentStatuses(): void {
    this.eventSubscriber = this.eventManager.subscribe('paymentStatusListModification', () => this.loadAll());
  }

  delete(paymentStatus: IPaymentStatus): void {
    const modalRef = this.modalService.open(PaymentStatusDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.paymentStatus = paymentStatus;
  }
}
