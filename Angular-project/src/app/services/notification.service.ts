import {Injectable} from '@angular/core';
import {SharedService} from "./shared.service";
import {Message} from "../types/message";
import {Frame} from "../types/frame";
import {HttpHeaders} from "@angular/common/http";
import {AuthenticateService} from "./authenticate.service";

declare const SockJS: any;
declare const Stomp: any;

@Injectable({
  providedIn: 'root'
})
export class NotificationService {
  private socketURL: string = "http://localhost:8084/our-websocket"
  private stompClient: any;
  private headers:any;
  constructor(private sharedService: SharedService,
              private authService: AuthenticateService) {
    this.headers = {
      "Authorization": "Bearer " + this.authService.getAccessToken()
    };
  }

  init(userExternalId: string) {
    const socket = new SockJS(this.socketURL);
    if (this.stompClient) {
      this.stompClient.disconnect();
    }
    this.stompClient = Stomp.over(socket);
    this.subscribeToMessage(userExternalId);
  }

  private subscribeToMessage(userExternalId: string) {
    this.stompClient.connect({}, () => {
      this.stompClient.subscribe('/topic/user/' + userExternalId + "/private-messages",
        (frame: Frame)=> {
          const message = JSON.parse(frame.body).content;
          alert(message);
          this.sharedService.sendMessage('Refresh');
      });
    });
  }
}
