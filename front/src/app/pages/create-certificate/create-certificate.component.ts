import { UserService } from './../../services/user.service';
import { CertificateService } from './../../services/certificate.service';
import { Route } from '@angular/compiler/src/core';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-create-certificate',
  templateUrl: './create-certificate.component.html',
  styleUrls: ['./create-certificate.component.css']
})
export class CreateCertificateComponent implements OnInit {

      public issuers = [] as any;
      public subjects = [] as any;
      public type: any = null;
      validateForm!: FormGroup;

      constructor(private router:Router,private fb: FormBuilder, private certificateService: CertificateService, private userService: UserService) {}

        ngOnInit(): void {
          this.validateForm = this.fb.group({
            days: [null, [Validators.required]],
            subject: [null, [Validators.required]],
            issuer: [null, [Validators.required]]
          });
        }

        public getAllSubjects(): void {
          this.userService.getAllSubjects().subscribe(data => {
              this.subjects = data;
              console.log(data);
          })
        }

        
        public getAllIssuers(): void {
          this.userService.getAllIssuers().subscribe(data => {
              this.issuers = data;
              console.log(data);
          })
        }
      

        changeSelect(e, formLogin){
          console.log(e.target.value);
          console.log(formLogin)
    
        }

        submitForm(): void {
          for (const i in this.validateForm.controls) {
            this.validateForm.controls[i].markAsDirty();
            this.validateForm.controls[i].updateValueAndValidity();
          }
          let type = this.type;
          let days = this.validateForm.value.days;
          let issuer = this.validateForm.value.issuer;
          let subject = this.validateForm.value.subject;
          console.log(this.validateForm.value.type);
          

          let body = {
            digitalSignature: this.validateForm.value.digitalSignature,
            keyEncipherment:  this.validateForm.value.keyEncipherment,
            keyAgreement:  this.validateForm.value.keyAgreement,
            nonRepudiation: this.validateForm.value.nonRepudiation
          }
          this.certificateService.createCertificate(type, days, issuer, subject, body).subscribe(data => {
            console.log("uspeo");
          }, error => { 
          console.log("nije uspeo");
          })
      }
        
  

}
