import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CertificateService } from './../../services/certificate.service';
import jwt_decode from 'jwt-decode';
import { Router } from '@angular/router';

@Component({
  selector: 'app-view-all-certificates',
  templateUrl: './view-all-certificates.component.html',
  styleUrls: ['./view-all-certificates.component.css']
})
export class ViewAllCertificatesComponent implements OnInit {

  id: any;
  user: any;
  public certificates = [] as any;
  public isValid = false;
  public notValid = true;
  public pendingUsers = [];
  public approveAlert = false;
  public denyAlert = false;
  public empty = false;
  public decodedToken: any;
  public token: any;

  constructor(private router: Router, private route: ActivatedRoute, private certificateService: CertificateService) { }

  ngOnInit(): void {
    this.setUpId();
    this.getCertChain();
  }

  private getToken(): void {
    this.token = JSON.parse(localStorage.getItem('token') || '{}');
    this.decodedToken = this.getDecodedAccessToken(this.token);
    if (this.decodedToken === null || this.decodedToken === undefined) {
      alert("Nije dozvoljen pristup");
      this.router.navigate(['frontpage']);
    } else {
      if (this.decodedToken.user_role === 'ADMIN') {
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

  private setUpId(): void {

    console.log("Id usera");
    this.user = JSON.parse(localStorage.getItem("user"));
    console.log(this.user);
    this.id = this.user.id;
  }

  public getCertChain(): void {
    this.certificateService.getCertChain(this.id).subscribe(data => {
      this.certificates = data;
      console.log(data);
    })
  }

  public download(id): void {
    this.certificateService.downloadCert(id).subscribe(data => {
      console.log("uspeo");
    })
  }

  checkValidity(id): void {
    this.certificateService.checkValitidy(id).subscribe(data => {
      console.log("uspeo");
      if (data === true) {
        this.isValid = data;
      }
      else {
        this.notValid = data;
      }
      console.log(data);
    }, error => {
      console.log("nije uspeo");
    })
  }
}
