import {Component, EventEmitter, Input, Output} from '@angular/core';
import {AuthenticateService} from "../../services/authenticate.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent {
  @Output() onChangeTableUsers = new EventEmitter();
  @Output() onChangeTableDevices = new EventEmitter();
  @Output() onChangeChatComponent = new EventEmitter();
  activeLink: string = "";

  public constructor(private authService: AuthenticateService, private router: Router) {
  }

  logOut() {
    this.authService.clearLocalStorage();
    this.router.navigate(['/welcome']);
  }

  onTableUsers() {
    this.onChangeTableUsers.emit({"tableUsers": true});
    this.activeLink = "users";
  }

  onTableDevices() {
    this.onChangeTableDevices.emit({"tableDevices": true});
    this.activeLink = "devices";
  }

  onChatComponent() {
    this.onChangeChatComponent.emit({"chatComponent": true});
    this.activeLink = "chat";
  }
}
