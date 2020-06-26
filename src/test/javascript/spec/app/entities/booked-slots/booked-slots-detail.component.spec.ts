import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { BookedSlotsDetailComponent } from 'app/entities/booked-slots/booked-slots-detail.component';
import { BookedSlots } from 'app/shared/model/booked-slots.model';

describe('Component Tests', () => {
  describe('BookedSlots Management Detail Component', () => {
    let comp: BookedSlotsDetailComponent;
    let fixture: ComponentFixture<BookedSlotsDetailComponent>;
    const route = ({ data: of({ bookedSlots: new BookedSlots(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [BookedSlotsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(BookedSlotsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BookedSlotsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load bookedSlots on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.bookedSlots).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
