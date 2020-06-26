import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IBookeSlotStatus } from 'app/shared/model/booke-slot-status.model';

type EntityResponseType = HttpResponse<IBookeSlotStatus>;
type EntityArrayResponseType = HttpResponse<IBookeSlotStatus[]>;

@Injectable({ providedIn: 'root' })
export class BookeSlotStatusService {
  public resourceUrl = SERVER_API_URL + 'api/booke-slot-statuses';

  constructor(protected http: HttpClient) {}

  create(bookeSlotStatus: IBookeSlotStatus): Observable<EntityResponseType> {
    return this.http.post<IBookeSlotStatus>(this.resourceUrl, bookeSlotStatus, { observe: 'response' });
  }

  update(bookeSlotStatus: IBookeSlotStatus): Observable<EntityResponseType> {
    return this.http.put<IBookeSlotStatus>(this.resourceUrl, bookeSlotStatus, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IBookeSlotStatus>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IBookeSlotStatus[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
