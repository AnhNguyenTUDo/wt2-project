import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError, map, switchMap } from 'rxjs/operators';
import { environment } from 'src/environments/environment';

@Injectable()
export class BasicAuthService {
  private token: string | null = null;
  public isAdmin: boolean = false;
  public username: string | null = null;

  constructor(private http: HttpClient) {}

  login(username: string, password: string): Observable<boolean> {
    const encodedToken = window.btoa(decodeURIComponent(encodeURIComponent(username + ':' + password)));
    const authHeader = `Basic ${encodedToken}`;

    const headers = new HttpHeaders({
      'Authorization': authHeader,
      'Content-Type': 'application/json'
    });

    return this.http.post<any>(`${environment.apiUrl}/users/login`, {}, { headers }).pipe(
      switchMap(body => {
        this.token = encodedToken;
        this.username = username;

        return this.isAdminCheck().pipe(
          map(isAdmin => {
            this.isAdmin = isAdmin;
            return true;
          }),
          catchError(() => of(false))
        );
      }),
      catchError(() => of(false))
      );
    }

  checkAuthorization(id: string): Observable<boolean>{
    const headers = this.getAuthHeaders();
    return this.http.get<boolean>(`/rest/messages/authorization/${id}`, {headers}).pipe(
      catchError(() => of(false))
    );
  }

  isAdminCheck(): Observable<boolean> {
      const headers = this.getAuthHeaders();
      return this.http.get<boolean>(`${environment.apiUrl}/users/role`, { headers }).pipe(
        catchError(() => of(false))
      );
    }

  resetAdminStatus():void{
    this.isAdmin = false;
  }

  getBaseUrl(): string {
      return `${environment.apiUrl}`;
  }

  logout(): Observable<boolean> {
    this.token = null;
    return of(true);
  }

  getAuthHeaders(): HttpHeaders {
    return this.token == null ? new HttpHeaders() : new HttpHeaders({
      "Authorization": `Basic ${this.token}`
    });
  }

  get isLoggedIn(): boolean {
    return this.token != null;
  }
}
