import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBookeSlotStatus } from 'app/shared/model/booke-slot-status.model';

@Component({
  selector: 'jhi-booke-slot-status-detail',
  templateUrl: './booke-slot-status-detail.component.html',
})
export class BookeSlotStatusDetailComponent implements OnInit {
  bookeSlotStatus: IBookeSlotStatus | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ bookeSlotStatus }) => (this.bookeSlotStatus = bookeSlotStatus));
  }

  previousState(): void {
    window.history.back();
  }
}
