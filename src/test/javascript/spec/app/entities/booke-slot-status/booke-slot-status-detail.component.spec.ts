import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { BookeSlotStatusDetailComponent } from 'app/entities/booke-slot-status/booke-slot-status-detail.component';
import { BookeSlotStatus } from 'app/shared/model/booke-slot-status.model';

describe('Component Tests', () => {
  describe('BookeSlotStatus Management Detail Component', () => {
    let comp: BookeSlotStatusDetailComponent;
    let fixture: ComponentFixture<BookeSlotStatusDetailComponent>;
    const route = ({ data: of({ bookeSlotStatus: new BookeSlotStatus(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [BookeSlotStatusDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(BookeSlotStatusDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BookeSlotStatusDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load bookeSlotStatus on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.bookeSlotStatus).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
