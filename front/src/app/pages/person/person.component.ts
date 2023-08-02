import { ChangeDetectorRef, Component, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatSort, Sort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { constantes } from 'src/app/configs/environments/constantes';
import { message } from 'src/app/configs/environments/message';
import { PersonService } from 'src/app/core/services/person.service';
import { MenuItem } from 'src/app/shared/models/menu-item';
import { PersonRequestModel } from 'src/app/shared/models/person-request.model';
import { PersonResponseModel } from 'src/app/shared/models/person-response.model';

@Component({
  selector: 'app-person',
  templateUrl: './person.component.html',
  styleUrls: ['./person.component.scss']
})
export class PersonComponent {

  title: string | undefined;

  displayedColumns: string[] = [
    'id',
    'name',
    'cpf',
    'employee',
    'dateBirth',
    'position',
    'active',
  ];

  dataSource!: MatTableDataSource<PersonResponseModel>;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(
    private service: PersonService,
    public dialog: MatDialog,
    private cdr: ChangeDetectorRef,
    private _snackBar: MatSnackBar
  ) {
    this.getTitlePage();
    this.listAll();
  }

  getTitlePage(): void {
    const menuSelectedString = localStorage.getItem(constantes.localStorage.menuSelected);
    const menuSelected: MenuItem = menuSelectedString ? JSON.parse(menuSelectedString) : "";
    this.title = menuSelected.displayName;
  }

  filtrar(event: Event): void {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }

  toggle(request: PersonResponseModel): void {
    request.active = !request.active;
    this.edit(request as PersonRequestModel);
  }

  edit(person: PersonRequestModel): void {
    this.service.edit(person)
    .subscribe({
      next: (response: PersonResponseModel) => {
        this.openSnackBar(message.success.person.updateSuccess,"Close");
      },
      error: (error) => {
        this.openSnackBar(message.errors.person.notFound,"Close");
      },
    });
  }

  listAll(): void {
    this.service.listAll()
    .subscribe({
      next: (response: PersonResponseModel[]) => {
        this.loadDataTable(response);
      },
      error: (error) => {
        this.openSnackBar(message.errors.person.notFound,"Close");
      },
    });
  }

  private loadDataTable(persons: PersonResponseModel[]): void {
    this.dataSource = new MatTableDataSource(persons);
    this.dataSource.paginator = this.paginator;
    this.loadSortTable();
  }

  private loadSortTable(): void {
    const sortState: Sort = {active: 'id', direction: 'asc'};
    this.sort.active = sortState.active;
    this.sort.direction = sortState.direction;
    this.dataSource.sort = this.sort;
  }

  openSnackBar(message: string, action: string) {
    this._snackBar.open(message, action, { duration: 3000 });
  }

}
