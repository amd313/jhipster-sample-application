import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IBookedSlots } from 'app/shared/model/booked-slots.model';

type EntityResponseType = HttpResponse<IBookedSlots>;
type EntityArrayResponseType = HttpResponse<IBookedSlots[]>;

@Injectable({ providedIn: 'root' })
export class BookedSlotsService {
  public resourceUrl = SERVER_API_URL + 'api/booked-slots';

  constructor(protected http: HttpClient) {}

  create(bookedSlots: IBookedSlots): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(bookedSlots);
    return this.http
      .post<IBookedSlots>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(bookedSlots: IBookedSlots): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(bookedSlots);
    return this.http
      .put<IBookedSlots>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IBookedSlots>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IBookedSlots[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(bookedSlots: IBookedSlots): IBookedSlots {
    const copy: IBookedSlots = Object.assign({}, bookedSlots, {
      slot: bookedSlots.slot && bookedSlots.slot.isValid() ? bookedSlots.slot.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.slot = res.body.slot ? moment(res.body.slot) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((bookedSlots: IBookedSlots) => {
        bookedSlots.slot = bookedSlots.slot ? moment(bookedSlots.slot) : undefined;
      });
    }
    return res;
  }
}
