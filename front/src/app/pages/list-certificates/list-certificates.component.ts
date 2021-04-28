import { CertificateService } from './../../services/certificate.service';
import { Route } from '@angular/compiler/src/core';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import jwt_decode from 'jwt-decode';

@Component({
  selector: 'app-list-certificates',
  templateUrl: './list-certificates.component.html',
  styleUrls: ['./list-certificates.component.css']
})
export class ListCertificatesComponent implements OnInit {

  public certificates = [] as any;
  public pendingUsers = [];
  public approveAlert = false;
  public denyAlert = false;
  public empty = false;
  public decodedToken: any;
  public token: any;

  constructor(private router: Router, private route: ActivatedRoute, private certificateService: CertificateService) { }

  ngOnInit(): void {
    this.getAllCertificates();
    this.getToken();
  }

  private getToken(): void {
    this.token = JSON.parse(localStorage.getItem('token') || '{}');
    this.decodedToken = this.getDecodedAccessToken(this.token);
    if (this.decodedToken === null || this.decodedToken === undefined) {
      alert("Nije dozvoljen pristup");
      this.router.navigate(['frontpage']);
    } else {
      if (this.decodedToken.user_role === 'USER') {
        alert("Nije dozvoljen pristup");
        this.router.navigate(['homepage']);
      }
    }
  }

  getDecodedAccessToken(token: string): any {
    try {
      return jwt_decode(token);
    }
    catch (Error) {
      return null;
    }
  }

  public getAllCertificates(): void {
    this.certificateService.getAllCertificates().subscribe(data => {
      this.certificates = data;
      console.log(data);
    })
  }

  revoke(id): void {
    let body = {
      id: id
    }
    console.log(body);
    this.certificateService.revokeCert(body).subscribe(data => {
      console.log("uspeo");
      location.reload();
    }, error => {
      console.log("nije uspeo");
    })
  }

}
