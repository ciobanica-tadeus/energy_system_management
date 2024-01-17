import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {SendMessage} from "../types/send-message";
import {Contact} from "../types/contact";
import {Observable} from "rxjs";
import {MessageInfo} from "../types/message-info";
import {Message} from "../types/message";
import {AuthenticateService} from "./authenticate.service";

@Injectable({
  providedIn: 'root'
})
export class ChatService {
  private url: string = "http://localhost:8084/api/v1/chat";
  private readonly headers;

  constructor(private http: HttpClient,
              private authService: AuthenticateService) {
    this.headers = new HttpHeaders({
      'Content-Type': 'application/json',
      "Authorization": "Bearer " + this.authService.getAccessToken()
    });
  }

  sendMessage(body: SendMessage) {
    return this.http.post(this.url + "/send", body,
      {headers: this.headers});
  }

  getContacts(userExternalId: string): Observable<Contact[]> {
    return this.http.get<Contact[]>(this.url + "/contacts/" + userExternalId,
      {headers: this.headers});
  }

  getConversation(body: MessageInfo): Observable<Message[]> {
    return this.http.post<Message[]>(this.url + "/messages", body,
      {headers: this.headers});
  }


  seeMessages(body: MessageInfo) {
    return this.http.post(this.url + "/seen", body,
      {headers: this.headers});
  }
}
