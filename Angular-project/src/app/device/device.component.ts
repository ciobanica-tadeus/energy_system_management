import { Component } from '@angular/core';
import {AdminService, Device} from "../services/admin.service";

@Component({
  selector: 'app-device',
  templateUrl: './device.component.html',
  styleUrls: ['./device.component.css']
})
export class DeviceComponent {
  devices: Device[] = [];
  title: string = '';
  deviceId: string = '';

  constructor(private adminService: AdminService) {
    this.getAllDevices();
  }

  private getAllDevices() {
    this.adminService.getAllDevices().subscribe(
      {
        next: (response: any) => {
          if (response.deviceResponseList && response) {
            this.devices = response.deviceResponseList.map(
              (deviceData: any) => ({
                id: deviceData.id,
                userId: deviceData.userId,
                maxHourlyEnergyConsumption: deviceData.maxHourlyEnergyConsumption,
                name: deviceData.name,
                description: deviceData.description,
                address: deviceData.address,
              })
            )
          }
        },
        error: error => {
          console.error(error);
        }
      }
    );
  }

  onAddDevice($event: any) {
    let deviceRequest: {
      name: string;
      description: string;
      address: string;
      maxHourlyEnergyConsumption: number
      userId: string;
    } = {
      "userId": $event.userId,
      "name": $event.name,
      "description": $event.description,
      "address": $event.address,
      "maxHourlyEnergyConsumption": $event.maxHourlyConsumption
    }
    this.adminService.addNewDevice(deviceRequest).subscribe({
      next: (response: any) => {
        console.log(response);
        this.getAllDevices();
      },
      error: error => {
        console.log(error);
      }
    })
  }

  onDeleteDevice(id: string) {
    this.adminService.deleteDevice(id).subscribe({
      next: (response: any) => {
        console.log(response);
        this.getAllDevices();
      },
      error: error => {
        console.log(error);
      }
    });
  }

  setTitle(title: string) {
    this.title = title;
  }

  setDeviceId(id: string) {
    this.deviceId = id;
  }

  onUpdateDevice($event: any) {
    let updateDeviceRequest: {
      id: string;
      name: string;
      description: string;
      address: string;
      maxHourlyEnergyConsumption: number
      userId: string;
    } = {
      "id": $event.id != '' ? $event.id : null,
      "userId": $event.userId != '' ? $event.userId : null,
      "name": $event.name != '' ? $event.name : null,
      "description": $event.description != '' ? $event.description : null,
      "address": $event.address != '' ? $event.address : null,
      "maxHourlyEnergyConsumption":
        $event.maxHourlyConsumption != '' ? $event.maxHourlyConsumption : null,
    }
    this.adminService.updateDevice(updateDeviceRequest).subscribe({
      next: (response: any) => {
        console.log(response);
        this.getAllDevices();
      },
      error: error => {
        console.log(error);
      }
    })
  }
}
