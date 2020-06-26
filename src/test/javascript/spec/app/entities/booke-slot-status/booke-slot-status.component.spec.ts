import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { BookeSlotStatusComponent } from 'app/entities/booke-slot-status/booke-slot-status.component';
import { BookeSlotStatusService } from 'app/entities/booke-slot-status/booke-slot-status.service';
import { BookeSlotStatus } from 'app/shared/model/booke-slot-status.model';

describe('Component Tests', () => {
  describe('BookeSlotStatus Management Component', () => {
    let comp: BookeSlotStatusComponent;
    let fixture: ComponentFixture<BookeSlotStatusComponent>;
    let service: BookeSlotStatusService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [BookeSlotStatusComponent],
      })
        .overrideTemplate(BookeSlotStatusComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BookeSlotStatusComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BookeSlotStatusService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new BookeSlotStatus(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.bookeSlotStatuses && comp.bookeSlotStatuses[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
