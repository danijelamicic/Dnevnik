import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { environment } from '../../environments/environment';
import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { AuthService } from './auth-service.service';
import { MessageService } from './message.service';
import { catchError, map, tap } from 'rxjs/operators';
import { PredmetURazredu } from '../entities/predmet-urazredu';


@Injectable({
  providedIn: 'root'
})
export class PredmetURazreduService {

  private predmetiurazreduUrl = environment.apiBaseUrl + '/predmetiURazredima/';

  constructor(private httpClient: HttpClient,
    private authService: AuthService,
    private messageService: MessageService) { }

  pronadjiPredmetURazreduPoId(id: number): Observable<PredmetURazredu> {
    return this.httpClient
      .get<PredmetURazredu>(this.predmetiurazreduUrl + id,  {headers: this.authService.getHeaders()})
      .pipe(
        tap(a => this.log(`Učitan predmeturazredu sa id "${a.idPrRaz}"`)),
        catchError(this.handleError<PredmetURazredu>('pronadjiPredmeteURazreduPoId')));
  }
  pronadjiSvePredmeteURazredu(): Observable<PredmetURazredu[]> {
    return this.httpClient
      .get<PredmetURazredu[]>(this.predmetiurazreduUrl,  {headers: this.authService.getHeaders()})
      .pipe(
        tap(_ => this.log(`Učitani predmetiurazredu`)),
        catchError(this.handleError<PredmetURazredu[]>('pronadjiSvePredmeteURazredu', [])));
  }
  dodajNoviPredmetURazredu(predmeturazredu: PredmetURazredu): Observable<PredmetURazredu> {
    return this.httpClient
      .post<PredmetURazredu>(this.predmetiurazreduUrl, predmeturazredu,  {headers: this.authService.getHeaders()})
      .pipe(
        tap(a => this.log(`Dodat predmeturazredu sa id "${a.idPrRaz}"`)),
        catchError(this.handleError<PredmetURazredu>('dodajNoviPredmetURazredu')));
  }
  izmeniPredmetURazredu(predmeturazredu: PredmetURazredu): Observable<PredmetURazredu> {
    return this.httpClient
      .put<PredmetURazredu>(this.predmetiurazreduUrl + predmeturazredu.idPrRaz, predmeturazredu,  {headers: this.authService.getHeaders()})
      .pipe(
        tap(a => this.log(`Izmenjen predmeturazredu sa id "${a.idPrRaz}"`)),
        catchError(this.handleError<PredmetURazredu>('izmeniPredmetURazredu')));
  }
  searchPredmetiURazredu(term: string): Observable<PredmetURazredu[]> {
    if (!term.trim()) {
      return this.pronadjiSvePredmeteURazredu();
    }

  return this.httpClient
  .get<PredmetURazredu[]>(`${this.predmetiurazreduUrl}?naziv=${term}`,  {headers: this.authService.getHeaders()})
  .pipe(
    tap(_ => this.log(`Nadjeni predmetiurazredu sa nazivom "${term}"`)),
    catchError(this.handleError<PredmetURazredu[]>('searchPredmetiURazredu', []))
);
}

  private log(message: string) {
    this.messageService.add('PredmetURazreduService: ' + message);
  }

  private handleError<T> (operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(error);
      this.log(`${operation} failed: ${error.message}`);
      return of(result as T);
    };
  }
}
