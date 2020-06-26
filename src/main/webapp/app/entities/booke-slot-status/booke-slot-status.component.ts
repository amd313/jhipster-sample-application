import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IBookeSlotStatus } from 'app/shared/model/booke-slot-status.model';
import { BookeSlotStatusService } from './booke-slot-status.service';
import { BookeSlotStatusDeleteDialogComponent } from './booke-slot-status-delete-dialog.component';

@Component({
  selector: 'jhi-booke-slot-status',
  templateUrl: './booke-slot-status.component.html',
})
export class BookeSlotStatusComponent implements OnInit, OnDestroy {
  bookeSlotStatuses?: IBookeSlotStatus[];
  eventSubscriber?: Subscription;

  constructor(
    protected bookeSlotStatusService: BookeSlotStatusService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.bookeSlotStatusService.query().subscribe((res: HttpResponse<IBookeSlotStatus[]>) => (this.bookeSlotStatuses = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInBookeSlotStatuses();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IBookeSlotStatus): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInBookeSlotStatuses(): void {
    this.eventSubscriber = this.eventManager.subscribe('bookeSlotStatusListModification', () => this.loadAll());
  }

  delete(bookeSlotStatus: IBookeSlotStatus): void {
    const modalRef = this.modalService.open(BookeSlotStatusDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.bookeSlotStatus = bookeSlotStatus;
  }
}
