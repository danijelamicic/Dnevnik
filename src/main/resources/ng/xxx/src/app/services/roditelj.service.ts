import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';

import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { environment } from '../../environments/environment';

import { MessageService } from '../services/message.service';
import { Roditelj } from '../entities/roditelj';
import { AuthService } from './auth-service.service';
import { Ucenik } from '../entities/ucenik';

@Injectable({
  providedIn: 'root'
})
export class RoditeljService {

  private roditeljiUrl = environment.apiBaseUrl + '/roditelji/';

  constructor(private httpClient: HttpClient,
    private authService: AuthService,
    private messageService: MessageService) { }

  pronadjiRoditeljaPoId(id: number): Observable<Roditelj> {
    return this.httpClient
      .get<Roditelj>(this.roditeljiUrl + id,  {headers: this.authService.getHeaders()})
      .pipe(
        tap(a => this.log(`Učitan roditelj sa id "${a.id}"`)),
        catchError(this.handleError<Roditelj>('pronadjiRoditeljaPoId')));
  }
  pronadjiSveRoditelje(): Observable<Roditelj[]> {
    return this.httpClient
      .get<Roditelj[]>(this.roditeljiUrl,  {headers: this.authService.getHeaders()})
      .pipe(
        tap(_ => this.log(`Učitani roditelji`)),
        catchError(this.handleError<Roditelj[]>('pronadjiSveRoditelje', [])));
  }
  dodajNovogRoditelja(roditelj: Roditelj): Observable<Roditelj> {
    return this.httpClient
      .post<Roditelj>(this.roditeljiUrl, roditelj,  {headers: this.authService.getHeaders()})
      .pipe(
        tap(a => this.log(`Dodat roditelj sa id "${a.id}"`)),
        catchError(this.handleError<Roditelj>('dodajNovogRoditelja')));
  }
  izmeniRoditelja(roditelj: Roditelj): Observable<Roditelj> {
    return this.httpClient
      .put<Roditelj>(this.roditeljiUrl + roditelj.id, roditelj,  {headers: this.authService.getHeaders()})
      .pipe(
        tap(a => this.log(`Izmenjen roditelj sa id "${a.id}"`)),
        catchError(this.handleError<Roditelj>('izmeniRoditelja')));
  }
  obrisiRoditeljaPoId (id: number): Observable<any> {
    return this.httpClient
    .delete<any>(this.roditeljiUrl + id, {headers: this.authService.getHeaders()})
      .pipe(
        tap(a => this.log(`Obrisan roditelj sa id "${a.id}"`)),
        catchError(this.handleError<Roditelj>('obrisiRoditeljaPoId')));

  }
  pronadjiDecuRoditelja(id: string): Observable<Ucenik[]> {
    return this.httpClient
    .get<Ucenik[]>(this.roditeljiUrl + 'ucenici/' + id, {headers: this.authService.getHeaders()})
    .pipe(
      tap(a => this.log(`Deca roditelja sa id $`)),
      catchError(this.handleError<Ucenik[]>('pronadjiDecuRoditelja')));

  }



  searchRoditelji(term: string): Observable<Roditelj[]> {
    if (!term.trim()) {
      return this.pronadjiSveRoditelje();
    }

  return this.httpClient
  .get<Roditelj[]>(`${this.roditeljiUrl}?naziv=${term}`,  {headers: this.authService.getHeaders()})
  .pipe(
    tap(_ => this.log(`Nadjeni roditelji sa nazivom "${term}"`)),
    catchError(this.handleError<Roditelj[]>('searchRoditelji', []))
);
}

  private log(message: string) {
    this.messageService.add('RoditeljService: ' + message);
  }

  private handleError<T> (operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(error);
      this.log(`${operation} failed: ${error.message}`);
      return of(result as T);
    };
  }
}
