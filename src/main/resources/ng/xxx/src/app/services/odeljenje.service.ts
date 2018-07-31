import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { environment } from '../../environments/environment';
import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { AuthService } from './auth-service.service';
import { MessageService } from './message.service';
import { catchError, map, tap } from 'rxjs/operators';
import { Odeljenje } from '../entities/odeljenje';



@Injectable({
  providedIn: 'root'
})
export class OdeljenjeService {

  private odeljenjeUrl = environment.apiBaseUrl + '/odeljenja';

  constructor(private httpClient: HttpClient,
    private authService: AuthService,
    private messageService: MessageService) { }

  pronadjiOdeljenjePoId(idOdeljenje: number): Observable<Odeljenje> {
    return this.httpClient
      .get<Odeljenje>(this.odeljenjeUrl + '/' + idOdeljenje,  {headers: this.authService.getHeaders()})
      .pipe(
        tap(a => this.log(`Učitano odeljenje sa id "${a.idOdeljenje}"`)),
        catchError(this.handleError<Odeljenje>('pronadjiOdeljenjePoId')));
  }
  pronadjiSvaOdeljenja(): Observable<Odeljenje[]> {
    return this.httpClient
      .get<Odeljenje[]>(this.odeljenjeUrl,  {headers: this.authService.getHeaders()})
      .pipe(
        tap(_ => this.log(`Učitana odeljenja`)),
        catchError(this.handleError<Odeljenje[]>('pronadjiSvaOdeljenja', [])));
  }
  dodajNovoOdeljenje(odeljenje: Odeljenje): Observable<Odeljenje> {
    return this.httpClient
      .post<Odeljenje>(this.odeljenjeUrl, odeljenje,  {headers: this.authService.getHeaders()})
      .pipe(
        tap(a => this.log(`Dodato odeljenje sa id "${a.idOdeljenje}"`)),
        catchError(this.handleError<Odeljenje>('dodajNovoOdeljenje')));
  }
  izmeniOdeljenje(odeljenje: Odeljenje): Observable<Odeljenje> {
    return this.httpClient
      .put<Odeljenje>(this.odeljenjeUrl + odeljenje.idOdeljenje, odeljenje,  {headers: this.authService.getHeaders()})
      .pipe(
        tap(a => this.log(`Izmenjeno odeljenje sa id "${a.idOdeljenje}"`)),
        catchError(this.handleError<Odeljenje>('izmeniOdeljenje')));
  }
  searchOdeljenje(term: string): Observable<Odeljenje[]> {
    if (!term.trim()) {
      return this.pronadjiSvaOdeljenja();
    }

  return this.httpClient
  .get<Odeljenje[]>(`${this.odeljenjeUrl}?naziv=${term}`,  {headers: this.authService.getHeaders()})
  .pipe(
    tap(_ => this.log(`Nadjeno odeljenje sa nazivom "${term}"`)),
    catchError(this.handleError<Odeljenje[]>('searchOdeljenje', []))
);
}

  private log(message: string) {
    this.messageService.add('OdeljenjeService: ' + message);
  }

  private handleError<T> (operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(error);
      this.log(`${operation} failed: ${error.message}`);
      return of(result as T);
    };
  }
}
