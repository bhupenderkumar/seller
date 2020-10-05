import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HaryaliTestModule } from '../../../../test.module';
import { Quote1DetailComponent } from 'app/entities/SELLER/quote-1/quote-1-detail.component';
import { Quote1 } from 'app/shared/model/SELLER/quote-1.model';

describe('Component Tests', () => {
  describe('Quote1 Management Detail Component', () => {
    let comp: Quote1DetailComponent;
    let fixture: ComponentFixture<Quote1DetailComponent>;
    const route = ({ data: of({ quote1: new Quote1(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HaryaliTestModule],
        declarations: [Quote1DetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(Quote1DetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(Quote1DetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load quote1 on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.quote1).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
