import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from 'app/shared/shared.module';
import { BookedSlotsComponent } from './booked-slots.component';
import { BookedSlotsDetailComponent } from './booked-slots-detail.component';
import { BookedSlotsUpdateComponent } from './booked-slots-update.component';
import { BookedSlotsDeleteDialogComponent } from './booked-slots-delete-dialog.component';
import { bookedSlotsRoute } from './booked-slots.route';

@NgModule({
  imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(bookedSlotsRoute)],
  declarations: [BookedSlotsComponent, BookedSlotsDetailComponent, BookedSlotsUpdateComponent, BookedSlotsDeleteDialogComponent],
  entryComponents: [BookedSlotsDeleteDialogComponent],
})
export class JhipsterSampleApplicationBookedSlotsModule {}
