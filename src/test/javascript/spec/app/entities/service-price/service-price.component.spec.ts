import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { ServicePriceComponent } from 'app/entities/service-price/service-price.component';
import { ServicePriceService } from 'app/entities/service-price/service-price.service';
import { ServicePrice } from 'app/shared/model/service-price.model';

describe('Component Tests', () => {
  describe('ServicePrice Management Component', () => {
    let comp: ServicePriceComponent;
    let fixture: ComponentFixture<ServicePriceComponent>;
    let service: ServicePriceService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [ServicePriceComponent],
      })
        .overrideTemplate(ServicePriceComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ServicePriceComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ServicePriceService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ServicePrice(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.servicePrices && comp.servicePrices[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
