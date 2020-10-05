import { Moment } from 'moment';
import { PayMentMode } from 'app/shared/model/enumerations/pay-ment-mode.model';

export interface ITransaction {
  id?: number;
  userName?: string;
  transactionAmount?: number;
  transactionDate?: Moment;
  transactionMethod?: PayMentMode;
  processedBy?: string;
  nurseryId?: number;
}

export class Transaction implements ITransaction {
  constructor(
    public id?: number,
    public userName?: string,
    public transactionAmount?: number,
    public transactionDate?: Moment,
    public transactionMethod?: PayMentMode,
    public processedBy?: string,
    public nurseryId?: number
  ) {}
}
