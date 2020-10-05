import { Moment } from 'moment';
import { IImages } from 'app/shared/model/SELLER/images.model';
import { IFeedBack } from 'app/shared/model/SELLER/feed-back.model';
import { Approval } from 'app/shared/model/enumerations/approval.model';
import { ProductType } from 'app/shared/model/enumerations/product-type.model';

export interface IProduct {
  id?: number;
  productName?: string;
  productDescription?: string;
  price?: number;
  approval?: Approval;
  showStatus?: boolean;
  productType?: ProductType;
  videoContentType?: string;
  video?: any;
  productDate?: Moment;
  updatedDate?: Moment;
  userName?: string;
  images?: IImages[];
  ratings?: IFeedBack[];
  nurseryId?: number;
}

export class Product implements IProduct {
  constructor(
    public id?: number,
    public productName?: string,
    public productDescription?: string,
    public price?: number,
    public approval?: Approval,
    public showStatus?: boolean,
    public productType?: ProductType,
    public videoContentType?: string,
    public video?: any,
    public productDate?: Moment,
    public updatedDate?: Moment,
    public userName?: string,
    public images?: IImages[],
    public ratings?: IFeedBack[],
    public nurseryId?: number
  ) {
    this.showStatus = this.showStatus || false;
  }
}
