import { Component, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatSort, Sort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { message } from 'src/app/configs/environments/message';
import { ProjectService } from 'src/app/core/services/project.service';
import { DialogConfirmComponent } from 'src/app/shared/components/dialog-confirm/dialog-confirm.component';
import { PersonResponseModel } from 'src/app/shared/models/person-response.model';
import { ProjectResponseModel } from 'src/app/shared/models/project-response.model';

import { FormProjectComponent } from './components/form-project/form-project.component';


@Component({
  selector: 'app-project',
  templateUrl: './project.component.html',
  styleUrls: ['./project.component.scss']
})
export class ProjectComponent {

  displayedColumns: string[] = [
    'id',
    'name',
    'dateStart',
    'dateEstimatedEnd',
    'dateEnd',
    'description',
    'status',
    'budget',
    'risk',
    'persons',
    'actions',
  ];

  dataSource!: MatTableDataSource<ProjectResponseModel>;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(
    private service: ProjectService,
    public dialog: MatDialog,
    private _snackBar: MatSnackBar
  ) {
    this.listAll();
  }

  listAll(): void {
    this.service.listAll()
    .subscribe({
      next: (response: ProjectResponseModel[]) => {
        this.loadDataTable(response);
      },
      error: (error) => {
        this.openSnackBar(message.errors.project.notFound,"Close");
      },
    });
  }

  delete(projectId: number): void {
    this.service.delete(projectId)
    .subscribe({
      next: () => {
        this.dataSource.data = this.dataSource.data.filter(item => item.id !== projectId );
        this.dataSource._updateChangeSubscription();
        this.openSnackBar(message.success.project.deletedSuccess,"Close");
      },
      error: (error) => {
        this.openSnackBar(message.errors.project.notFound,"Close");
      },
    });
  }

  private loadDataTable(persons: ProjectResponseModel[]): void {
    this.dataSource = new MatTableDataSource(persons);
    this.dataSource.paginator = this.paginator;
    this.loadSortTable();
  }

  private loadSortTable(): void {
    const sortState: Sort = {active: 'id', direction: 'desc'};
    this.sort.active = sortState.active;
    this.sort.direction = sortState.direction;
    this.dataSource.sort = this.sort;
  }

  getDatesTooltip(project: ProjectResponseModel): string {
    return "Date Start: " + project.dateStart
    + ", Date Estimated End: " + project.dateEstimatedEnd
    + ", Date End: " + project.dateEnd;
  }

  getPersonsTooltip(persons: PersonResponseModel[]): string {
    return persons?.map(p => p.name).toString();
  }

  openDialog(project?: ProjectResponseModel): void {
    const dialogRef = this.dialog.open(FormProjectComponent, { data: project });

    dialogRef.afterClosed().subscribe(result => {
      if(result){
        if(project){
          this.dataSource.data = this.dataSource.data.map(prj => (prj.id === project.id) ? result : prj);
        } else {
          // this.dataSource.data.push(result as ProjectResponseModel); // push: add item to top of list
          this.dataSource.data.unshift(result as ProjectResponseModel); // unshift: add item to end of list
        }
        this.dataSource._updateChangeSubscription();
      }
    });
  }

  openDialogConfirm(project: ProjectResponseModel): void {
    const confirm = this.dialog.open(DialogConfirmComponent, {
      maxWidth: '500px',
      data: {
        title: 'Confirmation dialog',
        message: `Would you like to delete the '${project.name}' project?`},
    });

    confirm.afterClosed().subscribe(confirm => {
      console.log(confirm);
      if(confirm){
        this.delete(project.id);
      }
    });
  }

  openSnackBar(message: string, action: string) {
    this._snackBar.open(message, action, { duration: 3000 });
  }

}
