import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPaymentStatus } from 'app/shared/model/payment-status.model';

type EntityResponseType = HttpResponse<IPaymentStatus>;
type EntityArrayResponseType = HttpResponse<IPaymentStatus[]>;

@Injectable({ providedIn: 'root' })
export class PaymentStatusService {
  public resourceUrl = SERVER_API_URL + 'api/payment-statuses';

  constructor(protected http: HttpClient) {}

  create(paymentStatus: IPaymentStatus): Observable<EntityResponseType> {
    return this.http.post<IPaymentStatus>(this.resourceUrl, paymentStatus, { observe: 'response' });
  }

  update(paymentStatus: IPaymentStatus): Observable<EntityResponseType> {
    return this.http.put<IPaymentStatus>(this.resourceUrl, paymentStatus, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPaymentStatus>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPaymentStatus[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
