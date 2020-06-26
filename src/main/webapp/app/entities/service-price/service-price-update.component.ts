import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IServicePrice, ServicePrice } from 'app/shared/model/service-price.model';
import { ServicePriceService } from './service-price.service';
import { IEmployee } from 'app/shared/model/employee.model';
import { EmployeeService } from 'app/entities/employee/employee.service';
import { IService } from 'app/shared/model/service.model';
import { ServiceService } from 'app/entities/service/service.service';

type SelectableEntity = IEmployee | IService;

@Component({
  selector: 'jhi-service-price-update',
  templateUrl: './service-price-update.component.html',
})
export class ServicePriceUpdateComponent implements OnInit {
  isSaving = false;
  employees: IEmployee[] = [];
  services: IService[] = [];

  editForm = this.fb.group({
    id: [],
    amount: [],
    employee: [],
    service: [],
  });

  constructor(
    protected servicePriceService: ServicePriceService,
    protected employeeService: EmployeeService,
    protected serviceService: ServiceService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ servicePrice }) => {
      this.updateForm(servicePrice);

      this.employeeService
        .query({ filter: 'serviceprice-is-null' })
        .pipe(
          map((res: HttpResponse<IEmployee[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IEmployee[]) => {
          if (!servicePrice.employee || !servicePrice.employee.id) {
            this.employees = resBody;
          } else {
            this.employeeService
              .find(servicePrice.employee.id)
              .pipe(
                map((subRes: HttpResponse<IEmployee>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IEmployee[]) => (this.employees = concatRes));
          }
        });

      this.serviceService
        .query({ filter: 'serviceprice-is-null' })
        .pipe(
          map((res: HttpResponse<IService[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IService[]) => {
          if (!servicePrice.service || !servicePrice.service.id) {
            this.services = resBody;
          } else {
            this.serviceService
              .find(servicePrice.service.id)
              .pipe(
                map((subRes: HttpResponse<IService>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IService[]) => (this.services = concatRes));
          }
        });
    });
  }

  updateForm(servicePrice: IServicePrice): void {
    this.editForm.patchValue({
      id: servicePrice.id,
      amount: servicePrice.amount,
      employee: servicePrice.employee,
      service: servicePrice.service,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const servicePrice = this.createFromForm();
    if (servicePrice.id !== undefined) {
      this.subscribeToSaveResponse(this.servicePriceService.update(servicePrice));
    } else {
      this.subscribeToSaveResponse(this.servicePriceService.create(servicePrice));
    }
  }

  private createFromForm(): IServicePrice {
    return {
      ...new ServicePrice(),
      id: this.editForm.get(['id'])!.value,
      amount: this.editForm.get(['amount'])!.value,
      employee: this.editForm.get(['employee'])!.value,
      service: this.editForm.get(['service'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IServicePrice>>): void {
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
