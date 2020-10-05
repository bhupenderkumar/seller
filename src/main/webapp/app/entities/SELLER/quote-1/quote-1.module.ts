import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HaryaliSharedModule } from 'app/shared/shared.module';
import { Quote1Component } from './quote-1.component';
import { Quote1DetailComponent } from './quote-1-detail.component';
import { Quote1UpdateComponent } from './quote-1-update.component';
import { Quote1DeleteDialogComponent } from './quote-1-delete-dialog.component';
import { quote1Route } from './quote-1.route';

@NgModule({
  imports: [HaryaliSharedModule, RouterModule.forChild(quote1Route)],
  declarations: [Quote1Component, Quote1DetailComponent, Quote1UpdateComponent, Quote1DeleteDialogComponent],
  entryComponents: [Quote1DeleteDialogComponent],
})
export class HaryaliQuote1Module {}
