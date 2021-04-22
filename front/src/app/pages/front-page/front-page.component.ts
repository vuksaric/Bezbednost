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

  loginForm():void{
    this.router.navigate(['frontpage/login']);
  }

  registrationForm():void{
    this.router.navigate(['frontpage/register']);
  }

}
