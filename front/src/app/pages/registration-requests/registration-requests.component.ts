import { Router } from '@angular/router';
import { RegistrationRequestService } from './../../services/registration-request.service';
import { Component, OnInit } from '@angular/core';
import jwt_decode from 'jwt-decode';
import { ThisReceiver } from '@angular/compiler';


@Component({
  selector: 'app-registration-requests',
  templateUrl: './registration-requests.component.html',
  styleUrls: ['./registration-requests.component.css']
})
export class RegistrationRequestsComponent implements OnInit {

  public pendingUsers = [];
  public approveAlert = false;
  public denyAlert = false;
  public empty = false;
  public decodedToken: any;
  public token: any;

  constructor(private router: Router, private rrService: RegistrationRequestService) { }


  ngOnInit(): void {
    this.getToken();
    this.getAllPendingUsers();
  }

  private getToken(): void {
    this.token = JSON.parse(localStorage.getItem('token') || '{}');
    this.decodedToken = this.getDecodedAccessToken(this.token);
    if (this.decodedToken === null || this.decodedToken === undefined) {
      alert("Nije dozvoljen pristup");
      this.router.navigate(['frontpage']);
    }else {
      if(this.decodedToken.user_role === 'USER'){
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

  private getAllPendingUsers(): void {
    this.rrService.getAllPendingUsers().subscribe(data => {
      this.pendingUsers = data;
      if (this.pendingUsers.length === 0) {
        this.empty = true;
      }
    }, error => {

    })
  }

  public approve(id): void {
    const body = {
      id: id
    }
    this.rrService.approveRegistrationRequest(body).subscribe(data => {
      location.reload();
      this.approveAlert = true;
    })
  }

  public deny(id): void {
    const body = {
      id: id
    }
    this.rrService.denyRegistrationRequest(body).subscribe(data => {
      location.reload();
      this.denyAlert = true;
    })
  }


}
