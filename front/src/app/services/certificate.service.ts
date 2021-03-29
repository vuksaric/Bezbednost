import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CertificateService {

  constructor(private http: HttpClient) { }

  public getAllCertificates(): Observable<any> {
    return this.http.get('http://localhost:8092/admin/allCertificates');
  }

  public getCertChain(id): Observable<any> {
    return this.http.get(`http://localhost:8092/subject/getCertChain/${id}`);
  }

  public downloadCert(id): Observable<any> {
    return this.http.get(`http://localhost:8092/subject/download/${id}`);
  }

  public createCertificate(type, days, subjectId, issuerId, body): Observable<any> {
    return this.http.post(`http://localhost:8092/admin/addCertificate/${type}/${days}/${subjectId}/${issuerId}`, body);
  }

  public revokeCert(body): Observable<any> {
    return this.http.post(`http://localhost:8092/ocsp/revoke`, body);
  }

  public checkValitidy(id): Observable<any> {
    return this.http.get(`http://localhost:8092/ocsp/checkValidity/${id}`)
  }
}
