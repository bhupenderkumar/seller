import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IProduct, Product } from 'app/shared/model/SELLER/product.model';
import { ProductService } from './product.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { INursery } from 'app/shared/model/SELLER/nursery.model';
import { NurseryService } from 'app/entities/SELLER/nursery/nursery.service';

@Component({
  selector: 'jhi-product-update',
  templateUrl: './product-update.component.html',
})
export class ProductUpdateComponent implements OnInit {
  isSaving = false;
  nurseries: INursery[] = [];
  productDateDp: any;
  updatedDateDp: any;

  editForm = this.fb.group({
    id: [],
    productName: [null, [Validators.required, Validators.minLength(5), Validators.maxLength(500)]],
    productDescription: [null, [Validators.required, Validators.maxLength(3999)]],
    price: [null, [Validators.required, Validators.min(10), Validators.max(1000)]],
    approval: [],
    showStatus: [],
    productType: [],
    video: [],
    videoContentType: [],
    productDate: [],
    updatedDate: [],
    userName: [],
    nurseryId: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected productService: ProductService,
    protected nurseryService: NurseryService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ product }) => {
      this.updateForm(product);

      this.nurseryService.query().subscribe((res: HttpResponse<INursery[]>) => (this.nurseries = res.body || []));
    });
  }

  updateForm(product: IProduct): void {
    this.editForm.patchValue({
      id: product.id,
      productName: product.productName,
      productDescription: product.productDescription,
      price: product.price,
      approval: product.approval,
      showStatus: product.showStatus,
      productType: product.productType,
      video: product.video,
      videoContentType: product.videoContentType,
      productDate: product.productDate,
      updatedDate: product.updatedDate,
      userName: product.userName,
      nurseryId: product.nurseryId,
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  setFileData(event: any, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe(null, (err: JhiFileLoadError) => {
      this.eventManager.broadcast(
        new JhiEventWithContent<AlertError>('haryaliApp.error', { ...err, key: 'error.file.' + err.key })
      );
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const product = this.createFromForm();
    if (product.id !== undefined) {
      this.subscribeToSaveResponse(this.productService.update(product));
    } else {
      this.subscribeToSaveResponse(this.productService.create(product));
    }
  }

  private createFromForm(): IProduct {
    return {
      ...new Product(),
      id: this.editForm.get(['id'])!.value,
      productName: this.editForm.get(['productName'])!.value,
      productDescription: this.editForm.get(['productDescription'])!.value,
      price: this.editForm.get(['price'])!.value,
      approval: this.editForm.get(['approval'])!.value,
      showStatus: this.editForm.get(['showStatus'])!.value,
      productType: this.editForm.get(['productType'])!.value,
      videoContentType: this.editForm.get(['videoContentType'])!.value,
      video: this.editForm.get(['video'])!.value,
      productDate: this.editForm.get(['productDate'])!.value,
      updatedDate: this.editForm.get(['updatedDate'])!.value,
      userName: this.editForm.get(['userName'])!.value,
      nurseryId: this.editForm.get(['nurseryId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProduct>>): void {
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
