import { MediaMatcher } from '@angular/cdk/layout';
import { ChangeDetectorRef, Component, OnDestroy } from '@angular/core';
import { MenuItem } from '../shared/models/MenuItem';

@Component({
  selector: 'app-pages',
  templateUrl: './pages.component.html',
  styleUrls: ['./pages.component.scss']
})
export class PagesComponent implements OnDestroy {

  title = "Project Manager";

  menus: MenuItem[] = [
    {
      displayName: 'Home',
      iconName: 'home',
      route: 'home',
      selected: true,
    },
    {
      displayName: 'Person',
      iconName: 'person',
      route: 'person',
      selected: false,
    },
    {
      displayName: 'Project',
      iconName: 'cases',
      route: 'project',
      selected: false,
    }
  ];

  mobileQuery: MediaQueryList;

  private _mobileQueryListener: () => void;

  constructor(cdr: ChangeDetectorRef, media: MediaMatcher) {
    this.mobileQuery = media.matchMedia('(max-width: 600px)');
    this._mobileQueryListener = () => cdr.detectChanges();
    this.mobileQuery.addListener(this._mobileQueryListener);
  }

  ngOnDestroy(): void {
    this.mobileQuery.removeListener(this._mobileQueryListener);
  }

  menuSelected(menuSelected: MenuItem): void {
    this.menus.map(item => item.selected = (item.displayName === menuSelected.displayName) ? true : false );
  }

}
