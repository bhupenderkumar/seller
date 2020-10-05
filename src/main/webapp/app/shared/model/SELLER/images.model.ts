export interface IImages {
  id?: number;
  imageContentType?: string;
  image?: any;
  thumbImageContentType?: string;
  thumbImage?: any;
  alt?: string;
  title?: string;
  productId?: number;
}

export class Images implements IImages {
  constructor(
    public id?: number,
    public imageContentType?: string,
    public image?: any,
    public thumbImageContentType?: string,
    public thumbImage?: any,
    public alt?: string,
    public title?: string,
    public productId?: number
  ) {}
}
