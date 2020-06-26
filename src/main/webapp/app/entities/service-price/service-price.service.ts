import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IServicePrice } from 'app/shared/model/service-price.model';

type EntityResponseType = HttpResponse<IServicePrice>;
type EntityArrayResponseType = HttpResponse<IServicePrice[]>;

@Injectable({ providedIn: 'root' })
export class ServicePriceService {
  public resourceUrl = SERVER_API_URL + 'api/service-prices';

  constructor(protected http: HttpClient) {}

  create(servicePrice: IServicePrice): Observable<EntityResponseType> {
    return this.http.post<IServicePrice>(this.resourceUrl, servicePrice, { observe: 'response' });
  }

  update(servicePrice: IServicePrice): Observable<EntityResponseType> {
    return this.http.put<IServicePrice>(this.resourceUrl, servicePrice, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IServicePrice>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IServicePrice[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
