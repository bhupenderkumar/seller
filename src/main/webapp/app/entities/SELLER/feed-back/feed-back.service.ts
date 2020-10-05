import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { IFeedBack } from 'app/shared/model/SELLER/feed-back.model';

type EntityResponseType = HttpResponse<IFeedBack>;
type EntityArrayResponseType = HttpResponse<IFeedBack[]>;

@Injectable({ providedIn: 'root' })
export class FeedBackService {
  public resourceUrl = SERVER_API_URL + 'api/feed-backs';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/feed-backs';

  constructor(protected http: HttpClient) {}

  create(feedBack: IFeedBack): Observable<EntityResponseType> {
    return this.http.post<IFeedBack>(this.resourceUrl, feedBack, { observe: 'response' });
  }

  update(feedBack: IFeedBack): Observable<EntityResponseType> {
    return this.http.put<IFeedBack>(this.resourceUrl, feedBack, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IFeedBack>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFeedBack[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFeedBack[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
