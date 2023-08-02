import { Component } from '@angular/core';
import { constantes } from 'src/app/configs/environments/constantes';
import { MenuItem } from 'src/app/shared/models/menu-item';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent {

  title = "Home";

  constructor() { }

}
