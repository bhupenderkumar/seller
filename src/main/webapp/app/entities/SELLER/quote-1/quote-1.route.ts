import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IQuote1, Quote1 } from 'app/shared/model/SELLER/quote-1.model';
import { Quote1Service } from './quote-1.service';
import { Quote1Component } from './quote-1.component';
import { Quote1DetailComponent } from './quote-1-detail.component';
import { Quote1UpdateComponent } from './quote-1-update.component';

@Injectable({ providedIn: 'root' })
export class Quote1Resolve implements Resolve<IQuote1> {
  constructor(private service: Quote1Service, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IQuote1> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((quote1: HttpResponse<Quote1>) => {
          if (quote1.body) {
            return of(quote1.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Quote1());
  }
}

export const quote1Route: Routes = [
  {
    path: '',
    component: Quote1Component,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'haryaliApp.sellerQuote1.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: Quote1DetailComponent,
    resolve: {
      quote1: Quote1Resolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'haryaliApp.sellerQuote1.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: Quote1UpdateComponent,
    resolve: {
      quote1: Quote1Resolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'haryaliApp.sellerQuote1.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: Quote1UpdateComponent,
    resolve: {
      quote1: Quote1Resolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'haryaliApp.sellerQuote1.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
