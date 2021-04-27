import { RegistrationRequestService } from './../../services/registration-request.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-registration-requests',
  templateUrl: './registration-requests.component.html',
  styleUrls: ['./registration-requests.component.css']
})
export class RegistrationRequestsComponent implements OnInit {

  public pendingUsers = [];
  public approveAlert = false;
  public denyAlert = false;
  public empty = false;

  constructor(private rrService: RegistrationRequestService) { }
  

  ngOnInit(): void {
    this.getAllPendingUsers();
  }

  private getAllPendingUsers(): void {
    this.rrService.getAllPendingUsers().subscribe(data => {
      this.pendingUsers = data;
      if(this.pendingUsers.length === 0){
        this.empty = true;
      }
    }, error => {
      
    })
  }

  public approve(id): void {
    const body = {
      id: id
    }
    this.rrService.approveRegistrationRequest(body).subscribe(data =>{
    location.reload();
      this.approveAlert = true;
    })
  }

  public deny(id): void {
    const body = {
      id: id
    }
    this.rrService.denyRegistrationRequest(body).subscribe(data =>{
      location.reload();
      this.denyAlert = true;
    })
  }


}
