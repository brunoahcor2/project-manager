import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, Observable } from 'rxjs';
import { ConfigService } from 'src/app/configs/config.service';
import { environment } from 'src/app/configs/environments/environment.hml';
import { PersonRequestModel } from 'src/app/shared/models/person-request.model';
import { PersonResponseModel } from 'src/app/shared/models/person-response.model';


@Injectable({
  providedIn: 'root'
})
export class PersonService {

  url = environment.api.url.concat(environment.api.person.domain);

  constructor(private http: HttpClient, private config: ConfigService) {}

  listAll(): Observable<PersonResponseModel[]>{
    return this.http.get<PersonResponseModel[]>(this.url)
    .pipe(
      catchError((error) => this.config.handleError(error, 'listAll'))
    );
  }

  edit(person: PersonRequestModel): Observable<PersonResponseModel>{
    return this.http.put<PersonResponseModel>(this.url.concat(`/${person.id}`), person)
    .pipe(
      catchError((error) => this.config.handleError(error, 'edit', person))
    );
  }

}
