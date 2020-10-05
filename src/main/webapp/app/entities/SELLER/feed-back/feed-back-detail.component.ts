import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFeedBack } from 'app/shared/model/SELLER/feed-back.model';

@Component({
  selector: 'jhi-feed-back-detail',
  templateUrl: './feed-back-detail.component.html',
})
export class FeedBackDetailComponent implements OnInit {
  feedBack: IFeedBack | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ feedBack }) => (this.feedBack = feedBack));
  }

  previousState(): void {
    window.history.back();
  }
}
