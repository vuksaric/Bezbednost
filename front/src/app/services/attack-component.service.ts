import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class AttackComponentService {

  constructor(private http: HttpClient) { }

  public escape(body): Observable<any> {
    return this.http.post(`http://localhost:8092/attack/escape`, body);
  }

  public email(body): Observable<any> {
    return this.http.post(`http://localhost:8092/attack/email`, body);
  }

  public name(body): Observable<any> {
    return this.http.post(`http://localhost:8092/attack/name`, body);
  }

  public password(body): Observable<any> {
    return this.http.post(`http://localhost:8092/attack/password`, body);
  }

  public organisationCheck(body): Observable<any> {
    return this.http.post(`http://localhost:8092/attack/organisation`, body);
  }
}
