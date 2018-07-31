import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { environment } from '../../environments/environment';
import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { AuthService } from './auth-service.service';
import { MessageService } from './message.service';
import { catchError, map, tap } from 'rxjs/operators';
import { Ocena } from '../entities/ocena';




@Injectable({
  providedIn: 'root'
})
export class OcenaService {

  private ocenaUrl = environment.apiBaseUrl + '/ocene/';

  constructor(private httpClient: HttpClient,
    private authService: AuthService,
    private messageService: MessageService) { }

  pronadjiOcenuPoId(id: number): Observable<Ocena> {
    return this.httpClient
      .get<Ocena>(this.ocenaUrl + id,  {headers: this.authService.getHeaders()})
      .pipe(
        tap(a => this.log(`Učitana ocena sa id "${a.idOcena}"`)),
        catchError(this.handleError<Ocena>('pronadjiOcenePoId')));
  }
  pronadjiSveOcene(): Observable<Ocena[]> {
    return this.httpClient
      .get<Ocena[]>(this.ocenaUrl,  {headers: this.authService.getHeaders()})
      .pipe(
        tap(_ => this.log(`Učitane ocene`)),
        catchError(this.handleError<Ocena[]>('pronadjiSveOcene', [])));
  }
  dodajNovoOdeljenje(ocena: Ocena): Observable<Ocena> {
    return this.httpClient
      .post<Ocena>(this.ocenaUrl, ocena,  {headers: this.authService.getHeaders()})
      .pipe(
        tap(a => this.log(`Dodata ocena sa id "${a.idOcena}"`)),
        catchError(this.handleError<Ocena>('dodajNovuOcenu')));
  }
  izmeniOdeljenje(ocena: Ocena): Observable<Ocena> {
    return this.httpClient
      .put<Ocena>(this.ocenaUrl + ocena.idOcena, ocena,  {headers: this.authService.getHeaders()})
      .pipe(
        tap(a => this.log(`Izmenjen ocena sa id "${a.idOcena}"`)),
        catchError(this.handleError<Ocena>('izmeniOcenu')));
  }
  prikaziSveOceneUcenikaPoPredmetu(idUcenika: number, idPredajeUOdeljenju: number): Observable<Ocena[]> {
    return this.httpClient
    .get<Ocena[]>(this.ocenaUrl + idUcenika + '/ucenikPredmet/' + idPredajeUOdeljenju,  {headers: this.authService.getHeaders()})
    .pipe(
      tap(
      catchError(this.handleError<Ocena[]>('prikaziSveOceneUcenikaPoPredmetu', []))));
  }
  searchOcene(term: string): Observable<Ocena[]> {
    if (!term.trim()) {
      return this.pronadjiSveOcene();
    }

  return this.httpClient
  .get<Ocena[]>(`${this.ocenaUrl}?naziv=${term}`,  {headers: this.authService.getHeaders()})
  .pipe(
    tap(_ => this.log(`Nadjena ocena sa nazivom "${term}"`)),
    catchError(this.handleError<Ocena[]>('searchOcene', []))
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

