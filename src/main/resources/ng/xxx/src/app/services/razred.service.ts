import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { environment } from '../../environments/environment';
import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { AuthService } from './auth-service.service';
import { MessageService } from './message.service';
import { catchError, map, tap } from 'rxjs/operators';
import { Razred } from '../entities/razred';

@Injectable({
  providedIn: 'root'
})
export class RazredService {

  private razrediUrl = environment.apiBaseUrl + '/razredi/';

  constructor(private httpClient: HttpClient,
    private authService: AuthService,
    private messageService: MessageService) { }

  pronadjiRazredPoId(id: number): Observable<Razred> {
    return this.httpClient
      .get<Razred>(this.razrediUrl + id,  {headers: this.authService.getHeaders()})
      .pipe(
        tap(a => this.log(`Učitan razred sa id "${a.idRazred}"`)),
        catchError(this.handleError<Razred>('pronadjiRazredePoId')));
  }
  pronadjiSveRazrede(): Observable<Razred[]> {
    return this.httpClient
      .get<Razred[]>(this.razrediUrl,  {headers: this.authService.getHeaders()})
      .pipe(
        tap(_ => this.log(`Učitani razredi`)),
        catchError(this.handleError<Razred[]>('pronadjiSveRazred', [])));
  }
  dodajNoviRazred(razred: Razred): Observable<Razred> {
    return this.httpClient
      .post<Razred>(this.razrediUrl, razred,  {headers: this.authService.getHeaders()})
      .pipe(
        tap(a => this.log(`Dodat razred sa id "${a.idRazred}"`)),
        catchError(this.handleError<Razred>('dodajNoviRazred')));
  }
  izmeniRoditelja(razred: Razred): Observable<Razred> {
    return this.httpClient
      .put<Razred>(this.razrediUrl + razred.idRazred, razred,  {headers: this.authService.getHeaders()})
      .pipe(
        tap(a => this.log(`Izmenjen razred sa id "${a.idRazred}"`)),
        catchError(this.handleError<Razred>('izmeniRazred')));
  }
  searchRazredi(term: string): Observable<Razred[]> {
    if (!term.trim()) {
      return this.pronadjiSveRazrede();
    }

  return this.httpClient
  .get<Razred[]>(`${this.razrediUrl}?naziv=${term}`,  {headers: this.authService.getHeaders()})
  .pipe(
    tap(_ => this.log(`Nadjeni razredi sa nazivom "${term}"`)),
    catchError(this.handleError<Razred[]>('searchRazredi', []))
);
}

  private log(message: string) {
    this.messageService.add('RazredService: ' + message);
  }

  private handleError<T> (operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(error);
      this.log(`${operation} failed: ${error.message}`);
      return of(result as T);
    };
  }
}
