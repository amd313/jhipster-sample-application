import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from 'app/shared/shared.module';
import { BookeSlotStatusComponent } from './booke-slot-status.component';
import { BookeSlotStatusDetailComponent } from './booke-slot-status-detail.component';
import { BookeSlotStatusUpdateComponent } from './booke-slot-status-update.component';
import { BookeSlotStatusDeleteDialogComponent } from './booke-slot-status-delete-dialog.component';
import { bookeSlotStatusRoute } from './booke-slot-status.route';

@NgModule({
  imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(bookeSlotStatusRoute)],
  declarations: [
    BookeSlotStatusComponent,
    BookeSlotStatusDetailComponent,
    BookeSlotStatusUpdateComponent,
    BookeSlotStatusDeleteDialogComponent,
  ],
  entryComponents: [BookeSlotStatusDeleteDialogComponent],
})
export class JhipsterSampleApplicationBookeSlotStatusModule {}
