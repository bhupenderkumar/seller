import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { IImages } from 'app/shared/model/SELLER/images.model';

type EntityResponseType = HttpResponse<IImages>;
type EntityArrayResponseType = HttpResponse<IImages[]>;

@Injectable({ providedIn: 'root' })
export class ImagesService {
  public resourceUrl = SERVER_API_URL + 'api/images';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/images';

  constructor(protected http: HttpClient) {}

  create(images: IImages): Observable<EntityResponseType> {
    return this.http.post<IImages>(this.resourceUrl, images, { observe: 'response' });
  }

  update(images: IImages): Observable<EntityResponseType> {
    return this.http.put<IImages>(this.resourceUrl, images, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IImages>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IImages[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IImages[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
