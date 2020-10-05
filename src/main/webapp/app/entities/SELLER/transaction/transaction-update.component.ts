import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITransaction, Transaction } from 'app/shared/model/SELLER/transaction.model';
import { TransactionService } from './transaction.service';
import { INursery } from 'app/shared/model/SELLER/nursery.model';
import { NurseryService } from 'app/entities/SELLER/nursery/nursery.service';

@Component({
  selector: 'jhi-transaction-update',
  templateUrl: './transaction-update.component.html',
})
export class TransactionUpdateComponent implements OnInit {
  isSaving = false;
  nurseries: INursery[] = [];
  transactionDateDp: any;

  editForm = this.fb.group({
    id: [],
    userName: [],
    transactionAmount: [],
    transactionDate: [],
    transactionMethod: [],
    processedBy: [],
    nurseryId: [],
  });

  constructor(
    protected transactionService: TransactionService,
    protected nurseryService: NurseryService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ transaction }) => {
      this.updateForm(transaction);

      this.nurseryService.query().subscribe((res: HttpResponse<INursery[]>) => (this.nurseries = res.body || []));
    });
  }

  updateForm(transaction: ITransaction): void {
    this.editForm.patchValue({
      id: transaction.id,
      userName: transaction.userName,
      transactionAmount: transaction.transactionAmount,
      transactionDate: transaction.transactionDate,
      transactionMethod: transaction.transactionMethod,
      processedBy: transaction.processedBy,
      nurseryId: transaction.nurseryId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const transaction = this.createFromForm();
    if (transaction.id !== undefined) {
      this.subscribeToSaveResponse(this.transactionService.update(transaction));
    } else {
      this.subscribeToSaveResponse(this.transactionService.create(transaction));
    }
  }

  private createFromForm(): ITransaction {
    return {
      ...new Transaction(),
      id: this.editForm.get(['id'])!.value,
      userName: this.editForm.get(['userName'])!.value,
      transactionAmount: this.editForm.get(['transactionAmount'])!.value,
      transactionDate: this.editForm.get(['transactionDate'])!.value,
      transactionMethod: this.editForm.get(['transactionMethod'])!.value,
      processedBy: this.editForm.get(['processedBy'])!.value,
      nurseryId: this.editForm.get(['nurseryId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITransaction>>): void {
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

  trackById(index: number, item: INursery): any {
    return item.id;
  }
}
