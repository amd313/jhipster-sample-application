import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPaymentStatus, PaymentStatus } from 'app/shared/model/payment-status.model';
import { PaymentStatusService } from './payment-status.service';
import { PaymentStatusComponent } from './payment-status.component';
import { PaymentStatusDetailComponent } from './payment-status-detail.component';
import { PaymentStatusUpdateComponent } from './payment-status-update.component';

@Injectable({ providedIn: 'root' })
export class PaymentStatusResolve implements Resolve<IPaymentStatus> {
  constructor(private service: PaymentStatusService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPaymentStatus> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((paymentStatus: HttpResponse<PaymentStatus>) => {
          if (paymentStatus.body) {
            return of(paymentStatus.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PaymentStatus());
  }
}

export const paymentStatusRoute: Routes = [
  {
    path: '',
    component: PaymentStatusComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.paymentStatus.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PaymentStatusDetailComponent,
    resolve: {
      paymentStatus: PaymentStatusResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.paymentStatus.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PaymentStatusUpdateComponent,
    resolve: {
      paymentStatus: PaymentStatusResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.paymentStatus.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PaymentStatusUpdateComponent,
    resolve: {
      paymentStatus: PaymentStatusResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.paymentStatus.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
