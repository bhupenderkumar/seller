import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IQuote1, Quote1 } from 'app/shared/model/SELLER/quote-1.model';
import { Quote1Service } from './quote-1.service';

@Component({
  selector: 'jhi-quote-1-update',
  templateUrl: './quote-1-update.component.html',
})
export class Quote1UpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    symbol: [null, [Validators.required]],
    price: [null, [Validators.required]],
    lastTrade: [null, [Validators.required]],
  });

  constructor(protected quote1Service: Quote1Service, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ quote1 }) => {
      if (!quote1.id) {
        const today = moment().startOf('day');
        quote1.lastTrade = today;
      }

      this.updateForm(quote1);
    });
  }

  updateForm(quote1: IQuote1): void {
    this.editForm.patchValue({
      id: quote1.id,
      symbol: quote1.symbol,
      price: quote1.price,
      lastTrade: quote1.lastTrade ? quote1.lastTrade.format(DATE_TIME_FORMAT) : null,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const quote1 = this.createFromForm();
    if (quote1.id !== undefined) {
      this.subscribeToSaveResponse(this.quote1Service.update(quote1));
    } else {
      this.subscribeToSaveResponse(this.quote1Service.create(quote1));
    }
  }

  private createFromForm(): IQuote1 {
    return {
      ...new Quote1(),
      id: this.editForm.get(['id'])!.value,
      symbol: this.editForm.get(['symbol'])!.value,
      price: this.editForm.get(['price'])!.value,
      lastTrade: this.editForm.get(['lastTrade'])!.value ? moment(this.editForm.get(['lastTrade'])!.value, DATE_TIME_FORMAT) : undefined,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IQuote1>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
