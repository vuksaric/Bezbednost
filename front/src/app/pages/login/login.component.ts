import { logging } from 'protractor';
import { RegistrationRequestService } from './../../services/registration-request.service';
import { AuthService } from './../../services/auth.service';
import { Route } from '@angular/compiler/src/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Validators, FormBuilder, FormGroup } from '@angular/forms';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  
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
      console.log(data);
    
    }, error => { 
      this.errorLogin = true;   
    })
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
        alert('Uspešno ste se registrovali!');
        this.router.navigateByUrl(`frontpage/login`);
      },
      error => {
        alert("Error login");
      });
    }
  }

  onButtonClickRegistration(){
    this.router.navigate(['register']);
  }



}
