import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { environment } from '../../environments/environment';
import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { AuthService } from './auth-service.service';
import { MessageService } from './message.service';
import { Predmet } from '../entities/predmet';
import { catchError, map, tap } from 'rxjs/operators';


@Injectable({
  providedIn: 'root'
})
export class PredmetService {

  private predmetiUrl = environment.apiBaseUrl + '/predmeti/';

  constructor(private httpClient: HttpClient,
    private authService: AuthService,
    private messageService: MessageService) { }

  pronadjiPredmetPoId(id: number): Observable<Predmet> {
    return this.httpClient
      .get<Predmet>(this.predmetiUrl + id,  {headers: this.authService.getHeaders()})
      .pipe(
        tap(a => this.log(`Učitan predmet sa id "${a.idPredmet}"`)),
        catchError(this.handleError<Predmet>('pronadjiPredmetePoId')));
  }
  pronadjiSvePredmete(): Observable<Predmet[]> {
    return this.httpClient
      .get<Predmet[]>(this.predmetiUrl,  {headers: this.authService.getHeaders()})
      .pipe(
        tap(_ => this.log(`Učitani predmeti`)),
        catchError(this.handleError<Predmet[]>('pronadjiSvePredmete', [])));
  }
  dodajNoviPredmet(predmet: Predmet): Observable<Predmet> {
    return this.httpClient
      .post<Predmet>(this.predmetiUrl, predmet,  {headers: this.authService.getHeaders()})
      .pipe(
        tap(a => this.log(`Dodat predmet sa id "${a.idPredmet}"`)),
        catchError(this.handleError<Predmet>('dodajNoviPredmet')));
  }
  izmeniPredmet(predmet: Predmet): Observable<Predmet> {
    return this.httpClient
      .put<Predmet>(this.predmetiUrl + predmet.idPredmet, predmet,  {headers: this.authService.getHeaders()})
      .pipe(
        tap(a => this.log(`Izmenjen predmet sa id "${a.idPredmet}"`)),
        catchError(this.handleError<Predmet>('izmeniPredmet')));
  }
  searchPredmeti(term: string): Observable<Predmet[]> {
    if (!term.trim()) {
      return this.pronadjiSvePredmete();
    }

  return this.httpClient
  .get<Predmet[]>(`${this.predmetiUrl}?naziv=${term}`,  {headers: this.authService.getHeaders()})
  .pipe(
    tap(_ => this.log(`Nadjeni predmeti sa nazivom "${term}"`)),
    catchError(this.handleError<Predmet[]>('searchPredmeti', []))
);
}

  private log(message: string) {
    this.messageService.add('PredmetService: ' + message);
  }

  private handleError<T> (operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(error);
      this.log(`${operation} failed: ${error.message}`);
      return of(result as T);
    };
  }
}
