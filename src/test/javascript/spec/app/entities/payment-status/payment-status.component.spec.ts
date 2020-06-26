import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { PaymentStatusComponent } from 'app/entities/payment-status/payment-status.component';
import { PaymentStatusService } from 'app/entities/payment-status/payment-status.service';
import { PaymentStatus } from 'app/shared/model/payment-status.model';

describe('Component Tests', () => {
  describe('PaymentStatus Management Component', () => {
    let comp: PaymentStatusComponent;
    let fixture: ComponentFixture<PaymentStatusComponent>;
    let service: PaymentStatusService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [PaymentStatusComponent],
      })
        .overrideTemplate(PaymentStatusComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PaymentStatusComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PaymentStatusService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new PaymentStatus(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.paymentStatuses && comp.paymentStatuses[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
