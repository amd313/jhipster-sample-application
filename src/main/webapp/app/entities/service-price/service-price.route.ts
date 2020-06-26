import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IServicePrice, ServicePrice } from 'app/shared/model/service-price.model';
import { ServicePriceService } from './service-price.service';
import { ServicePriceComponent } from './service-price.component';
import { ServicePriceDetailComponent } from './service-price-detail.component';
import { ServicePriceUpdateComponent } from './service-price-update.component';

@Injectable({ providedIn: 'root' })
export class ServicePriceResolve implements Resolve<IServicePrice> {
  constructor(private service: ServicePriceService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IServicePrice> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((servicePrice: HttpResponse<ServicePrice>) => {
          if (servicePrice.body) {
            return of(servicePrice.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ServicePrice());
  }
}

export const servicePriceRoute: Routes = [
  {
    path: '',
    component: ServicePriceComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.servicePrice.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ServicePriceDetailComponent,
    resolve: {
      servicePrice: ServicePriceResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.servicePrice.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ServicePriceUpdateComponent,
    resolve: {
      servicePrice: ServicePriceResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.servicePrice.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ServicePriceUpdateComponent,
    resolve: {
      servicePrice: ServicePriceResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.servicePrice.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
