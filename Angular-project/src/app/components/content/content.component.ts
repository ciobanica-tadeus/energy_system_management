import {Component} from '@angular/core';
import {AuthenticateService} from "../../services/authenticate.service";
import {Router} from "@angular/router";
import {NotificationService} from "../../services/notification.service";

@Component({
  selector: 'app-content',
  templateUrl: './content.component.html',
  styleUrls: ['./content.component.css']
})
export class ContentComponent {

  constructor(private authenticateService: AuthenticateService,
              private notificationService: NotificationService,
              private router: Router) {
  }

  onLogin($event: any) {
    let username = $event.username;
    let password = $event.password;
    this.authenticateService
      .authenticateAnUser(username, password)
      .subscribe({
        next: (response) => {
          console.log(response);
          this.authenticateService.expirationCounter(new Date(response.expirationDate));
          console.log("Expiration " + new Date(response.expirationDate));
          this.authenticateService.setAccessToken(response.accessToken);
          this.authenticateService.setUserId(response.uuid);
          this.authenticateService.setRole(response.role);
          this.notificationService.init(response.uuid);
          console.log("The init websocket is called: " + response.uuid);
          if (response.role === "ADMIN") {
            this.router.navigate(['/admin', response.uuid]);
          } else if (response.role === "CLIENT") {
            this.router.navigate(['/client', response.uuid]);
          }
        },
        error: error => {
          console.error(error); // Log the error for debugging
          alert("The credentials are incorrect.Try again");
        }
      });
  }
}
