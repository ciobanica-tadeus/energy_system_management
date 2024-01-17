import {Component} from '@angular/core';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent {
  usersTable :boolean = false;
  devicesTable: boolean = false;
  chatComponent: boolean = false;
  onTableUsers($event: any) {
    this.usersTable = $event.tableUsers;
    this.devicesTable = false;
    this.chatComponent = false;
  }

  onTableDevices($event: any) {
    this.devicesTable = $event.tableDevices;
    this.usersTable = false;
    this.chatComponent = false;
  }

  onChatComponent($event: any) {
    this.chatComponent = $event.chatComponent;
    this.usersTable = false;
    this.devicesTable = false;
  }
}
