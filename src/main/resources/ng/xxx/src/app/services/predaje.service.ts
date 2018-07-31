import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { environment } from '../../environments/environment';
import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { AuthService } from './auth-service.service';
import { MessageService } from './message.service';
import { catchError, map, tap } from 'rxjs/operators';
import { Predaje } from '../entities/predaje';

@Injectable({
  providedIn: 'root'
})
export class PredajeService {

  private predajeUrl = environment.apiBaseUrl + '/predaje/';

  constructor(private httpClient: HttpClient,
    private authService: AuthService,
    private messageService: MessageService) { }

  pronadjiPredajePoId(id: number): Observable<Predaje> {
    return this.httpClient
      .get<Predaje>(this.predajeUrl + id,  {headers: this.authService.getHeaders()})
      .pipe(
        tap(a => this.log(`Učitan predaje sa id "${a.idPredaje}"`)),
        catchError(this.handleError<Predaje>('pronadjiPredajePoId')));
  }
  pronadjiSvePredaje(): Observable<Predaje[]> {
    return this.httpClient
      .get<Predaje[]>(this.predajeUrl,  {headers: this.authService.getHeaders()})
      .pipe(
        tap(_ => this.log(`Učitani predaje`)),
        catchError(this.handleError<Predaje[]>('pronadjiSvePredaje', [])));
  }
  dodajNoviPredaje(predaje: Predaje): Observable<Predaje> {
    return this.httpClient
      .post<Predaje>(this.predajeUrl, predaje,  {headers: this.authService.getHeaders()})
      .pipe(
        tap(a => this.log(`Dodat predaje sa id "${a.idPredaje}"`)),
        catchError(this.handleError<Predaje>('dodajNoviPredaje')));
  }
  izmeniPredaje(predaje: Predaje): Observable<Predaje> {
    return this.httpClient
      .put<Predaje>(this.predajeUrl + predaje.idPredaje, predaje,  {headers: this.authService.getHeaders()})
      .pipe(
        tap(a => this.log(`Izmenjen predaje sa id "${a.idPredaje}"`)),
        catchError(this.handleError<Predaje>('izmeniPredaje')));
  }
  searchPredaje(term: string): Observable<Predaje[]> {
    if (!term.trim()) {
      return this.pronadjiSvePredaje();
    }

  return this.httpClient
  .get<Predaje[]>(`${this.predajeUrl}?naziv=${term}`,  {headers: this.authService.getHeaders()})
  .pipe(
    tap(_ => this.log(`Nadjeni predaje sa nazivom "${term}"`)),
    catchError(this.handleError<Predaje[]>('searchPredaje', []))
);
}

  private log(message: string) {
    this.messageService.add('PredajeService: ' + message);
  }

  private handleError<T> (operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(error);
      this.log(`${operation} failed: ${error.message}`);
      return of(result as T);
    };
  }
}
