import { logging } from 'protractor';
import { UserService } from './../../services/user.service';
import { CertificateService } from './../../services/certificate.service';
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
      public typeCert: any = null;
      public nonRepudiationC = false;
      public digitalSignatureC = false;
      public keyEnciphermentC = false;
      public keyAgreementC = false;
      
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

        public checkType(): void {
          console.log(this.type);
          console.log("Usao sam u check");
         
        }

        
        public getAllIssuers(): void {
          this.userService.getAllIssuers().subscribe(data => {
              this.issuers = data;
              console.log(data);
          })
        }
      

      

        submitForm(): void {
      
          for (const i in this.validateForm.controls) {
            this.validateForm.controls[i].markAsDirty();
            this.validateForm.controls[i].updateValueAndValidity();
          }
          
       
          let days = this.validateForm.value.days;
          let issuer = this.validateForm.value.issuer;
          let subject = this.validateForm.value.subject;
          

          if(this.type === "1"){
            this.typeCert = "root";
           }else if(this.type === "2"){
            this.typeCert = "interRoot";
           }else if(this.type === "3"){
            this.typeCert = "endEntity";
            console.log(this.typeCert);
            console.log("USAO SAM!!!!!");
          }else if(this.type === "4"){
            this.typeCert = "intermediate";
          }
         
          let body = {
            digitalSignature: this.digitalSignatureC,
            keyEncipherment:  this.keyEnciphermentC,
            keyAgreement:  this.keyAgreementC,
            nonRepudiation: this.nonRepudiationC
          }
          console.log(body);
          this.certificateService.createCertificate(this.typeCert, days, subject, issuer, body).subscribe(data => {
            console.log("uspeo");
          }, error => { 
            console.log(this.typeCert);
          console.log("nije uspeo");
          })
      }
        
  

}
