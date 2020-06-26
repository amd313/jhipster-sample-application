import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from 'app/shared/shared.module';
import { ServicePriceComponent } from './service-price.component';
import { ServicePriceDetailComponent } from './service-price-detail.component';
import { ServicePriceUpdateComponent } from './service-price-update.component';
import { ServicePriceDeleteDialogComponent } from './service-price-delete-dialog.component';
import { servicePriceRoute } from './service-price.route';

@NgModule({
  imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(servicePriceRoute)],
  declarations: [ServicePriceComponent, ServicePriceDetailComponent, ServicePriceUpdateComponent, ServicePriceDeleteDialogComponent],
  entryComponents: [ServicePriceDeleteDialogComponent],
})
export class JhipsterSampleApplicationServicePriceModule {}
