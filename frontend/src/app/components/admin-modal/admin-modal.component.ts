import {Component, EventEmitter, Output} from '@angular/core';

@Component({
  selector: 'app-admin-modal',
  templateUrl: './admin-modal.component.html',
  styleUrls: ['./admin-modal.component.css']
})
export class AdminModalComponent {
  @Output() onSubmitModal = new EventEmitter();
  name: string = "";
  username: string = "";
  password: string = "";
  role: string = "";

  onSubmit() {
    this.onSubmitModal.emit({
      "name": this.name,
      "username": this.username,
      "password": this.password,
      "roleType" : this.role
    });
  }


}
