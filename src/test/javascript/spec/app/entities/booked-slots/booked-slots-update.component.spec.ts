import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { BookedSlotsUpdateComponent } from 'app/entities/booked-slots/booked-slots-update.component';
import { BookedSlotsService } from 'app/entities/booked-slots/booked-slots.service';
import { BookedSlots } from 'app/shared/model/booked-slots.model';

describe('Component Tests', () => {
  describe('BookedSlots Management Update Component', () => {
    let comp: BookedSlotsUpdateComponent;
    let fixture: ComponentFixture<BookedSlotsUpdateComponent>;
    let service: BookedSlotsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [BookedSlotsUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(BookedSlotsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BookedSlotsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BookedSlotsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new BookedSlots(123);
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
        const entity = new BookedSlots();
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
