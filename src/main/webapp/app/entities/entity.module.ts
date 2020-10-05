import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'nursery',
        loadChildren: () => import('./SELLER/nursery/nursery.module').then(m => m.HaryaliNurseryModule),
      },
      {
        path: 'product',
        loadChildren: () => import('./SELLER/product/product.module').then(m => m.HaryaliProductModule),
      },
      {
        path: 'feed-back',
        loadChildren: () => import('./SELLER/feed-back/feed-back.module').then(m => m.HaryaliFeedBackModule),
      },
      {
        path: 'images',
        loadChildren: () => import('./SELLER/images/images.module').then(m => m.HaryaliImagesModule),
      },
      {
        path: 'quote-1',
        loadChildren: () => import('./SELLER/quote-1/quote-1.module').then(m => m.HaryaliQuote1Module),
      },
      {
        path: 'transaction',
        loadChildren: () => import('./SELLER/transaction/transaction.module').then(m => m.HaryaliTransactionModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class HaryaliEntityModule {}
