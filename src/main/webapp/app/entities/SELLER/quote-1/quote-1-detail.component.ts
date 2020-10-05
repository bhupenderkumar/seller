import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IQuote1 } from 'app/shared/model/SELLER/quote-1.model';

@Component({
  selector: 'jhi-quote-1-detail',
  templateUrl: './quote-1-detail.component.html',
})
export class Quote1DetailComponent implements OnInit {
  quote1: IQuote1 | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ quote1 }) => (this.quote1 = quote1));
  }

  previousState(): void {
    window.history.back();
  }
}
