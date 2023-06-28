import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { Message } from '../message';
import { BaseMessageService } from '../base-message.service';

@Injectable()
export class MessageService extends BaseMessageService {

  constructor(http: HttpClient) {
    super(http);
  }

  getAll(): Observable<Message[]> {
    return this.http.get(`/rest/messages`, { headers: this.defaultHeaders }).pipe(
      map((body: any) => body.map((message: any) => Message.fromObject(message)))
    );
  }

  getMessage(id: string): Observable<Message> {
    return this.http.get(`/rest/messages/${id}`, { headers: this.defaultHeaders }).pipe(
      map(body => Message.fromObject(body))
    );
  }

  create(content: string): Observable<Message> {
    return this.http.post(`/rest/messages`, { content }, { headers: this.defaultHeaders }).pipe(
      map(body => Message.fromObject(body))
    );
  }

  update(id: string, content: string): Observable<Message> {
    return this.http.put(`/rest/messages/${id}`, { content }, { headers: this.defaultHeaders }).pipe(
      map((body: any) => Message.fromObject(body))
    );
  }

  delete(id: string): Observable<void> {
    return this.http.delete(`/rest/messages/${id}`, { headers: this.defaultHeaders }).pipe(
      map(() => {})
    );
  }
}
