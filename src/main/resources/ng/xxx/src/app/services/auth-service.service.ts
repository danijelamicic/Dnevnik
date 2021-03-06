import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { MessageService } from './message.service';
import { tap, catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private authUrl = environment.apiBaseUrl + '/auth/';
  public role: string;
public id: string;

  constructor(
    private httpClient: HttpClient,
    private messageService: MessageService
  ) {
    this.role = localStorage.getItem('role');
   this.id = localStorage.getItem('id');
  }

  login(email: string, password: string): Observable<string> {
    let httpHeaders = new HttpHeaders();
    httpHeaders = httpHeaders.append('Content-Type', 'application/json');
    httpHeaders = httpHeaders.append(
      'Authorization',
      'Basic ' + btoa(`${email}:${password}`)
    );

    return this.httpClient
      .post<any>(this.authUrl + 'login', {}, { headers: httpHeaders })
      .pipe(
        tap(a => this.log(`Uspesan login`)),
        catchError(this.handleError<any>('login'))
      );
  }

  logout() {
    localStorage.removeItem('credentials');
    localStorage.removeItem('role');
    localStorage.removeItem('id');

    this.role = null;
    this.id = null;
  }

  saveCredentials(email: string, password: string, role: string, id: string) {
    localStorage.setItem('credentials', btoa(`${email}:${password}`));
    localStorage.setItem('role', role);
    localStorage.setItem('id', id);
    this.role = role;
    this.id = id;
  }

  getHeaders(): HttpHeaders {
    let httpHeaders = new HttpHeaders();

    const credentials = localStorage.getItem('credentials');
    if (credentials) {
      httpHeaders = httpHeaders.append('Content-Type', 'application/json');
      httpHeaders = httpHeaders.append('Authorization', 'Basic ' + credentials);
    }

    return httpHeaders;
  }

  private log(message: string) {
    this.messageService.add('AuthService: ' + message);
  }

  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(error);
      this.log(`${operation} failed: ${error.message}`);
      return of(result as T);
    };
  }
}
