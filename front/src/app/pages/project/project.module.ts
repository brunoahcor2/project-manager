import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { RouterModule, Routes } from '@angular/router';
import { MaterialModule } from 'src/app/core/material/material.module';

import { FormProjectComponent } from './components/form-project/form-project.component';
import { ProjectComponent } from './project.component';
import { CURRENCY_MASK_CONFIG, CurrencyMaskModule } from 'ng2-currency-mask';
import { CustomCurrencyMaskConfig } from 'src/app/configs/custom-currency-mask.config';

const routes: Routes = [{
    path: '',
    component: ProjectComponent,
}];

@NgModule({
  declarations: [ProjectComponent, FormProjectComponent],
  imports: [
    CommonModule,
    MaterialModule,
    ReactiveFormsModule,
    CurrencyMaskModule,
    RouterModule.forChild(routes),
  ],
  providers: [
    {
      provide: CURRENCY_MASK_CONFIG,
      useValue: CustomCurrencyMaskConfig
    }
],
})
export class ProjectModule { }
