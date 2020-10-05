import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { INursery, Nursery } from 'app/shared/model/SELLER/nursery.model';
import { NurseryService } from './nursery.service';

@Component({
  selector: 'jhi-nursery-update',
  templateUrl: './nursery-update.component.html',
})
export class NurseryUpdateComponent implements OnInit {
  isSaving = false;
  createdDateDp: any;
  updatedDateDp: any;

  editForm = this.fb.group({
    id: [],
    nurseryName: [],
    houseNo: [],
    salutation: [],
    firstName: [null, [Validators.required]],
    lastName: [],
    middleName: [],
    streetNo: [],
    districtNo: [],
    city: [],
    state: [],
    country: [],
    latitude: [],
    longitude: [],
    addharNo: [],
    panCardNo: [],
    payMentMode: [],
    upiId: [],
    payMentDuration: [],
    accountNo: [],
    bankIFSC: [],
    bankName: [],
    createdDate: [],
    updatedDate: [],
    userName: [],
  });

  constructor(protected nurseryService: NurseryService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ nursery }) => {
      this.updateForm(nursery);
    });
  }

  updateForm(nursery: INursery): void {
    this.editForm.patchValue({
      id: nursery.id,
      nurseryName: nursery.nurseryName,
      houseNo: nursery.houseNo,
      salutation: nursery.salutation,
      firstName: nursery.firstName,
      lastName: nursery.lastName,
      middleName: nursery.middleName,
      streetNo: nursery.streetNo,
      districtNo: nursery.districtNo,
      city: nursery.city,
      state: nursery.state,
      country: nursery.country,
      latitude: nursery.latitude,
      longitude: nursery.longitude,
      addharNo: nursery.addharNo,
      panCardNo: nursery.panCardNo,
      payMentMode: nursery.payMentMode,
      upiId: nursery.upiId,
      payMentDuration: nursery.payMentDuration,
      accountNo: nursery.accountNo,
      bankIFSC: nursery.bankIFSC,
      bankName: nursery.bankName,
      createdDate: nursery.createdDate,
      updatedDate: nursery.updatedDate,
      userName: nursery.userName,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const nursery = this.createFromForm();
    if (nursery.id !== undefined) {
      this.subscribeToSaveResponse(this.nurseryService.update(nursery));
    } else {
      this.subscribeToSaveResponse(this.nurseryService.create(nursery));
    }
  }

  private createFromForm(): INursery {
    return {
      ...new Nursery(),
      id: this.editForm.get(['id'])!.value,
      nurseryName: this.editForm.get(['nurseryName'])!.value,
      houseNo: this.editForm.get(['houseNo'])!.value,
      salutation: this.editForm.get(['salutation'])!.value,
      firstName: this.editForm.get(['firstName'])!.value,
      lastName: this.editForm.get(['lastName'])!.value,
      middleName: this.editForm.get(['middleName'])!.value,
      streetNo: this.editForm.get(['streetNo'])!.value,
      districtNo: this.editForm.get(['districtNo'])!.value,
      city: this.editForm.get(['city'])!.value,
      state: this.editForm.get(['state'])!.value,
      country: this.editForm.get(['country'])!.value,
      latitude: this.editForm.get(['latitude'])!.value,
      longitude: this.editForm.get(['longitude'])!.value,
      addharNo: this.editForm.get(['addharNo'])!.value,
      panCardNo: this.editForm.get(['panCardNo'])!.value,
      payMentMode: this.editForm.get(['payMentMode'])!.value,
      upiId: this.editForm.get(['upiId'])!.value,
      payMentDuration: this.editForm.get(['payMentDuration'])!.value,
      accountNo: this.editForm.get(['accountNo'])!.value,
      bankIFSC: this.editForm.get(['bankIFSC'])!.value,
      bankName: this.editForm.get(['bankName'])!.value,
      createdDate: this.editForm.get(['createdDate'])!.value,
      updatedDate: this.editForm.get(['updatedDate'])!.value,
      userName: this.editForm.get(['userName'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<INursery>>): void {
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
