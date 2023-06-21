import { Injectable } from '@angular/core';
import { BaseMessagesService } from '../base-messages.service';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Messages } from '../messages';
import { map } from 'rxjs/operators';
import { BasicAuthService } from './basic-auth.service';
import { AuthService } from './auth.service';

@Injectable()
export class AuthMessagesService extends BaseMessagesService {

  private _authService: AuthService;

  constructor(http: HttpClient) {
    super(http);
    this._authService = new BasicAuthService(http);
  }

  getAll(): Observable<Messages[]> {
    return this.http.get<any[]>(`${this._authService.getBaseUrl()}/messages`, {headers: this._authService.getAuthHeaders()}).pipe(
      map(body => body.map(n => Messages.fromObject(n)))
    );
  }

//   getNewest(): Observable<Messages> {
//     return this.http.get<any>(`${this._authService.getBaseUrl()}/messages/newest`, {headers: this._authService.getAuthHeaders()}).pipe(
//       map(body => Messages.fromObject(body))
//     );
//   }

  create(content: string): Observable<Messages> {
    return this.http.post<any>(`${this._authService.getBaseUrl()}/messages`, {content}, {headers: this._authService.getAuthHeaders()}).pipe(
      map(body => Messages.fromObject(body))
    );
  }

  set authService(value: AuthService) {
    this._authService = value;
  }
}
