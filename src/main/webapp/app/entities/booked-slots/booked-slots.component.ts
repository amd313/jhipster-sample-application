import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IBookedSlots } from 'app/shared/model/booked-slots.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { BookedSlotsService } from './booked-slots.service';
import { BookedSlotsDeleteDialogComponent } from './booked-slots-delete-dialog.component';

@Component({
  selector: 'jhi-booked-slots',
  templateUrl: './booked-slots.component.html',
})
export class BookedSlotsComponent implements OnInit, OnDestroy {
  bookedSlots: IBookedSlots[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected bookedSlotsService: BookedSlotsService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.bookedSlots = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0,
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.bookedSlotsService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe((res: HttpResponse<IBookedSlots[]>) => this.paginateBookedSlots(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.bookedSlots = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInBookedSlots();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IBookedSlots): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInBookedSlots(): void {
    this.eventSubscriber = this.eventManager.subscribe('bookedSlotsListModification', () => this.reset());
  }

  delete(bookedSlots: IBookedSlots): void {
    const modalRef = this.modalService.open(BookedSlotsDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.bookedSlots = bookedSlots;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateBookedSlots(data: IBookedSlots[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.bookedSlots.push(data[i]);
      }
    }
  }
}
