import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICustomerPayment, CustomerPayment } from 'app/shared/model/customer-payment.model';
import { CustomerPaymentService } from './customer-payment.service';

@Component({
  selector: 'jhi-customer-payment-update',
  templateUrl: './customer-payment-update.component.html',
})
export class CustomerPaymentUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    status: [],
    amount: [],
  });

  constructor(
    protected customerPaymentService: CustomerPaymentService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ customerPayment }) => {
      this.updateForm(customerPayment);
    });
  }

  updateForm(customerPayment: ICustomerPayment): void {
    this.editForm.patchValue({
      id: customerPayment.id,
      status: customerPayment.status,
      amount: customerPayment.amount,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const customerPayment = this.createFromForm();
    if (customerPayment.id !== undefined) {
      this.subscribeToSaveResponse(this.customerPaymentService.update(customerPayment));
    } else {
      this.subscribeToSaveResponse(this.customerPaymentService.create(customerPayment));
    }
  }

  private createFromForm(): ICustomerPayment {
    return {
      ...new CustomerPayment(),
      id: this.editForm.get(['id'])!.value,
      status: this.editForm.get(['status'])!.value,
      amount: this.editForm.get(['amount'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICustomerPayment>>): void {
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
