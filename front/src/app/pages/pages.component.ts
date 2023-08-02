import { MediaMatcher } from '@angular/cdk/layout';
import { ChangeDetectorRef, Component, OnDestroy, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { constantes } from '../configs/environments/constantes';
import { MenuItem } from '../shared/models/menu-item';

@Component({
  selector: 'app-pages',
  templateUrl: './pages.component.html',
  styleUrls: ['./pages.component.scss']
})
export class PagesComponent implements OnInit, OnDestroy {

  titleProject = "Project Manager";

  menus: MenuItem[] = [];
  menuSelected: MenuItem | undefined;

  mobileQuery: MediaQueryList;

  private _mobileQueryListener: () => void;

  constructor(cdr: ChangeDetectorRef,
              media: MediaMatcher,
              private router: Router)
  {
    this.mobileQuery = media.matchMedia('(max-width: 600px)');
    this._mobileQueryListener = () => cdr.detectChanges();
    this.mobileQuery.addListener(this._mobileQueryListener);
  }

  ngOnInit(): void {
    this.initMenu();
  }

  ngOnDestroy(): void {
    this.mobileQuery.removeListener(this._mobileQueryListener);
  }

  getMenus(): void {
    this.menus = [
      {
        displayName: 'Home',
        iconName: 'home',
        route: 'home',
      },
      {
        displayName: 'Person',
        iconName: 'person',
        route: 'person',
      },
      {
        displayName: 'Project',
        iconName: 'cases',
        route: 'project',
      }
    ];
  }

  selectMenu(menuSelected: MenuItem): void {
    this.menuSelected = menuSelected;
    localStorage.setItem(constantes.localStorage.menuSelected,JSON.stringify(menuSelected));
  }

  initMenu(): void {
    this.loadMenu(localStorage.getItem(constantes.localStorage.menus));
    this.loadMenuSelected(localStorage.getItem(constantes.localStorage.menuSelected));
    this.checkCurrentUrl();
  }

  private loadMenu(menuString: string | null): void {
    if(menuString){
      this.menus = JSON.parse(menuString);
    } else {
      this.getMenus();
      localStorage.setItem(constantes.localStorage.menus,JSON.stringify(this.menus));
    }
  }

  private loadMenuSelected(menuSelectedString: string | null): void {
    if(menuSelectedString){
      this.menuSelected = JSON.parse(menuSelectedString);
    } else {
      this.menuSelected = this.menus[0];
      localStorage.setItem(constantes.localStorage.menuSelected,JSON.stringify(this.menuSelected));
    }
  }

  private checkCurrentUrl(): void {
    if(!this.router.url.includes( this.menuSelected?.route! ))
      this.router.navigate(['/pages/',this.menuSelected?.route ]);
  }

}
