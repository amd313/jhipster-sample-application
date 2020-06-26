import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IBookeSlotStatus, BookeSlotStatus } from 'app/shared/model/booke-slot-status.model';
import { BookeSlotStatusService } from './booke-slot-status.service';
import { BookeSlotStatusComponent } from './booke-slot-status.component';
import { BookeSlotStatusDetailComponent } from './booke-slot-status-detail.component';
import { BookeSlotStatusUpdateComponent } from './booke-slot-status-update.component';

@Injectable({ providedIn: 'root' })
export class BookeSlotStatusResolve implements Resolve<IBookeSlotStatus> {
  constructor(private service: BookeSlotStatusService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IBookeSlotStatus> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((bookeSlotStatus: HttpResponse<BookeSlotStatus>) => {
          if (bookeSlotStatus.body) {
            return of(bookeSlotStatus.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new BookeSlotStatus());
  }
}

export const bookeSlotStatusRoute: Routes = [
  {
    path: '',
    component: BookeSlotStatusComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.bookeSlotStatus.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: BookeSlotStatusDetailComponent,
    resolve: {
      bookeSlotStatus: BookeSlotStatusResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.bookeSlotStatus.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: BookeSlotStatusUpdateComponent,
    resolve: {
      bookeSlotStatus: BookeSlotStatusResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.bookeSlotStatus.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: BookeSlotStatusUpdateComponent,
    resolve: {
      bookeSlotStatus: BookeSlotStatusResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.bookeSlotStatus.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
