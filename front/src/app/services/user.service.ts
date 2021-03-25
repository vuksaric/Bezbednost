import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) { }

  public getAllSubjects(): Observable<any> {
    return this.http.get(`http://localhost:8092/subject/getAllSubjects`);
  }

  public getAllIssuers(): Observable<any> {
    return this.http.get(`http://localhost:8092/admin/getAllIssuer`);
  }
}
