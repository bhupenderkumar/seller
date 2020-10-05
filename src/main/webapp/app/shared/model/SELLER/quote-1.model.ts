import { Moment } from 'moment';

export interface IQuote1 {
  id?: number;
  symbol?: string;
  price?: number;
  lastTrade?: Moment;
}

export class Quote1 implements IQuote1 {
  constructor(public id?: number, public symbol?: string, public price?: number, public lastTrade?: Moment) {}
}
