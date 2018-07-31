import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';

import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { Administrator } from '../entities/administrator';
import { MessageService } from '../services/message.service';
import { AuthService } from './auth-service.service';

@Injectable({
  providedIn: 'root'
})
export class AdministratorService {

  private administratoriUrl = environment.apiBaseUrl + '/admini/';


  constructor(private httpClient: HttpClient,
    private authService: AuthService,
    private messageService: MessageService) { }

  pronadjiAdminaPoId(id: number): Observable<Administrator> {
    return this.httpClient
      .get<Administrator>(this.administratoriUrl + id, {headers: this.authService.getHeaders()})
      .pipe(
        tap(a => this.log(`Učitan administrator sa id "${a.id}"`)),
        catchError(this.handleError<Administrator>('pronadjiAdminaPoId')));
  }
  pronadjiSveAdmine(): Observable<Administrator[]> {
    return this.httpClient
      .get<Administrator[]>(this.administratoriUrl, {headers: this.authService.getHeaders()})
      .pipe(
        tap(_ => this.log(`Učitani administratori`)),
        catchError(this.handleError<Administrator[]>('pronadjiSveAdmine', [])));
  }
  dodajNovogAdministratora(administrator: Administrator): Observable<Administrator> {
    return this.httpClient
      .post<Administrator>(this.administratoriUrl, administrator, {headers: this.authService.getHeaders()})
      .pipe(
        tap(a => this.log(`Dodat administrator sa id "${a.id}"`)),
        catchError(this.handleError<Administrator>('dodajNovogAdministratora')));
  }
  izmeniAdministratora(administrator: Administrator): Observable<Administrator> {
    return this.httpClient
      .put<Administrator>(this.administratoriUrl + administrator.id, administrator, {headers: this.authService.getHeaders()})
      .pipe(
        tap(a => this.log(`Izmenjen administrator sa id "${a.id}"`)),
        catchError(this.handleError<Administrator>('izmeniAdministratora')));
  }
  obrisiAdministratoraPoId (id: number): Observable<any> {
    return this.httpClient
    .delete<any>(this.administratoriUrl + id, {headers: this.authService.getHeaders()})
      .pipe(
        tap(a => this.log(`Obrisan administrator sa id "${a.id}"`)),
        catchError(this.handleError<Administrator>('obrisiAdministratoraPoId')));

  }


  searchAdministratori(term: string): Observable<Administrator[]> {
    if (!term.trim()) {
      return this.pronadjiSveAdmine();
    }

  return this.httpClient
  .get<Administrator[]>(`${this.administratoriUrl}?naziv=${term}`, {headers: this.authService.getHeaders()})
  .pipe(
    tap(_ => this.log(`Nadjeni administratori sa nazivom "${term}"`)),
    catchError(this.handleError<Administrator[]>('searchAdministratori', []))
);
}

  private log(message: string) {
    this.messageService.add('AdministratorService: ' + message);
  }

  private handleError<T> (operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(error);
      this.log(`${operation} failed: ${error.message}`);
      return of(result as T);
    };
  }
}
