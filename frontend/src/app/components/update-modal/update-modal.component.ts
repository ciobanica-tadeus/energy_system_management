import {Component, EventEmitter, Input, OnDestroy, OnInit, Output} from '@angular/core';
import {User} from "../../services/admin.service";

@Component({
  selector: 'app-update-modal',
  templateUrl: './update-modal.component.html',
  styleUrls: ['./update-modal.component.css']
})
export class UpdateModalComponent {
  @Input() userDetails ?: User;
  @Output() onSubmitUpdateModal = new EventEmitter();
  id: string = "";
  name: string = "";
  username: string = "";
  password: string = "";
  role: string = "";

  constructor() {
  }

  onSubmit() {
    this.onSubmitUpdateModal.emit({
      "id": this.id != "" ?
        this.id : this.userDetails?.id,
      "name": this.name != "" ?
        this.name : this.userDetails?.name,
      "username": this.username != "" ?
        this.username : this.userDetails?.username,
      "password": this.password != "" ?
        this.password : null,
      "roleType": this.role != "" ? this.role : null
    });
    this.id = "";
    this.name = "";
    this.username = "";
    this.password = "";
    this.role = "";
  }
}
