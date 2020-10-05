import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { INursery } from 'app/shared/model/SELLER/nursery.model';
import { NurseryService } from './nursery.service';

@Component({
  templateUrl: './nursery-delete-dialog.component.html',
})
export class NurseryDeleteDialogComponent {
  nursery?: INursery;

  constructor(protected nurseryService: NurseryService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.nurseryService.delete(id).subscribe(() => {
      this.eventManager.broadcast('nurseryListModification');
      this.activeModal.close();
    });
  }
}
