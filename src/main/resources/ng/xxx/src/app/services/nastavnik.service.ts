import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';

import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { environment } from '../../environments/environment';

import { MessageService } from '../services/message.service';
import { Nastavnik } from '../entities/nastavnik';
import { AuthService } from './auth-service.service';

@Injectable({
  providedIn: 'root'
})
export class NastavnikService {

  private nastavniciUrl = environment.apiBaseUrl + '/nastavnici/';


  constructor(private httpClient: HttpClient,
    private authService: AuthService,
    private messageService: MessageService) { }

  pronadjiNastavnikaPoId(id: number): Observable<Nastavnik> {
    return this.httpClient
      .get<Nastavnik>(this.nastavniciUrl + id, {headers: this.authService.getHeaders()})
      .pipe(
        tap(a => this.log(`Učitan nastavnik sa id "${a.id}"`)),
        catchError(this.handleError<Nastavnik>('pronadjiNastavnikaPoId')));
  }
  pronadjiSveNastavnike(): Observable<Nastavnik[]> {
    return this.httpClient
      .get<Nastavnik[]>(this.nastavniciUrl, {headers: this.authService.getHeaders()})
      .pipe(
        tap(_ => this.log(`Učitani nastavnici`)),
        catchError(this.handleError<Nastavnik[]>('pronadjiSveNastavnike', [])));
  }
  dodajNovogNastavnika(nastavnik: Nastavnik): Observable<Nastavnik> {
    return this.httpClient
      .post<Nastavnik>(this.nastavniciUrl, nastavnik, {headers: this.authService.getHeaders()})
      .pipe(
        tap(a => this.log(`Dodat nastavnik sa id "${a.id}"`)),
        catchError(this.handleError<Nastavnik>('dodajNovogNastavnika')));
  }
  izmeniNastavnika(nastavnik: Nastavnik): Observable<Nastavnik> {
    return this.httpClient
      .put<Nastavnik>(this.nastavniciUrl + nastavnik.id, nastavnik, {headers: this.authService.getHeaders()})
      .pipe(
        tap(a => this.log(`Izmenjen nastavnik sa id "${a.id}"`)),
        catchError(this.handleError<Nastavnik>('izmeniNastavnika')));
  }
  obrisiNastavnikaPoId (id: number): Observable<any> {
    return this.httpClient
    .delete<any>(this.nastavniciUrl + id, {headers: this.authService.getHeaders()})
      .pipe(
        tap(a => this.log(`Obrisan administrator sa id "${a.id}"`)),
        catchError(this.handleError<Nastavnik>('obrisiNastavnikaPoId')));

  }

  searchNastavnici(term: string): Observable<Nastavnik[]> {
    if (!term.trim()) {
      return this.pronadjiSveNastavnike();
    }

  return this.httpClient
  .get<Nastavnik[]>(`${this.nastavniciUrl}?naziv=${term}`, {headers: this.authService.getHeaders()})
  .pipe(
    tap(_ => this.log(`Nadjeni nastavnici sa nazivom "${term}"`)),
    catchError(this.handleError<Nastavnik[]>('searchNastavnici', []))
  );
}

  private log(message: string) {
    this.messageService.add('NastavnikService: ' + message);
  }

  private handleError<T> (operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(error);
      this.log(`${operation} failed: ${error.message}`);
      return of(result as T);
    };
  }
}
