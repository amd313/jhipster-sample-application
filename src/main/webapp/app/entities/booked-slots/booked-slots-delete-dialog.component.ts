import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBookedSlots } from 'app/shared/model/booked-slots.model';
import { BookedSlotsService } from './booked-slots.service';

@Component({
  templateUrl: './booked-slots-delete-dialog.component.html',
})
export class BookedSlotsDeleteDialogComponent {
  bookedSlots?: IBookedSlots;

  constructor(
    protected bookedSlotsService: BookedSlotsService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.bookedSlotsService.delete(id).subscribe(() => {
      this.eventManager.broadcast('bookedSlotsListModification');
      this.activeModal.close();
    });
  }
}
