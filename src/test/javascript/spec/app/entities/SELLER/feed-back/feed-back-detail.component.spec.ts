import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HaryaliTestModule } from '../../../../test.module';
import { FeedBackDetailComponent } from 'app/entities/SELLER/feed-back/feed-back-detail.component';
import { FeedBack } from 'app/shared/model/SELLER/feed-back.model';

describe('Component Tests', () => {
  describe('FeedBack Management Detail Component', () => {
    let comp: FeedBackDetailComponent;
    let fixture: ComponentFixture<FeedBackDetailComponent>;
    const route = ({ data: of({ feedBack: new FeedBack(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HaryaliTestModule],
        declarations: [FeedBackDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(FeedBackDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FeedBackDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load feedBack on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.feedBack).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
