import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IBookedSlots, BookedSlots } from 'app/shared/model/booked-slots.model';
import { BookedSlotsService } from './booked-slots.service';
import { BookedSlotsComponent } from './booked-slots.component';
import { BookedSlotsDetailComponent } from './booked-slots-detail.component';
import { BookedSlotsUpdateComponent } from './booked-slots-update.component';

@Injectable({ providedIn: 'root' })
export class BookedSlotsResolve implements Resolve<IBookedSlots> {
  constructor(private service: BookedSlotsService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IBookedSlots> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((bookedSlots: HttpResponse<BookedSlots>) => {
          if (bookedSlots.body) {
            return of(bookedSlots.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new BookedSlots());
  }
}

export const bookedSlotsRoute: Routes = [
  {
    path: '',
    component: BookedSlotsComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.bookedSlots.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: BookedSlotsDetailComponent,
    resolve: {
      bookedSlots: BookedSlotsResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.bookedSlots.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: BookedSlotsUpdateComponent,
    resolve: {
      bookedSlots: BookedSlotsResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.bookedSlots.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: BookedSlotsUpdateComponent,
    resolve: {
      bookedSlots: BookedSlotsResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.bookedSlots.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
