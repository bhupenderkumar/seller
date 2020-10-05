import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IFeedBack, FeedBack } from 'app/shared/model/SELLER/feed-back.model';
import { FeedBackService } from './feed-back.service';
import { IProduct } from 'app/shared/model/SELLER/product.model';
import { ProductService } from 'app/entities/SELLER/product/product.service';

@Component({
  selector: 'jhi-feed-back-update',
  templateUrl: './feed-back-update.component.html',
})
export class FeedBackUpdateComponent implements OnInit {
  isSaving = false;
  products: IProduct[] = [];

  editForm = this.fb.group({
    id: [],
    rating: [],
    userName: [],
    userComments: [null, [Validators.required, Validators.minLength(1), Validators.maxLength(3999)]],
    productId: [],
  });

  constructor(
    protected feedBackService: FeedBackService,
    protected productService: ProductService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ feedBack }) => {
      this.updateForm(feedBack);

      this.productService.query().subscribe((res: HttpResponse<IProduct[]>) => (this.products = res.body || []));
    });
  }

  updateForm(feedBack: IFeedBack): void {
    this.editForm.patchValue({
      id: feedBack.id,
      rating: feedBack.rating,
      userName: feedBack.userName,
      userComments: feedBack.userComments,
      productId: feedBack.productId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const feedBack = this.createFromForm();
    if (feedBack.id !== undefined) {
      this.subscribeToSaveResponse(this.feedBackService.update(feedBack));
    } else {
      this.subscribeToSaveResponse(this.feedBackService.create(feedBack));
    }
  }

  private createFromForm(): IFeedBack {
    return {
      ...new FeedBack(),
      id: this.editForm.get(['id'])!.value,
      rating: this.editForm.get(['rating'])!.value,
      userName: this.editForm.get(['userName'])!.value,
      userComments: this.editForm.get(['userComments'])!.value,
      productId: this.editForm.get(['productId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFeedBack>>): void {
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

  trackById(index: number, item: IProduct): any {
    return item.id;
  }
}
