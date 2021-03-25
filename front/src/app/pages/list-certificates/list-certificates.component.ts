import { CertificateService } from './../../services/certificate.service';
import { Route } from '@angular/compiler/src/core';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-list-certificates',
  templateUrl: './list-certificates.component.html',
  styleUrls: ['./list-certificates.component.css']
})
export class ListCertificatesComponent implements OnInit {

  public certificates = [] as any;
  
  constructor(private router: Router,  private route: ActivatedRoute, private certificateService: CertificateService) { }

  ngOnInit(): void {
    this.getAllCertificates();
  }

  public getAllCertificates(): void {
    this.certificateService.getAllCertificates().subscribe(data => {
        this.certificates = data;
        console.log(data);
    })
  }

  revoke(id): void {
    let body = {
     id: id
    }
    console.log(body);
    this.certificateService.revokeCert(body).subscribe(data => {
      console.log("uspeo");
      location.reload();
    }, error => { 
    console.log("nije uspeo");
    })
  }

}
