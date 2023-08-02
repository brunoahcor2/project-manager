import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, Observable } from 'rxjs';
import { ConfigService } from 'src/app/configs/config.service';
import { environment } from 'src/app/configs/environments/environment.hml';
import { ProjectRequestModel } from 'src/app/shared/models/project-request.model';
import { ProjectResponseModel } from 'src/app/shared/models/project-response.model';

@Injectable({
  providedIn: 'root'
})
export class ProjectService {

  url = environment.api.url.concat(environment.api.project.domain);

  constructor(private http: HttpClient, private config: ConfigService) {}

  listAll(): Observable<ProjectResponseModel[]>{
    return this.http.get<ProjectResponseModel[]>(this.url)
    .pipe(
      catchError((error) => this.config.handleError(error, 'listAll'))
    );
  }

  save(project: ProjectRequestModel): Observable<ProjectResponseModel>{
    return this.http.post<ProjectResponseModel>(this.url.concat(environment.api.project.endpointRegistry), project)
    .pipe(
      catchError((error) => this.config.handleError(error, 'save', project))
    );
  }

  edit(project: ProjectRequestModel): Observable<ProjectResponseModel>{
    return this.http.put<ProjectResponseModel>(this.url.concat(`/${project.id}`), project)
    .pipe(
      catchError((error) => this.config.handleError(error, 'edit', project))
    );
  }

  delete(projectId: number): Observable<ProjectResponseModel>{
    return this.http.delete<ProjectResponseModel>(this.url.concat(`/${projectId}`))
    .pipe(
      catchError((error) => this.config.handleError(error, 'delete'))
    );
  }

}
