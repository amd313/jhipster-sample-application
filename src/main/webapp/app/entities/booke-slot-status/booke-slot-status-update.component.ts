import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IBookeSlotStatus, BookeSlotStatus } from 'app/shared/model/booke-slot-status.model';
import { BookeSlotStatusService } from './booke-slot-status.service';

@Component({
  selector: 'jhi-booke-slot-status-update',
  templateUrl: './booke-slot-status-update.component.html',
})
export class BookeSlotStatusUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    statusName: [],
  });

  constructor(
    protected bookeSlotStatusService: BookeSlotStatusService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ bookeSlotStatus }) => {
      this.updateForm(bookeSlotStatus);
    });
  }

  updateForm(bookeSlotStatus: IBookeSlotStatus): void {
    this.editForm.patchValue({
      id: bookeSlotStatus.id,
      statusName: bookeSlotStatus.statusName,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const bookeSlotStatus = this.createFromForm();
    if (bookeSlotStatus.id !== undefined) {
      this.subscribeToSaveResponse(this.bookeSlotStatusService.update(bookeSlotStatus));
    } else {
      this.subscribeToSaveResponse(this.bookeSlotStatusService.create(bookeSlotStatus));
    }
  }

  private createFromForm(): IBookeSlotStatus {
    return {
      ...new BookeSlotStatus(),
      id: this.editForm.get(['id'])!.value,
      statusName: this.editForm.get(['statusName'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBookeSlotStatus>>): void {
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
