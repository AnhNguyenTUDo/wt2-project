import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Message } from './message';

export abstract class BaseMessageService {
  protected defaultHeaders = new HttpHeaders({
    'Content-Type': 'application/json'
  });

  protected constructor(protected http: HttpClient) {
  }
  abstract getMessage(id: string): Observable<Message>;

  abstract getAll(): Observable<Message[]>;

  abstract create(content: string): Observable<Message>;

  abstract update(id: string, content: string): Observable<Message>;

  abstract delete(id: string): Observable<void>;
}
