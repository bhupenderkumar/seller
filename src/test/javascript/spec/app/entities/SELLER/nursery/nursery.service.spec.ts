import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { NurseryService } from 'app/entities/SELLER/nursery/nursery.service';
import { INursery, Nursery } from 'app/shared/model/SELLER/nursery.model';
import { COUNTRY } from 'app/shared/model/enumerations/country.model';
import { PayMentMode } from 'app/shared/model/enumerations/pay-ment-mode.model';
import { PayMentDuration } from 'app/shared/model/enumerations/pay-ment-duration.model';

describe('Service Tests', () => {
  describe('Nursery Service', () => {
    let injector: TestBed;
    let service: NurseryService;
    let httpMock: HttpTestingController;
    let elemDefault: INursery;
    let expectedResult: INursery | INursery[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(NurseryService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Nursery(
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        COUNTRY.INDIA,
        0,
        0,
        'AAAAAAA',
        'AAAAAAA',
        PayMentMode.ONLINE,
        'AAAAAAA',
        PayMentDuration.TEN,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        currentDate,
        currentDate,
        'AAAAAAA'
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            createdDate: currentDate.format(DATE_FORMAT),
            updatedDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Nursery', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            createdDate: currentDate.format(DATE_FORMAT),
            updatedDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            createdDate: currentDate,
            updatedDate: currentDate,
          },
          returnedFromService
        );

        service.create(new Nursery()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Nursery', () => {
        const returnedFromService = Object.assign(
          {
            nurseryName: 'BBBBBB',
            houseNo: 'BBBBBB',
            salutation: 'BBBBBB',
            firstName: 'BBBBBB',
            lastName: 'BBBBBB',
            middleName: 'BBBBBB',
            streetNo: 'BBBBBB',
            districtNo: 'BBBBBB',
            city: 'BBBBBB',
            state: 'BBBBBB',
            country: 'BBBBBB',
            latitude: 1,
            longitude: 1,
            addharNo: 'BBBBBB',
            panCardNo: 'BBBBBB',
            payMentMode: 'BBBBBB',
            upiId: 'BBBBBB',
            payMentDuration: 'BBBBBB',
            accountNo: 'BBBBBB',
            bankIFSC: 'BBBBBB',
            bankName: 'BBBBBB',
            createdDate: currentDate.format(DATE_FORMAT),
            updatedDate: currentDate.format(DATE_FORMAT),
            userName: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            createdDate: currentDate,
            updatedDate: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Nursery', () => {
        const returnedFromService = Object.assign(
          {
            nurseryName: 'BBBBBB',
            houseNo: 'BBBBBB',
            salutation: 'BBBBBB',
            firstName: 'BBBBBB',
            lastName: 'BBBBBB',
            middleName: 'BBBBBB',
            streetNo: 'BBBBBB',
            districtNo: 'BBBBBB',
            city: 'BBBBBB',
            state: 'BBBBBB',
            country: 'BBBBBB',
            latitude: 1,
            longitude: 1,
            addharNo: 'BBBBBB',
            panCardNo: 'BBBBBB',
            payMentMode: 'BBBBBB',
            upiId: 'BBBBBB',
            payMentDuration: 'BBBBBB',
            accountNo: 'BBBBBB',
            bankIFSC: 'BBBBBB',
            bankName: 'BBBBBB',
            createdDate: currentDate.format(DATE_FORMAT),
            updatedDate: currentDate.format(DATE_FORMAT),
            userName: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            createdDate: currentDate,
            updatedDate: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Nursery', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
