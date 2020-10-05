import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IQuote1 } from 'app/shared/model/SELLER/quote-1.model';
import { Quote1Service } from './quote-1.service';

@Component({
  templateUrl: './quote-1-delete-dialog.component.html',
})
export class Quote1DeleteDialogComponent {
  quote1?: IQuote1;

  constructor(protected quote1Service: Quote1Service, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.quote1Service.delete(id).subscribe(() => {
      this.eventManager.broadcast('quote1ListModification');
      this.activeModal.close();
    });
  }
}
