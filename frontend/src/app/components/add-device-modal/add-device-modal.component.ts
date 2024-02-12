import {Component, EventEmitter, Input, Output} from '@angular/core';

@Component({
  selector: 'app-add-device-modal',
  templateUrl: './add-device-modal.component.html',
  styleUrls: ['./add-device-modal.component.css']
})
export class AddDeviceModalComponent {
  @Output() onSubmitAddDeviceModal = new EventEmitter();
  @Output() onSubmitUpdateDeviceModal = new EventEmitter();
  @Input() title : string = "";
  @Input() deviceId : string = '';
  userId: string = '';
  name: string = '';
  description: string = '';
  address: string = '';
  maxHourlyConsumption: string = '';
  onSubmit() {
    if(this.title === "Add New Device"){
      this.onSubmitAddDeviceModal.emit({
        "userId": this.userId,
        "name": this.name,
        "description": this.description,
        "address" : this.address,
        "maxHourlyConsumption" : this.maxHourlyConsumption
      });
    }else if( this.title === "Update Device"){
      this.onSubmitUpdateDeviceModal.emit({
        "id" : this.deviceId,
        "userId": this.userId,
        "name": this.name,
        "description": this.description,
        "address" : this.address,
        "maxHourlyConsumption" : this.maxHourlyConsumption
      });
    }

  }
}
