import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HaryaliTestModule } from '../../../../test.module';
import { FeedBackUpdateComponent } from 'app/entities/SELLER/feed-back/feed-back-update.component';
import { FeedBackService } from 'app/entities/SELLER/feed-back/feed-back.service';
import { FeedBack } from 'app/shared/model/SELLER/feed-back.model';

describe('Component Tests', () => {
  describe('FeedBack Management Update Component', () => {
    let comp: FeedBackUpdateComponent;
    let fixture: ComponentFixture<FeedBackUpdateComponent>;
    let service: FeedBackService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HaryaliTestModule],
        declarations: [FeedBackUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(FeedBackUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FeedBackUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FeedBackService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new FeedBack(123);
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
        const entity = new FeedBack();
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
