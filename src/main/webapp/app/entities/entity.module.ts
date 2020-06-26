import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'category',
        loadChildren: () => import('./category/category.module').then(m => m.JhipsterSampleApplicationCategoryModule),
      },
      {
        path: 'service',
        loadChildren: () => import('./service/service.module').then(m => m.JhipsterSampleApplicationServiceModule),
      },
      {
        path: 'employee',
        loadChildren: () => import('./employee/employee.module').then(m => m.JhipsterSampleApplicationEmployeeModule),
      },
      {
        path: 'service-price',
        loadChildren: () => import('./service-price/service-price.module').then(m => m.JhipsterSampleApplicationServicePriceModule),
      },
      {
        path: 'customer',
        loadChildren: () => import('./customer/customer.module').then(m => m.JhipsterSampleApplicationCustomerModule),
      },
      {
        path: 'customer-payment',
        loadChildren: () =>
          import('./customer-payment/customer-payment.module').then(m => m.JhipsterSampleApplicationCustomerPaymentModule),
      },
      {
        path: 'booked-slots',
        loadChildren: () => import('./booked-slots/booked-slots.module').then(m => m.JhipsterSampleApplicationBookedSlotsModule),
      },
      {
        path: 'payment-status',
        loadChildren: () => import('./payment-status/payment-status.module').then(m => m.JhipsterSampleApplicationPaymentStatusModule),
      },
      {
        path: 'booke-slot-status',
        loadChildren: () =>
          import('./booke-slot-status/booke-slot-status.module').then(m => m.JhipsterSampleApplicationBookeSlotStatusModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class JhipsterSampleApplicationEntityModule {}
