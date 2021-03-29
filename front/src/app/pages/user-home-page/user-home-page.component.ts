import { CertificateService } from './../../services/certificate.service';
import { ActivatedRoute } from '@angular/router';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-user-home-page',
  templateUrl: './user-home-page.component.html',
  styleUrls: ['./user-home-page.component.css']
})
export class UserHomePageComponent implements OnInit {

  id: any;
  public certificates = [] as any;
  public isValid = false; 
  public notValid = true; 

  constructor(private route: ActivatedRoute, private certificateService: CertificateService) { }

  ngOnInit(): void {
    this.setUpId();
    this.getCertChain();
  }

  private setUpId(): void {
    this.id = this.route.snapshot.params.id;
    console.log("Id usera");
    console.log(this.id);
   
  } 

  public getCertChain(): void {
    this.certificateService.getCertChain(this.id).subscribe(data => {
        this.certificates = data;
        console.log(data);
    })
  }

  public download(id): void {
    this.certificateService.downloadCert(id).subscribe(data =>{
      console.log("uspeo");
    })
  }

  checkValidity(id): void {
    this.certificateService.checkValitidy(id).subscribe(data => {
      console.log("uspeo");
      if(data === true){
        this.isValid = data;
      }
      else{
        this.notValid = data;
      }
      console.log(data);
    }, error => { 
    console.log("nije uspeo");
    })
  }



}
