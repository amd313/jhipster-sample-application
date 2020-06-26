import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICustomerPayment } from 'app/shared/model/customer-payment.model';

type EntityResponseType = HttpResponse<ICustomerPayment>;
type EntityArrayResponseType = HttpResponse<ICustomerPayment[]>;

@Injectable({ providedIn: 'root' })
export class CustomerPaymentService {
  public resourceUrl = SERVER_API_URL + 'api/customer-payments';

  constructor(protected http: HttpClient) {}

  create(customerPayment: ICustomerPayment): Observable<EntityResponseType> {
    return this.http.post<ICustomerPayment>(this.resourceUrl, customerPayment, { observe: 'response' });
  }

  update(customerPayment: ICustomerPayment): Observable<EntityResponseType> {
    return this.http.put<ICustomerPayment>(this.resourceUrl, customerPayment, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICustomerPayment>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICustomerPayment[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
