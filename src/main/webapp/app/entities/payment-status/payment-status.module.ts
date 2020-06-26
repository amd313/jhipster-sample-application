import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from 'app/shared/shared.module';
import { PaymentStatusComponent } from './payment-status.component';
import { PaymentStatusDetailComponent } from './payment-status-detail.component';
import { PaymentStatusUpdateComponent } from './payment-status-update.component';
import { PaymentStatusDeleteDialogComponent } from './payment-status-delete-dialog.component';
import { paymentStatusRoute } from './payment-status.route';

@NgModule({
  imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(paymentStatusRoute)],
  declarations: [PaymentStatusComponent, PaymentStatusDetailComponent, PaymentStatusUpdateComponent, PaymentStatusDeleteDialogComponent],
  entryComponents: [PaymentStatusDeleteDialogComponent],
})
export class JhipsterSampleApplicationPaymentStatusModule {}
