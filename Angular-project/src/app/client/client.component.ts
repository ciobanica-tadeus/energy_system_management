import {Component, OnInit} from '@angular/core';
import {AuthenticateService} from "../services/authenticate.service";
import {Router} from "@angular/router";
import {ClientService} from "../services/client.service";
import {Device} from "../services/admin.service";

@Component({
  selector: 'app-client',
  templateUrl: './client.component.html',
  styleUrls: ['./client.component.css']
})
export class ClientComponent implements OnInit {
  devices: Device[] = [];
  activeLink: string = "";
  displayChat = false;
  public constructor(private authService: AuthenticateService,
                     private clientService: ClientService,
                     private router: Router) {
  }

  ngOnInit(): void {
    this.getDevices();
    this.displayChat = false;
  }

  private getDevices() {
    this.clientService.getDevices(this.authService.getUserId() == null ? "" : this.authService.getUserId()).subscribe(
      {
        next: (response: any) => {
          if (response.deviceResponseList && response) {
            this.devices = response.deviceResponseList.map(
              (deviceData: any) => ({
                id: deviceData.id,
                maxHourlyEnergyConsumption: deviceData.maxHourlyEnergyConsumption,
                name: deviceData.name,
                description: deviceData.description,
                address: deviceData.address,
              })
            )
          }
        },
        error: error => {
          console.log(error);
        }
      }
    );
  }

  logOut() {
    this.authService.clearLocalStorage();
    this.router.navigate(['/welcome']);
  }

  onChatComponent() {
    this.activeLink = "chat";
    this.displayChat = true;
  }
}
