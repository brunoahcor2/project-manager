import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PagesComponent } from './pages.component';

const routes: Routes = [
//   { path: '', component: PagesComponent }
// ];
  { path: '', pathMatch: 'full', redirectTo: 'home' },
  {
    path: '',
    component: PagesComponent,
    children: [
      {
        path: 'home',
        loadChildren: () => import('./home/home.module').then((m) => m.HomeModule)
      },
      {
        path: 'person',
        loadChildren: () => import('./person/person.module').then((m) => m.PersonModule)
      },
      {
        path: 'project',
        loadChildren: () => import('./project/project.module').then((m) => m.ProjectModule)
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PagesRoutingModule {}
