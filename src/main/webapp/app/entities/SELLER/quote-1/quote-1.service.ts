import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { IQuote1 } from 'app/shared/model/SELLER/quote-1.model';

type EntityResponseType = HttpResponse<IQuote1>;
type EntityArrayResponseType = HttpResponse<IQuote1[]>;

@Injectable({ providedIn: 'root' })
export class Quote1Service {
  public resourceUrl = SERVER_API_URL + 'api/quote-1-s';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/quote-1-s';

  constructor(protected http: HttpClient) {}

  create(quote1: IQuote1): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(quote1);
    return this.http
      .post<IQuote1>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(quote1: IQuote1): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(quote1);
    return this.http
      .put<IQuote1>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IQuote1>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IQuote1[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IQuote1[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(quote1: IQuote1): IQuote1 {
    const copy: IQuote1 = Object.assign({}, quote1, {
      lastTrade: quote1.lastTrade && quote1.lastTrade.isValid() ? quote1.lastTrade.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.lastTrade = res.body.lastTrade ? moment(res.body.lastTrade) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((quote1: IQuote1) => {
        quote1.lastTrade = quote1.lastTrade ? moment(quote1.lastTrade) : undefined;
      });
    }
    return res;
  }
}
