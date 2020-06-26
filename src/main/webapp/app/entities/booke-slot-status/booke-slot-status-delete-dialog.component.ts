import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBookeSlotStatus } from 'app/shared/model/booke-slot-status.model';
import { BookeSlotStatusService } from './booke-slot-status.service';

@Component({
  templateUrl: './booke-slot-status-delete-dialog.component.html',
})
export class BookeSlotStatusDeleteDialogComponent {
  bookeSlotStatus?: IBookeSlotStatus;

  constructor(
    protected bookeSlotStatusService: BookeSlotStatusService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.bookeSlotStatusService.delete(id).subscribe(() => {
      this.eventManager.broadcast('bookeSlotStatusListModification');
      this.activeModal.close();
    });
  }
}
