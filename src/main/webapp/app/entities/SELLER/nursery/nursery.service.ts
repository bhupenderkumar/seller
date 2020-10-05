import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { INursery } from 'app/shared/model/SELLER/nursery.model';

type EntityResponseType = HttpResponse<INursery>;
type EntityArrayResponseType = HttpResponse<INursery[]>;

@Injectable({ providedIn: 'root' })
export class NurseryService {
  public resourceUrl = SERVER_API_URL + 'api/nurseries';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/nurseries';

  constructor(protected http: HttpClient) {}

  create(nursery: INursery): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(nursery);
    return this.http
      .post<INursery>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(nursery: INursery): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(nursery);
    return this.http
      .put<INursery>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<INursery>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<INursery[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<INursery[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(nursery: INursery): INursery {
    const copy: INursery = Object.assign({}, nursery, {
      createdDate: nursery.createdDate && nursery.createdDate.isValid() ? nursery.createdDate.format(DATE_FORMAT) : undefined,
      updatedDate: nursery.updatedDate && nursery.updatedDate.isValid() ? nursery.updatedDate.format(DATE_FORMAT) : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.createdDate = res.body.createdDate ? moment(res.body.createdDate) : undefined;
      res.body.updatedDate = res.body.updatedDate ? moment(res.body.updatedDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((nursery: INursery) => {
        nursery.createdDate = nursery.createdDate ? moment(nursery.createdDate) : undefined;
        nursery.updatedDate = nursery.updatedDate ? moment(nursery.updatedDate) : undefined;
      });
    }
    return res;
  }
}
