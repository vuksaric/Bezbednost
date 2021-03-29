import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-front-page',
  templateUrl: './front-page.component.html',
  styleUrls: ['./front-page.component.css']
})
export class FrontPageComponent implements OnInit {

  id: any;

  constructor(private router: Router ) { }

  ngOnInit(): void {
  }

  adminHomePage():void{
    localStorage.clear();
    localStorage.setItem('userId', JSON.stringify(1));
    this.router.navigate(['front-page/admin-home-page']);
  }

  user1HomePage():void{
    console.log("kliknuo sam na tab za maju");
    localStorage.clear();
    this.id = 1;
    localStorage.setItem('userId', JSON.stringify(1));
    this.router.navigate([`front-page/user-home-page/${this.id}`]);
  }

  user2HomePage():void{
    console.log("kliknuo sam na tab za ninu");
    localStorage.clear();
    this.id = 2;
    localStorage.setItem('userId', JSON.stringify(2));
    this.router.navigate([`front-page/user-home-page/${this.id}`]);
  }

}
