import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';

import { MaterialModule } from '../core/material/material.module';
import { PagesRoutingModule } from './pages-routing.module';
import { PagesComponent } from './pages.component';
import { DialogConfirmComponent } from '../shared/components/dialog-confirm/dialog-confirm.component';


@NgModule({
  declarations: [
    PagesComponent,
    DialogConfirmComponent
  ],
  imports: [
    CommonModule,
    PagesRoutingModule,
    MaterialModule
  ],
  exports: [DialogConfirmComponent]
})
export class PagesModule { }
