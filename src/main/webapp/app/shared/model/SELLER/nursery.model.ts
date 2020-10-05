import { Moment } from 'moment';
import { IProduct } from 'app/shared/model/SELLER/product.model';
import { ITransaction } from 'app/shared/model/SELLER/transaction.model';
import { COUNTRY } from 'app/shared/model/enumerations/country.model';
import { PayMentMode } from 'app/shared/model/enumerations/pay-ment-mode.model';
import { PayMentDuration } from 'app/shared/model/enumerations/pay-ment-duration.model';

export interface INursery {
  id?: number;
  nurseryName?: string;
  houseNo?: string;
  salutation?: string;
  firstName?: string;
  lastName?: string;
  middleName?: string;
  streetNo?: string;
  districtNo?: string;
  city?: string;
  state?: string;
  country?: COUNTRY;
  latitude?: number;
  longitude?: number;
  addharNo?: string;
  panCardNo?: string;
  payMentMode?: PayMentMode;
  upiId?: string;
  payMentDuration?: PayMentDuration;
  accountNo?: string;
  bankIFSC?: string;
  bankName?: string;
  createdDate?: Moment;
  updatedDate?: Moment;
  userName?: string;
  products?: IProduct[];
  transactions?: ITransaction[];
}

export class Nursery implements INursery {
  constructor(
    public id?: number,
    public nurseryName?: string,
    public houseNo?: string,
    public salutation?: string,
    public firstName?: string,
    public lastName?: string,
    public middleName?: string,
    public streetNo?: string,
    public districtNo?: string,
    public city?: string,
    public state?: string,
    public country?: COUNTRY,
    public latitude?: number,
    public longitude?: number,
    public addharNo?: string,
    public panCardNo?: string,
    public payMentMode?: PayMentMode,
    public upiId?: string,
    public payMentDuration?: PayMentDuration,
    public accountNo?: string,
    public bankIFSC?: string,
    public bankName?: string,
    public createdDate?: Moment,
    public updatedDate?: Moment,
    public userName?: string,
    public products?: IProduct[],
    public transactions?: ITransaction[]
  ) {}
}
