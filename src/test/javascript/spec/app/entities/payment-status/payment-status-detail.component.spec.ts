import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { PaymentStatusDetailComponent } from 'app/entities/payment-status/payment-status-detail.component';
import { PaymentStatus } from 'app/shared/model/payment-status.model';

describe('Component Tests', () => {
  describe('PaymentStatus Management Detail Component', () => {
    let comp: PaymentStatusDetailComponent;
    let fixture: ComponentFixture<PaymentStatusDetailComponent>;
    const route = ({ data: of({ paymentStatus: new PaymentStatus(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [PaymentStatusDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(PaymentStatusDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PaymentStatusDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load paymentStatus on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.paymentStatus).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
