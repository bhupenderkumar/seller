import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IFeedBack, FeedBack } from 'app/shared/model/SELLER/feed-back.model';
import { FeedBackService } from './feed-back.service';
import { FeedBackComponent } from './feed-back.component';
import { FeedBackDetailComponent } from './feed-back-detail.component';
import { FeedBackUpdateComponent } from './feed-back-update.component';

@Injectable({ providedIn: 'root' })
export class FeedBackResolve implements Resolve<IFeedBack> {
  constructor(private service: FeedBackService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFeedBack> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((feedBack: HttpResponse<FeedBack>) => {
          if (feedBack.body) {
            return of(feedBack.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new FeedBack());
  }
}

export const feedBackRoute: Routes = [
  {
    path: '',
    component: FeedBackComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'haryaliApp.sellerFeedBack.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: FeedBackDetailComponent,
    resolve: {
      feedBack: FeedBackResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'haryaliApp.sellerFeedBack.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: FeedBackUpdateComponent,
    resolve: {
      feedBack: FeedBackResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'haryaliApp.sellerFeedBack.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: FeedBackUpdateComponent,
    resolve: {
      feedBack: FeedBackResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'haryaliApp.sellerFeedBack.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
