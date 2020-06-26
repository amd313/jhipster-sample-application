import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { BookeSlotStatusUpdateComponent } from 'app/entities/booke-slot-status/booke-slot-status-update.component';
import { BookeSlotStatusService } from 'app/entities/booke-slot-status/booke-slot-status.service';
import { BookeSlotStatus } from 'app/shared/model/booke-slot-status.model';

describe('Component Tests', () => {
  describe('BookeSlotStatus Management Update Component', () => {
    let comp: BookeSlotStatusUpdateComponent;
    let fixture: ComponentFixture<BookeSlotStatusUpdateComponent>;
    let service: BookeSlotStatusService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [BookeSlotStatusUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(BookeSlotStatusUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BookeSlotStatusUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BookeSlotStatusService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new BookeSlotStatus(123);
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
        const entity = new BookeSlotStatus();
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
