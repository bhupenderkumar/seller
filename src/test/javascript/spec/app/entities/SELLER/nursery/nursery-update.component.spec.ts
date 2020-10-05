import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HaryaliTestModule } from '../../../../test.module';
import { NurseryUpdateComponent } from 'app/entities/SELLER/nursery/nursery-update.component';
import { NurseryService } from 'app/entities/SELLER/nursery/nursery.service';
import { Nursery } from 'app/shared/model/SELLER/nursery.model';

describe('Component Tests', () => {
  describe('Nursery Management Update Component', () => {
    let comp: NurseryUpdateComponent;
    let fixture: ComponentFixture<NurseryUpdateComponent>;
    let service: NurseryService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HaryaliTestModule],
        declarations: [NurseryUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(NurseryUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(NurseryUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(NurseryService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Nursery(123);
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
        const entity = new Nursery();
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
