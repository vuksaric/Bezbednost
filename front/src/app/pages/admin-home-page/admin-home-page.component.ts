import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import jwt_decode from 'jwt-decode';

@Component({
  selector: 'app-admin-home-page',
  templateUrl: './admin-home-page.component.html',
  styleUrls: ['./admin-home-page.component.css']
})
export class AdminHomePageComponent implements OnInit {
  public pendingUsers = [];
  public approveAlert = false;
  public denyAlert = false;
  public empty = false;
  public decodedToken: any;
  public token: any;

  constructor(private router: Router) { }

  ngOnInit(): void {
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

  create(): void {
    this.router.navigate(['front-page/create-certificate']);
  }

  viewList(): void {
    this.router.navigate(['front-page/list-certificates']);
  }

}
