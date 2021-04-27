import { Router } from '@angular/router';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.css']
})
export class HomepageComponent implements OnInit {

  public isAdmin = false;
  public isUser = false;
  private user: any;


  constructor(private router: Router) { }

  ngOnInit(): void {
    this.setupUser();
    this.setupUserType();
  }

  private setupUserType(): void {
    if(this.user.userRoles === "ADMIN"){
      this.isAdmin = true;
    }else if(this.user.userRoles === "USER"){
      this.isUser = true;
    }
  }

  public clearStorage(): void {
    localStorage.clear();
    this.router.navigateByUrl('frontpage');
  }


  registrationRequests():void{
    this.router.navigate(['homepage/registration-requests']);
  }

  allCert():void{
    this.router.navigate(['homepage/list-certificates']);
  }

  create():void{
    this.router.navigate(['homepage/create-certificate']);
    }

  certificates():void{
      this.router.navigate(['homepage/create-certificate']);
  }

  private setupUser(): void {
    this.user = JSON.parse(localStorage.getItem('user')|| '{}');
    console.log(this.user);
  } 



}
