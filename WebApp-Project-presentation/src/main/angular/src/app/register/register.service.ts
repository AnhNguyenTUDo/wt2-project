import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment as env } from '../../environments/environment';
import { Users } from '../users';
import { map } from 'rxjs/operators';

@Injectable()
export class RegisterService{

  constructor(private http: HttpClient)  { }

  protected defaultHeaders = new HttpHeaders({
      'Content-Type': 'application/json'
    });

  registerUser(name: string, password: string): Observable<Users> {
      return this.http.post(`${env.apiUrl}/user/register`, {name, password}, {headers: this.defaultHeaders}).pipe(
         map(body => Users.fromObject(body))
         );
    }

//     create(headline: string, content: string): Observable<News> {
//         return this.http.post(`${env.apiUrl}/news`, {headline, content}, {headers: this.defaultHeaders}).pipe(
//           map(body => News.fromObject(body))
//         );
//       }


}
