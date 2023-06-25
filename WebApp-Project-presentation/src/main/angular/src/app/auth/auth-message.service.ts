import { Injectable } from '@angular/core';
import { BaseMessageService } from '../base-message.service';
import { Observable } from 'rxjs';
import { Message } from '../message';
import { map } from 'rxjs/operators';
import { BasicAuthService } from './basic-auth.service';
import { HttpClient } from '@angular/common/http';

@Injectable()
export class AuthMessageService extends BaseMessageService {

  private authService: BasicAuthService;

  constructor(http: HttpClient){
    super(http);
    this.authService = new BasicAuthService(http);
  }

  getAll(): Observable<Message[]>{
    return this.http.get<any[]>(`${this.authService.getBaseUrl()}/messages`, {headers: this.authService.getAuthHeaders()}).pipe(
          map(body => body.map(n => Message.fromObject(n)))
        );
  }

  getMessage(id: string): Observable<Message>{
    return this.http.get<any>(`${this.authService.getBaseUrl()}/messages/${id}`, {headers: this.authService.getAuthHeaders()}).pipe(
              map(body => Message.fromObject(body))
            );
  }

  create(content: string): Observable<Message>{
    return this.http.post<any>(`${this.authService.getBaseUrl()}/messages`, {content}, {headers: this.authService.getAuthHeaders()}).pipe(
          map(body => Message.fromObject(body))
        );
  }

  update(id: string, content: string): Observable<Message>{
        return this.http
          .put<any>(`${this.authService.getBaseUrl()}/messages/${id}`, { content }, { headers: this.authService.getAuthHeaders()}).pipe(
          map((body) => Message.fromObject(body))
          );
  }

  delete(id: string): Observable<void>{
        return this.http.delete<void>(`${this.authService.getBaseUrl()}/messages/${id}`, {
          headers: this.authService.getAuthHeaders(),
        });
  }

//   set authService(value: BasicAuthService) {
//       this.authService = value;
//     }

}
