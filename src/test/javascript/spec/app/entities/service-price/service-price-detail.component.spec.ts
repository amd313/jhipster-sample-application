import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { ServicePriceDetailComponent } from 'app/entities/service-price/service-price-detail.component';
import { ServicePrice } from 'app/shared/model/service-price.model';

describe('Component Tests', () => {
  describe('ServicePrice Management Detail Component', () => {
    let comp: ServicePriceDetailComponent;
    let fixture: ComponentFixture<ServicePriceDetailComponent>;
    const route = ({ data: of({ servicePrice: new ServicePrice(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [ServicePriceDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ServicePriceDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ServicePriceDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load servicePrice on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.servicePrice).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
