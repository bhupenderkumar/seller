export interface IFeedBack {
  id?: number;
  rating?: number;
  userName?: string;
  userComments?: string;
  productId?: number;
}

export class FeedBack implements IFeedBack {
  constructor(
    public id?: number,
    public rating?: number,
    public userName?: string,
    public userComments?: string,
    public productId?: number
  ) {}
}
