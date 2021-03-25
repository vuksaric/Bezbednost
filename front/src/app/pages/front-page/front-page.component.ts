import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-front-page',
  templateUrl: './front-page.component.html',
  styleUrls: ['./front-page.component.css']
})
export class FrontPageComponent implements OnInit {

  constructor(private router: Router ) { }

  ngOnInit(): void {
  }

  adminHomePage():void{
    localStorage.clear();
    localStorage.setItem('userId', JSON.stringify(1));
    this.router.navigate(['front-page/admin-home-page']);
  }

  user1HomePage():void{
    localStorage.clear();
    localStorage.setItem('userId', JSON.stringify(2));
    this.router.navigate(['front-page/user-home-page']);
  }

  user2HomePage():void{
    localStorage.clear();
    localStorage.setItem('userId', JSON.stringify(3));
    this.router.navigate(['front-page/user-home-page']);
  }

}
