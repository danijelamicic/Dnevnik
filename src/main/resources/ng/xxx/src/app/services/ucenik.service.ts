import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';

import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { MessageService } from '../services/message.service';
import { Ucenik } from '../entities/ucenik';
import { AuthService } from './auth-service.service';
import { Ocena } from '../entities/ocena';
import { Predmet } from '../entities/predmet';
import { OcenaPredmetDTO } from '../entities/ocena-predmet-dto';

@Injectable({
  providedIn: 'root'
})
export class UcenikService {

  private uceniciUrl = environment.apiBaseUrl + '/ucenici/';


  constructor(private httpClient: HttpClient,
    private authService: AuthService,
    private messageService: MessageService) { }

  pronadjiUcenikaPoId(id: number): Observable<Ucenik> {
    return this.httpClient
      .get<Ucenik>(this.uceniciUrl + id,  {headers: this.authService.getHeaders()})
      .pipe(
        tap(a => this.log(`Učitan ucenik sa id "${a.id}"`)),
        catchError(this.handleError<Ucenik>('pronadjiUcenikaPoId')));
  }
  pronadjiSveUcenike(): Observable<Ucenik[]> {
    return this.httpClient
      .get<Ucenik[]>(this.uceniciUrl,  {headers: this.authService.getHeaders()})
      .pipe(
        tap(_ => this.log(`Učitani ucenici`)),
        catchError(this.handleError<Ucenik[]>('pronadjiSveUcenike', [])));
  }
  dodajNovogUcenika(ucenik: Ucenik): Observable<Ucenik> {
    return this.httpClient
      .post<Ucenik>(this.uceniciUrl, ucenik, {headers: this.authService.getHeaders()})
      .pipe(
        tap(a => this.log(`Dodat ucenik sa id "${a.id}"`)),
        catchError(this.handleError<Ucenik>('dodajNovogUcenika')));
  }
  izmeniUcenika(ucenik: Ucenik): Observable<Ucenik> {
    return this.httpClient
      .put<Ucenik>(this.uceniciUrl + ucenik.id, ucenik,  {headers: this.authService.getHeaders()})
      .pipe(
        tap(a => this.log(`Izmenjen ucenik sa id "${a.id}"`)),
        catchError(this.handleError<Ucenik>('izmeniUcenika')));
  }
  obrisiUcenikaPoId (id: number): Observable<any> {
    return this.httpClient
    .delete<any>(this.uceniciUrl + id, {headers: this.authService.getHeaders()})
      .pipe(
        tap(a => this.log(`Obrisan ucenik sa id "${a.id}"`)),
        catchError(this.handleError<Ucenik>('obrisiUcenikaPoId')));

  }
  pronadjiOcenePoPredmetuPoIdUcenika(id: string): Observable<OcenaPredmetDTO[]> {
    return this.httpClient
      .get<OcenaPredmetDTO[]>(this.uceniciUrl + 'ocenapredmet/' + id,  {headers: this.authService.getHeaders()})
      .pipe(
        tap(a => this.log(`Učitane ocene ucenika sa id "$"`)),
        catchError(this.handleError<OcenaPredmetDTO[]>('pronadjiOcenePoPredmetuPoIdUcenika')));
  }


  searchUcenici(term: string): Observable<Ucenik[]> {
    if (!term.trim()) {
      return this.pronadjiSveUcenike();
    }

  return this.httpClient
  .get<Ucenik[]>(`${this.uceniciUrl}?naziv=${term}`,  {headers: this.authService.getHeaders()})
  .pipe(
    tap(_ => this.log(`Nadjeni ucenici sa nazivom "${term}"`)),
    catchError(this.handleError<Ucenik[]>('searchUcenici', []))
);
}

  private log(message: string) {
    this.messageService.add('UcenikService: ' + message);
  }

  private handleError<T> (operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(error);
      this.log(`${operation} failed: ${error.message}`);
      return of(result as T);
    };
  }
}
