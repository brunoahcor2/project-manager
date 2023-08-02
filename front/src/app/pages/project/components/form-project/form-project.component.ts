import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Title } from '@angular/platform-browser';
import { message } from 'src/app/configs/environments/message';
import { PersonService } from 'src/app/core/services/person.service';
import { ProjectService } from 'src/app/core/services/project.service';
import { Risk } from 'src/app/shared/enums/risk.enum';
import { Status } from 'src/app/shared/enums/status.enum';
import { PersonResponseModel } from 'src/app/shared/models/person-response.model';
import { ProjectRequestModel } from 'src/app/shared/models/project-request.model';
import { ProjectResponseModel } from 'src/app/shared/models/project-response.model';

@Component({
  selector: 'app-form-project',
  templateUrl: './form-project.component.html',
  styleUrls: ['./form-project.component.scss']
})
export class FormProjectComponent implements OnInit {

  title = "Register Project";

  formGroup!: FormGroup;

  status: (string | Status)[] = [];
  risks: (string | Risk)[] = [];

  project = new ProjectRequestModel();
  listPersons: PersonResponseModel[] = [];

  isEdit: boolean = false;
  currentDate: Date = new Date();

  constructor( private projectService: ProjectService,
    private personService: PersonService,
    private fb: FormBuilder,
    public dialogRef: MatDialogRef<FormProjectComponent>,
    private _snackBar: MatSnackBar,
    @Inject(MAT_DIALOG_DATA) public data: ProjectResponseModel)
  {
    if(data){
      this.isEdit = true;
      this.project = data;
      this.title = "Edit Project";
    }
  }

  getStatus(): void {
    this.status = Object.values(Status).filter(value => typeof value === 'string');
  }

  getRisks(): void {
    this.risks = Object.values(Risk).filter(value => typeof value === 'string');
  }

  getPersons(): void {
    this.personService.listAll()
    .subscribe({
      next: (response: PersonResponseModel[]) => {
        this.listPersons = response
      },
      error: (error) => {
        this.openSnackBar(message.errors.person.notFound,"Close");
      },
    });
  }

  initFormGroup(): void {

    this.getStatus();
    this.getRisks();
    this.getPersons();

    this.formGroup = this.fb.group({
      id: [''],
      name: ['', [Validators.required, Validators.minLength(5)]],
      dateStart: ['', Validators.required],
      dateEstimatedEnd: ['', Validators.required],
      dateEnd: [''],
      description: ['', [Validators.required, Validators.minLength(10)]],
      status: ['', Validators.required],
      budget: ['', Validators.required],
      risk: [''],
      persons: [''],
    });

    this.formGroup.patchValue(this.project);
    this.formGroup.controls['persons'].setValue(this.setPersonsInTheForm());
  }

  ngOnInit() {
    this.initFormGroup();
  }

  cancel(): void {
    this.dialogRef.close();
  }

  prepareToSave(): ProjectRequestModel {
    const request = this.formGroup.getRawValue() as ProjectRequestModel;
    request.persons = this.setPersonsInTheObject();
    return request;
  }

  setPersonsInTheForm(): (number|undefined)[] | undefined {
    return this.project.persons?.map(p => p.id);
  }

  setPersonsInTheObject(): PersonResponseModel[] {
    const ids: number[] = this.formGroup.controls['persons'].value;
    return this.listPersons.filter(lp => ids.includes(lp.id));
  }

  save(): void {
    const request = this.prepareToSave();
    this.isEdit ? this.edit(request) : this.register(request);
  }

  register(request: ProjectRequestModel): void {
    this.projectService.save(request)
    .subscribe({
      next: (response: ProjectResponseModel) => {
        this.openSnackBar(message.success.project.registeredSuccess,"Close");
        this.dialogRef.close(response);
      },
      error: (error) => {
        this.openSnackBar(message.errors.project.notRegistered,"Close");
      },
    });
  }

  edit(request: ProjectRequestModel): void {
    this.projectService.edit(request)
    .subscribe({
      next: (response: ProjectResponseModel) => {
        this.openSnackBar(message.success.project.updateSuccess,"Close");
        this.dialogRef.close(response);
      },
      error: (error) => {
        this.openSnackBar(message.errors.project.notChanged,"Close");
      },
    });
  }

  openSnackBar(message: string, action: string) {
    this._snackBar.open(message, action, { duration: 3000 });
  }

}
