import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFeedBack } from 'app/shared/model/SELLER/feed-back.model';
import { FeedBackService } from './feed-back.service';

@Component({
  templateUrl: './feed-back-delete-dialog.component.html',
})
export class FeedBackDeleteDialogComponent {
  feedBack?: IFeedBack;

  constructor(protected feedBackService: FeedBackService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.feedBackService.delete(id).subscribe(() => {
      this.eventManager.broadcast('feedBackListModification');
      this.activeModal.close();
    });
  }
}
