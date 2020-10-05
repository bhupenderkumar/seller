import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { INursery } from 'app/shared/model/SELLER/nursery.model';

@Component({
  selector: 'jhi-nursery-detail',
  templateUrl: './nursery-detail.component.html',
})
export class NurseryDetailComponent implements OnInit {
  nursery: INursery | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ nursery }) => (this.nursery = nursery));
  }

  previousState(): void {
    window.history.back();
  }
}
