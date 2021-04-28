import { AuthService } from './../../services/auth.service';
import { AttackComponentService } from './../../services/attack-component.service';
import { Router } from '@angular/router';
import { FormControl, Validators, FormGroup, FormBuilder } from '@angular/forms';
import { Component, OnInit } from '@angular/core';
import { formatCurrency } from '@angular/common';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit {


  errorRegister: boolean = false;
  validateForm!: FormGroup;
  emailBool: boolean = false;
  name: boolean = false;
  lastName: boolean = false;
  passwordBool: boolean = false;
  organisation: boolean = false;
  organisationUnit: boolean = false;

  constructor(private router: Router, private fb: FormBuilder, private authService: AuthService, private attackService: AttackComponentService) { }

  submitForm(): void {
    for (const i in this.validateForm.controls) {
      this.validateForm.controls[i].markAsDirty();
      this.validateForm.controls[i].updateValueAndValidity();
    }

    this.attackService.email(this.validateForm.value.username).subscribe(data => {
      this.emailBool = data.bool;
      console.log(this.emailBool);
      this.attackService.name(this.validateForm.value.firstName).subscribe(data => {
        this.name = data.bool;
        console.log(this.name);
        this.attackService.name(this.validateForm.value.lastName).subscribe(data => {
          this.lastName = data.bool;
          console.log(this.lastName);
          this.attackService.password(this.validateForm.value.password).subscribe(data => {
            this.passwordBool = data.bool;
            console.log(this.passwordBool);
            this.attackService.organisationCheck(this.validateForm.value.organisation).subscribe(data => {
              this.organisation = data.bool;
              console.log(this.organisation);
              this.attackService.organisationCheck(this.validateForm.value.organisationUnit).subscribe(data => {
                this.organisationUnit = data.bool;
                console.log(this.organisationUnit);
                if (this.emailBool && this.name && this.lastName && this.passwordBool && this.organisation && this.organisationUnit) {
                  const body = {
                    username: this.validateForm.value.username,
                    password: this.validateForm.value.password,
                    rePassword: this.validateForm.value.rePassword,
                    organisation: this.validateForm.value.organisation,
                    organisationUnit: this.validateForm.value.organisationUnit,
                    firstName: this.validateForm.value.firstName,
                    lastName: this.validateForm.value.lastName
                  }
                  console.log(body);
                  this.authService.registerSubject(body).subscribe(data => {
                    this.router.navigateByUrl(`frontpage/login`);

                  }, error => {
                    this.errorRegister = true;
                  })
                } else {
                  
                }
              })
            })
          })
        })
      })
    })
  }




  updateConfirmValidator(): void {
    /** wait for refresh value */
    Promise.resolve().then(() => this.validateForm.controls.checkPassword.updateValueAndValidity());
  }

  confirmationValidator = (control: FormControl): { [s: string]: boolean } => {
    if (!control.value) {
      return { required: true };
    } else if (control.value !== this.validateForm.controls.password.value) {
      return { confirm: true, error: true };
    }
    return {};
  };

  getCaptcha(e: MouseEvent): void {
    e.preventDefault();
  }



  ngOnInit(): void {
    this.validateForm = this.fb.group({
      username: [null, [Validators.email, Validators.required]],
      password: [null, [Validators.required]],
      rePassword: [null, [Validators.required , this.confirmationValidator]],
      firstName: [null, [Validators.required]],
      lastName: [null, [Validators.required]],
      organisation: [null, [Validators.required]],
      organisationUnit: [null, [Validators.required]]
    });
  }



}
