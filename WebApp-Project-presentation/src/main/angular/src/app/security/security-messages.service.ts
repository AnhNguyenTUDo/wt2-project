import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Messages } from '../messages';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { BaseMessagesService } from '../base-messages.service';
import { environment as env } from '../../environments/environment';

@Injectable()
export class SecurityMessagesService extends BaseMessagesService {

  constructor(http: HttpClient) {
    super(http);
  }

  getAll(): Observable<Messages[]> {
    return this.http.get<any[]>(`${env.apiUrl}/security/messages`, {headers: this.defaultHeaders}).pipe(
      map(body => body.map(n => Messages.fromObject(n)))
    );
  }

//   getNewest(): Observable<Messages> {
//     return this.http.get<any>(`${env.apiUrl}/security/messages/newest`, {headers: this.defaultHeaders}).pipe(
//       map(body => Messages.fromObject(body))
//     );
//   }

  create(content: string): Observable<Messages> {
    return this.http.post<any>(`${env.apiUrl}/security/messages`, {content}, {headers: this.defaultHeaders}).pipe(
      map(body => Messages.fromObject(body))
    );
  }
}
