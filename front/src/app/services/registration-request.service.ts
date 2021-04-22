import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class RegistrationRequestService {

  constructor(private http: HttpClient) { }

  public confirmRegistrationRequest(body): Observable<any> {
    return this.http.put('http://localhost:8092/subject/confirm', body);
  }

  public getAllPendingUsers(): Observable<any> {
    return this.http.get('http://localhost:8092/subject/registration-requests');
  }

  public approveRegistrationRequest(body): Observable<any> {
    return this.http.put('http://localhost:8092/subject/approve', body);
  }

  public denyRegistrationRequest(body): Observable<any> {
    return this.http.put('http://localhost:8092/subject/deny', body);
  }
}
