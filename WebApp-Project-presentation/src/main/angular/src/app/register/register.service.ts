import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { map, catchError } from 'rxjs/operators';
import { environment as env } from '../../environments/environment';

@Injectable()
export class RegisterService{

  constructor(private http: HttpClient)  { }

  protected defaultHeaders = new HttpHeaders({
      'Content-Type': 'application/json'
    });

    registerUser(username: string, password: string): Observable<any> {
      return this.http.post(`${env.apiUrl}/users/register`, {username, password}, {headers: this.defaultHeaders, responseType: 'text'}).pipe(
        catchError((error: any) => {
           console.error('Registration failed:', error);
           return throwError('Failed to register');
           })
      );
    }

}
