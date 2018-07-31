import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { environment } from '../../environments/environment';
import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { AuthService } from './auth-service.service';
import { MessageService } from './message.service';
import { catchError, map, tap } from 'rxjs/operators';
import { PredajeUOdeljenju } from '../entities/predaje-uodeljenju';

@Injectable({
  providedIn: 'root'
})
export class PredajeUOdeljenjuService {
  private predajeuodeljenjuUrl = environment.apiBaseUrl + '/predajeuodeljenju/';

  constructor(
    private httpClient: HttpClient,
    private authService: AuthService,
    private messageService: MessageService
  ) {}

  pronadjiPredajeUOdeljenjuPoId(id: number): Observable<PredajeUOdeljenju> {
    return this.httpClient
      .get<PredajeUOdeljenju>(this.predajeuodeljenjuUrl + id, {
        headers: this.authService.getHeaders()
      })
      .pipe(
        tap(a =>
          this.log(`Učitan predajeuodeljenju sa id "${a.idPredajeUOdeljenju}"`)
        ),
        catchError(
          this.handleError<PredajeUOdeljenju>('pronadjiPredajeUOdeljenjuPoId')
        )
      );
  }
  pronadjiSvePredajeUOdeljenju(): Observable<PredajeUOdeljenju[]> {
    return this.httpClient
      .get<PredajeUOdeljenju[]>(this.predajeuodeljenjuUrl, {
        headers: this.authService.getHeaders()
      })
      .pipe(
        tap(_ => this.log(`Učitani predajeuodeljenju`)),
        catchError(
          this.handleError<PredajeUOdeljenju[]>(
            'pronadjiSvePredajeUOdeljenju',
            []
          )
        )
      );
  }
  dodajNoviPredajeUOdeljenju(
    predajeuodeljenju: PredajeUOdeljenju
  ): Observable<PredajeUOdeljenju> {
    return this.httpClient
      .post<PredajeUOdeljenju>(this.predajeuodeljenjuUrl, predajeuodeljenju, {
        headers: this.authService.getHeaders()
      })
      .pipe(
        tap(a =>
          this.log(`Dodat predajeuodeljenju sa id "${a.idPredajeUOdeljenju}"`)
        ),
        catchError(
          this.handleError<PredajeUOdeljenju>('dodajNoviPredajeUOdeljenju')
        )
      );
  }
  izmeniPredajeUOdeljenju(
    predajeuodeljenju: PredajeUOdeljenju
  ): Observable<PredajeUOdeljenju> {
    return this.httpClient
      .put<PredajeUOdeljenju>(
        this.predajeuodeljenjuUrl + predajeuodeljenju.idPredajeUOdeljenju,
        predajeuodeljenju,
        { headers: this.authService.getHeaders() }
      )
      .pipe(
        tap(a =>
          this.log(
            `Izmenjen predajeuodeljenju sa id "${a.idPredajeUOdeljenju}"`
          )
        ),
        catchError(
          this.handleError<PredajeUOdeljenju>('izmeniPredajeUOdeljenju')
        )
      );
  }
  searchPredajeUOdeljenju(term: string): Observable<PredajeUOdeljenju[]> {
    if (!term.trim()) {
      return this.pronadjiSvePredajeUOdeljenju();
    }

    return this.httpClient
      .get<PredajeUOdeljenju[]>(`${this.predajeuodeljenjuUrl}?naziv=${term}`, {
        headers: this.authService.getHeaders()
      })
      .pipe(
        tap(_ => this.log(`Nadjeni predajeuodeljenju sa nazivom "${term}"`)),
        catchError(
          this.handleError<PredajeUOdeljenju[]>('searchPredajeUOdeljenju', [])
        )
      );
  }

  private log(message: string) {
    this.messageService.add('PredajeUOdeljenjuService: ' + message);
  }

  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(error);
      this.log(`${operation} failed: ${error.message}`);
      return of(result as T);
    };
  }
}
