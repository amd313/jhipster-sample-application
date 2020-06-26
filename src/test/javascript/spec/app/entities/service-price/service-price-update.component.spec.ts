import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { ServicePriceUpdateComponent } from 'app/entities/service-price/service-price-update.component';
import { ServicePriceService } from 'app/entities/service-price/service-price.service';
import { ServicePrice } from 'app/shared/model/service-price.model';

describe('Component Tests', () => {
  describe('ServicePrice Management Update Component', () => {
    let comp: ServicePriceUpdateComponent;
    let fixture: ComponentFixture<ServicePriceUpdateComponent>;
    let service: ServicePriceService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [ServicePriceUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ServicePriceUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ServicePriceUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ServicePriceService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ServicePrice(123);
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
        const entity = new ServicePrice();
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
