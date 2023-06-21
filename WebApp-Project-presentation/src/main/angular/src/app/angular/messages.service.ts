import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Messages } from '../messages';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { BaseMessagesService } from '../base-messages.service';
import { environment as env } from '../../environments/environment';

@Injectable()
export class MessagesService extends BaseMessagesService {

  constructor(http: HttpClient) {
    super(http);
  }

  getAll(): Observable<Messages[]> {
    return this.http.get(`${env.apiUrl}/messages`, {headers: this.defaultHeaders}).pipe(
      map((body: any[]) => body.map(n => Messages.fromObject(n)))
    );
  }

//   getMessageest(): Observable<Messages> {
//     return this.http.get(`${env.apiUrl}/Messages/Messageest`, {headers: this.defaultHeaders}).pipe(
//       map(body => Messages.fromObject(body))
//     );
//   }
////////////////////////////////////////////////TO CHANGE POST URL/////////////////////////
  create(message: string): Observable<Messages> {
    return this.http.post(`${env.apiUrl}/messages/user/2`, {message}, {headers: this.defaultHeaders}).pipe(
      map(body => Messages.fromObject(body))
    );
  }
}
