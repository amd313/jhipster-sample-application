import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IBookedSlots, BookedSlots } from 'app/shared/model/booked-slots.model';
import { BookedSlotsService } from './booked-slots.service';
import { ICustomer } from 'app/shared/model/customer.model';
import { CustomerService } from 'app/entities/customer/customer.service';
import { IService } from 'app/shared/model/service.model';
import { ServiceService } from 'app/entities/service/service.service';
import { IServicePrice } from 'app/shared/model/service-price.model';
import { ServicePriceService } from 'app/entities/service-price/service-price.service';
import { IEmployee } from 'app/shared/model/employee.model';
import { EmployeeService } from 'app/entities/employee/employee.service';
import { ICustomerPayment } from 'app/shared/model/customer-payment.model';
import { CustomerPaymentService } from 'app/entities/customer-payment/customer-payment.service';

type SelectableEntity = ICustomer | IService | IServicePrice | IEmployee | ICustomerPayment;

@Component({
  selector: 'jhi-booked-slots-update',
  templateUrl: './booked-slots-update.component.html',
})
export class BookedSlotsUpdateComponent implements OnInit {
  isSaving = false;
  customers: ICustomer[] = [];
  services: IService[] = [];
  serviceprices: IServicePrice[] = [];
  employees: IEmployee[] = [];
  payments: ICustomerPayment[] = [];

  editForm = this.fb.group({
    id: [],
    slot: [],
    customerName: [],
    employeeName: [],
    serviceName: [],
    categoryName: [],
    status: [],
    paymentStatus: [],
    customer: [],
    service: [],
    servicePrice: [],
    employee: [],
    payment: [],
  });

  constructor(
    protected bookedSlotsService: BookedSlotsService,
    protected customerService: CustomerService,
    protected serviceService: ServiceService,
    protected servicePriceService: ServicePriceService,
    protected employeeService: EmployeeService,
    protected customerPaymentService: CustomerPaymentService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ bookedSlots }) => {
      if (!bookedSlots.id) {
        const today = moment().startOf('day');
        bookedSlots.slot = today;
      }

      this.updateForm(bookedSlots);

      this.customerService
        .query({ filter: 'bookedslots-is-null' })
        .pipe(
          map((res: HttpResponse<ICustomer[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: ICustomer[]) => {
          if (!bookedSlots.customer || !bookedSlots.customer.id) {
            this.customers = resBody;
          } else {
            this.customerService
              .find(bookedSlots.customer.id)
              .pipe(
                map((subRes: HttpResponse<ICustomer>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: ICustomer[]) => (this.customers = concatRes));
          }
        });

      this.serviceService
        .query({ filter: 'bookedslots-is-null' })
        .pipe(
          map((res: HttpResponse<IService[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IService[]) => {
          if (!bookedSlots.service || !bookedSlots.service.id) {
            this.services = resBody;
          } else {
            this.serviceService
              .find(bookedSlots.service.id)
              .pipe(
                map((subRes: HttpResponse<IService>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IService[]) => (this.services = concatRes));
          }
        });

      this.servicePriceService
        .query({ filter: 'bookedslots-is-null' })
        .pipe(
          map((res: HttpResponse<IServicePrice[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IServicePrice[]) => {
          if (!bookedSlots.servicePrice || !bookedSlots.servicePrice.id) {
            this.serviceprices = resBody;
          } else {
            this.servicePriceService
              .find(bookedSlots.servicePrice.id)
              .pipe(
                map((subRes: HttpResponse<IServicePrice>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IServicePrice[]) => (this.serviceprices = concatRes));
          }
        });

      this.employeeService
        .query({ filter: 'bookedslots-is-null' })
        .pipe(
          map((res: HttpResponse<IEmployee[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IEmployee[]) => {
          if (!bookedSlots.employee || !bookedSlots.employee.id) {
            this.employees = resBody;
          } else {
            this.employeeService
              .find(bookedSlots.employee.id)
              .pipe(
                map((subRes: HttpResponse<IEmployee>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IEmployee[]) => (this.employees = concatRes));
          }
        });

      this.customerPaymentService
        .query({ filter: 'bookedslots-is-null' })
        .pipe(
          map((res: HttpResponse<ICustomerPayment[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: ICustomerPayment[]) => {
          if (!bookedSlots.payment || !bookedSlots.payment.id) {
            this.payments = resBody;
          } else {
            this.customerPaymentService
              .find(bookedSlots.payment.id)
              .pipe(
                map((subRes: HttpResponse<ICustomerPayment>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: ICustomerPayment[]) => (this.payments = concatRes));
          }
        });
    });
  }

  updateForm(bookedSlots: IBookedSlots): void {
    this.editForm.patchValue({
      id: bookedSlots.id,
      slot: bookedSlots.slot ? bookedSlots.slot.format(DATE_TIME_FORMAT) : null,
      customerName: bookedSlots.customerName,
      employeeName: bookedSlots.employeeName,
      serviceName: bookedSlots.serviceName,
      categoryName: bookedSlots.categoryName,
      status: bookedSlots.status,
      paymentStatus: bookedSlots.paymentStatus,
      customer: bookedSlots.customer,
      service: bookedSlots.service,
      servicePrice: bookedSlots.servicePrice,
      employee: bookedSlots.employee,
      payment: bookedSlots.payment,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const bookedSlots = this.createFromForm();
    if (bookedSlots.id !== undefined) {
      this.subscribeToSaveResponse(this.bookedSlotsService.update(bookedSlots));
    } else {
      this.subscribeToSaveResponse(this.bookedSlotsService.create(bookedSlots));
    }
  }

  private createFromForm(): IBookedSlots {
    return {
      ...new BookedSlots(),
      id: this.editForm.get(['id'])!.value,
      slot: this.editForm.get(['slot'])!.value ? moment(this.editForm.get(['slot'])!.value, DATE_TIME_FORMAT) : undefined,
      customerName: this.editForm.get(['customerName'])!.value,
      employeeName: this.editForm.get(['employeeName'])!.value,
      serviceName: this.editForm.get(['serviceName'])!.value,
      categoryName: this.editForm.get(['categoryName'])!.value,
      status: this.editForm.get(['status'])!.value,
      paymentStatus: this.editForm.get(['paymentStatus'])!.value,
      customer: this.editForm.get(['customer'])!.value,
      service: this.editForm.get(['service'])!.value,
      servicePrice: this.editForm.get(['servicePrice'])!.value,
      employee: this.editForm.get(['employee'])!.value,
      payment: this.editForm.get(['payment'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBookedSlots>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
