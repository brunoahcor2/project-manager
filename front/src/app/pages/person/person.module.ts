import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MaterialModule } from 'src/app/core/material/material.module';
import { CpfPipe } from 'src/app/shared/pipes/cpf.pipe';
import { YesOrNoPipe } from 'src/app/shared/pipes/yes-or-no.pipe';

import { PersonComponent } from './person.component';

const routes: Routes = [{
  path: '',
  component: PersonComponent,
}];

@NgModule({
  declarations: [PersonComponent, CpfPipe, YesOrNoPipe],
  imports: [
    CommonModule,
    MaterialModule,
    RouterModule.forChild(routes),
  ]
})
export class PersonModule { }
