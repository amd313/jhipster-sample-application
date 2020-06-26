import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IPaymentStatus, PaymentStatus } from 'app/shared/model/payment-status.model';
import { PaymentStatusService } from './payment-status.service';

@Component({
  selector: 'jhi-payment-status-update',
  templateUrl: './payment-status-update.component.html',
})
export class PaymentStatusUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    statusName: [],
  });

  constructor(protected paymentStatusService: PaymentStatusService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ paymentStatus }) => {
      this.updateForm(paymentStatus);
    });
  }

  updateForm(paymentStatus: IPaymentStatus): void {
    this.editForm.patchValue({
      id: paymentStatus.id,
      statusName: paymentStatus.statusName,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const paymentStatus = this.createFromForm();
    if (paymentStatus.id !== undefined) {
      this.subscribeToSaveResponse(this.paymentStatusService.update(paymentStatus));
    } else {
      this.subscribeToSaveResponse(this.paymentStatusService.create(paymentStatus));
    }
  }

  private createFromForm(): IPaymentStatus {
    return {
      ...new PaymentStatus(),
      id: this.editForm.get(['id'])!.value,
      statusName: this.editForm.get(['statusName'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPaymentStatus>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
