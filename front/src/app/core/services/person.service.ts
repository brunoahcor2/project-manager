import { PersonRequestModel } from 'src/app/shared/models/PersonRequestModel';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, of, tap } from 'rxjs';
import { PersonResponseModel } from 'src/app/shared/models/PersonResponseModel';

@Injectable({
  providedIn: 'root'
})
export class PersonService {

  url = 'http://localhost:8081/project-manager/api/v1/persons';

  constructor(private http: HttpClient) {}

  listAllPersons(): Observable<PersonResponseModel[]>{
    return this.http.get<PersonResponseModel[]>(this.url)
    .pipe(
      catchError(this.handleError<PersonResponseModel[]>('listAllPersons', []))
    );
  }

  save(person: PersonRequestModel): Observable<PersonResponseModel>{
    return this.http.post<PersonResponseModel>(this.url.concat('/registry'), person)
    .pipe(
      catchError(this.handleError<PersonResponseModel>('save'))
    );
  }

  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {

      // TODO: send the error to remote logging infrastructure
      console.error(error); // log to console instead

      // TODO: better job of transforming error for user consumption
      // this.log(`${operation} failed: ${error.message}`);

      // Let the app keep running by returning an empty result.
      return of(result as T);
    };
  }

}
