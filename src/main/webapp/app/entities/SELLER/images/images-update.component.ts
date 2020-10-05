import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IImages, Images } from 'app/shared/model/SELLER/images.model';
import { ImagesService } from './images.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IProduct } from 'app/shared/model/SELLER/product.model';
import { ProductService } from 'app/entities/SELLER/product/product.service';

@Component({
  selector: 'jhi-images-update',
  templateUrl: './images-update.component.html',
})
export class ImagesUpdateComponent implements OnInit {
  isSaving = false;
  products: IProduct[] = [];

  editForm = this.fb.group({
    id: [],
    image: [null, []],
    imageContentType: [],
    thumbImage: [],
    thumbImageContentType: [],
    alt: [null, [Validators.maxLength(500)]],
    title: [null, [Validators.maxLength(500)]],
    productId: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected imagesService: ImagesService,
    protected productService: ProductService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ images }) => {
      this.updateForm(images);

      this.productService.query().subscribe((res: HttpResponse<IProduct[]>) => (this.products = res.body || []));
    });
  }

  updateForm(images: IImages): void {
    this.editForm.patchValue({
      id: images.id,
      image: images.image,
      imageContentType: images.imageContentType,
      thumbImage: images.thumbImage,
      thumbImageContentType: images.thumbImageContentType,
      alt: images.alt,
      title: images.title,
      productId: images.productId,
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

  clearInputImage(field: string, fieldContentType: string, idInput: string): void {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null,
    });
    if (this.elementRef && idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const images = this.createFromForm();
    if (images.id !== undefined) {
      this.subscribeToSaveResponse(this.imagesService.update(images));
    } else {
      this.subscribeToSaveResponse(this.imagesService.create(images));
    }
  }

  private createFromForm(): IImages {
    return {
      ...new Images(),
      id: this.editForm.get(['id'])!.value,
      imageContentType: this.editForm.get(['imageContentType'])!.value,
      image: this.editForm.get(['image'])!.value,
      thumbImageContentType: this.editForm.get(['thumbImageContentType'])!.value,
      thumbImage: this.editForm.get(['thumbImage'])!.value,
      alt: this.editForm.get(['alt'])!.value,
      title: this.editForm.get(['title'])!.value,
      productId: this.editForm.get(['productId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IImages>>): void {
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
