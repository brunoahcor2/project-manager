import { Component, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatSort, Sort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { PersonService } from 'src/app/core/services/person.service';
import { PersonRequestModel } from 'src/app/shared/models/PersonRequestModel';
import { PersonResponseModel } from 'src/app/shared/models/PersonResponseModel';

@Component({
  selector: 'app-person',
  templateUrl: './person.component.html',
  styleUrls: ['./person.component.scss']
})
export class PersonComponent {

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
    private _snackBar: MatSnackBar
  ) {
    this.listAll();
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
    this.save(request as PersonRequestModel);
  }

  save(person: PersonRequestModel): void {
    this.service.save(person).subscribe(
      (response: PersonResponseModel) => {
        this.reloadItemDataTable(response);
        this.loadComponentsTable();
        this.openSnackBar("Person updated successfully!","Close");
      });
  }

  listAll(): void {
    this.service.listAllPersons().subscribe(
      (response: PersonResponseModel[]) => {
        this.loadDataTable(response);
        this.loadComponentsTable();
      });
  }

  private loadDataTable(persons: PersonResponseModel[]): void {
    this.dataSource = new MatTableDataSource(persons);
  }

  private reloadItemDataTable(person: PersonResponseModel): void {
    this.dataSource.data.filter(p => p.id === person.id).map(p => person);
  }

  private loadComponentsTable(): void {
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
    this._snackBar.open(message, action);
  }

}
