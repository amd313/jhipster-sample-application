import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBookedSlots } from 'app/shared/model/booked-slots.model';

@Component({
  selector: 'jhi-booked-slots-detail',
  templateUrl: './booked-slots-detail.component.html',
})
export class BookedSlotsDetailComponent implements OnInit {
  bookedSlots: IBookedSlots | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ bookedSlots }) => (this.bookedSlots = bookedSlots));
  }

  previousState(): void {
    window.history.back();
  }
}
