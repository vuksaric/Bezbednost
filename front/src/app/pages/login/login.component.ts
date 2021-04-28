import { RegistrationRequestService } from './../../services/registration-request.service';
import { AuthService } from './../../services/auth.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Validators, FormBuilder, FormGroup } from '@angular/forms';
import { Component, OnInit } from '@angular/core';
import jwt_decode from 'jwt-decode';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  token: any;
  errorLogin: boolean = false;
  validateForm!: FormGroup;

  constructor(private route: ActivatedRoute,private router:Router,private fb: FormBuilder, private authService: AuthService, private rrService: RegistrationRequestService) {}

  submitForm(): void {
    for (const i in this.validateForm.controls) {
      this.validateForm.controls[i].markAsDirty();
      this.validateForm.controls[i].updateValueAndValidity();
    }
    const body = {
        username: this.validateForm.value.username,
        password: this.validateForm.value.password
    }
    this.authService.login(body).subscribe(data => {
      const user = data;
      localStorage.setItem('user', JSON.stringify(user));
      localStorage.setItem('token', JSON.stringify(user.token));
      console.log( this.getDecodedAccessToken(data.token));
      this.router.navigate(['homepage']);
    }, error => { 
      this.errorLogin = true;   
    })
  }

  getDecodedAccessToken(token: string): any {
    try{
        return jwt_decode(token);
    }
    catch(Error){
        return null;
    }
  }

  forgotPassword(): void{
    this.router.navigate(['frontpage/forgot-password']);
  }
  

  ngOnInit(): void {
    this.validateForm = this.fb.group({
      username: [null, [Validators.required]],
      password: [null, [Validators.required]],
      remember: [true]
    });

    const email = this.route.snapshot.params.email;
    console.log(email);
    if(email != undefined){
      const body = {
        email: email
      }
      this.rrService.confirmRegistrationRequest(body).subscribe(() => {
        this.router.navigateByUrl(`frontpage/login`);
      },
      error => {
      });
    }
  }

  onButtonClickRegistration(){
    this.router.navigate(['register']);
  }



}
