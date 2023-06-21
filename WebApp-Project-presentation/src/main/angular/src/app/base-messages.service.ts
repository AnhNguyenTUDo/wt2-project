import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
// import { News } from './news';
import { Messages } from './messages';

export abstract class BaseMessagesService {
  protected defaultHeaders = new HttpHeaders({
    'Content-Type': 'application/json'
  });

  protected constructor(protected http: HttpClient) {
  }

//   abstract getNewest(): Observable<News>;

  abstract getAll(): Observable<Messages[]>;

  abstract create(content: string): Observable<Messages>;
}
