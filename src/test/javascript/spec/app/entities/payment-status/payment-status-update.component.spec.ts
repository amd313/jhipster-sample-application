import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { PaymentStatusUpdateComponent } from 'app/entities/payment-status/payment-status-update.component';
import { PaymentStatusService } from 'app/entities/payment-status/payment-status.service';
import { PaymentStatus } from 'app/shared/model/payment-status.model';

describe('Component Tests', () => {
  describe('PaymentStatus Management Update Component', () => {
    let comp: PaymentStatusUpdateComponent;
    let fixture: ComponentFixture<PaymentStatusUpdateComponent>;
    let service: PaymentStatusService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [PaymentStatusUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(PaymentStatusUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PaymentStatusUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PaymentStatusService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PaymentStatus(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new PaymentStatus();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
