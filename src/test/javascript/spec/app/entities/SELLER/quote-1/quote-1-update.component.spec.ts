import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HaryaliTestModule } from '../../../../test.module';
import { Quote1UpdateComponent } from 'app/entities/SELLER/quote-1/quote-1-update.component';
import { Quote1Service } from 'app/entities/SELLER/quote-1/quote-1.service';
import { Quote1 } from 'app/shared/model/SELLER/quote-1.model';

describe('Component Tests', () => {
  describe('Quote1 Management Update Component', () => {
    let comp: Quote1UpdateComponent;
    let fixture: ComponentFixture<Quote1UpdateComponent>;
    let service: Quote1Service;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HaryaliTestModule],
        declarations: [Quote1UpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(Quote1UpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(Quote1UpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(Quote1Service);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Quote1(123);
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
        const entity = new Quote1();
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
