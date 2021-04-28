import { Router } from '@angular/router';
import { Component, OnInit } from '@angular/core';
import jwt_decode from 'jwt-decode';

@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.css']
})
export class HomepageComponent implements OnInit {

  public isAdmin = false;
  public isUser = false;
  private user: any;
  public pendingUsers = [];
  public approveAlert = false;
  public denyAlert = false;
  public empty = false;
  public decodedToken: any;
  public token: any;


  constructor(private router: Router) { }

  ngOnInit(): void {
    this.setupUser();
    this.setupUserType();
    this.getToken();
  }

  private getToken(): void {
    this.token = JSON.parse(localStorage.getItem('token') || '{}');
    this.decodedToken = this.getDecodedAccessToken(this.token);
    if (this.decodedToken === null || this.decodedToken === undefined) {
      alert("Nije dozvoljen pristup");
      this.router.navigate(['frontpage']);
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

  private setupUserType(): void {
    if (this.user.userRoles === "ADMIN") {
      this.isAdmin = true;
    } else if (this.user.userRoles === "USER") {
      this.isUser = true;
    }
  }

  public clearStorage(): void {
    localStorage.clear();
    this.router.navigateByUrl('frontpage');
  }


  registrationRequests(): void {
    this.router.navigate(['homepage/registration-requests']);
  }

  allCert(): void {
    this.router.navigate(['homepage/list-certificates']);
  }

  create(): void {
    this.router.navigate(['homepage/create-certificate']);
  }

  certificates(): void {
    this.router.navigate(['homepage/view-all-certificates']);
  }

  private setupUser(): void {
    this.user = JSON.parse(localStorage.getItem('user') || '{}');
    console.log(this.user);
  }



}
