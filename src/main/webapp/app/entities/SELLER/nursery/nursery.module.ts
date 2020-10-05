import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HaryaliSharedModule } from 'app/shared/shared.module';
import { NurseryComponent } from './nursery.component';
import { NurseryDetailComponent } from './nursery-detail.component';
import { NurseryUpdateComponent } from './nursery-update.component';
import { NurseryDeleteDialogComponent } from './nursery-delete-dialog.component';
import { nurseryRoute } from './nursery.route';

@NgModule({
  imports: [HaryaliSharedModule, RouterModule.forChild(nurseryRoute)],
  declarations: [NurseryComponent, NurseryDetailComponent, NurseryUpdateComponent, NurseryDeleteDialogComponent],
  entryComponents: [NurseryDeleteDialogComponent],
})
export class HaryaliNurseryModule {}
