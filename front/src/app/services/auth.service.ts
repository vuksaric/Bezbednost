import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient) { }

  public login(body): Observable<any> {
    return this.http.put(`http://localhost:8092/auth/login`, body);
  }

  public registerSubject(body): Observable<any> {
    return this.http.post('http://localhost:8092/auth/register', body);
  }
}
