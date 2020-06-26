import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from 'app/shared/shared.module';
import { CustomerPaymentComponent } from './customer-payment.component';
import { CustomerPaymentDetailComponent } from './customer-payment-detail.component';
import { CustomerPaymentUpdateComponent } from './customer-payment-update.component';
import { CustomerPaymentDeleteDialogComponent } from './customer-payment-delete-dialog.component';
import { customerPaymentRoute } from './customer-payment.route';

@NgModule({
  imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(customerPaymentRoute)],
  declarations: [
    CustomerPaymentComponent,
    CustomerPaymentDetailComponent,
    CustomerPaymentUpdateComponent,
    CustomerPaymentDeleteDialogComponent,
  ],
  entryComponents: [CustomerPaymentDeleteDialogComponent],
})
export class JhipsterSampleApplicationCustomerPaymentModule {}
