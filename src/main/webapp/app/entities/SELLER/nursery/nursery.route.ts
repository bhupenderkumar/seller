import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { INursery, Nursery } from 'app/shared/model/SELLER/nursery.model';
import { NurseryService } from './nursery.service';
import { NurseryComponent } from './nursery.component';
import { NurseryDetailComponent } from './nursery-detail.component';
import { NurseryUpdateComponent } from './nursery-update.component';

@Injectable({ providedIn: 'root' })
export class NurseryResolve implements Resolve<INursery> {
  constructor(private service: NurseryService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<INursery> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((nursery: HttpResponse<Nursery>) => {
          if (nursery.body) {
            return of(nursery.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Nursery());
  }
}

export const nurseryRoute: Routes = [
  {
    path: '',
    component: NurseryComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'haryaliApp.sellerNursery.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: NurseryDetailComponent,
    resolve: {
      nursery: NurseryResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'haryaliApp.sellerNursery.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: NurseryUpdateComponent,
    resolve: {
      nursery: NurseryResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'haryaliApp.sellerNursery.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: NurseryUpdateComponent,
    resolve: {
      nursery: NurseryResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'haryaliApp.sellerNursery.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
