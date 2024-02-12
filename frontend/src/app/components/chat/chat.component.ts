import {Component, OnDestroy, OnInit} from '@angular/core';
import {AdminService} from "../../services/admin.service";
import {AuthenticateService} from "../../services/authenticate.service";
import {ChatService} from "../../services/chat.service";
import {Message} from "../../types/message";
import {SendMessage} from "../../types/send-message";
import {Contact} from "../../types/contact";
import {MessageInfo} from "../../types/message-info";
import {SharedService} from "../../services/shared.service";
import {takeUntil} from "rxjs";
import {UnsubComponent} from "../unsub/unsub.component";

export interface userData {
  id: string,
  name: string,
  username: string,
  roleType: string
}

@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.css']
})
export class ChatComponent extends UnsubComponent implements OnInit, OnDestroy {
  adminPersistenceId = "8532843b-1755-4731-b3a3-b6367522c95a";
  selectedContactId: string = '';
  userData: userData = {
    id: "",
    name: "",
    username: "",
    roleType: ""
  };
  message: string = '';
  contacts: Contact[] = [];
  messages: Message[] = [];

  constructor(private adminService: AdminService,
              private authService: AuthenticateService,
              private chatService: ChatService,
              private sharedService: SharedService) {
    super();
  }

  ngOnInit(): void {
    const usernameId = this.authService.getUserId() || '';
    if (usernameId !== '') {
      this.adminService.getUserDetails(usernameId)
        .subscribe(
          {
            next: (response) => {
              const userResponse = JSON.parse(response);
              console.log(userResponse);
              this.userData.id = userResponse.id;
              this.userData.username = userResponse.username;
              this.userData.name = userResponse.name;
              this.userData.roleType = userResponse.roleType;
              console.log("userData.id : " + this.userData.id);
              console.log("userData.role : " + this.userData.roleType);
              if (userResponse.roleType === 'CLIENT') {
                this.selectedContactId = this.adminPersistenceId;
              }
            },
            error: error => {
              console.log(error);
            }
          }
        );
    }

    setTimeout(() => {
      this.getContacts();
    }, 1000);

    this.sharedService.message$.subscribe(() => {
      if (this.selectedContactId !== '') {
        setTimeout(() => {
          this.getConversation(this.selectedContactId);
        }, 1000);
      }
    });
  }

  handleSendMessage() {
    if (this.message === '') {
      this.alertEmptyMessage();
    } else {
      this.sendMessage();
    }
  }

  private alertEmptyMessage() {
    alert("Message can't be empty");
  }

  private sendMessage() {
    if (this.userData.roleType === 'CLIENT') {
      this.send(this.adminPersistenceId, this.userData.id);
    } else if (this.userData.roleType === 'ADMIN') {
      this.send(this.selectedContactId, this.adminPersistenceId);
    }
    this.getConversation(this.selectedContactId);
  }

  private send(receiverId: string, senderId: string) {
    const body: SendMessage = {
      senderId: senderId,
      receiverId: receiverId,
      content: this.message,
      name: this.userData.name,
      username: this.userData.username
    };
    this.chatService
      .sendMessage(body)
      .pipe(takeUntil(this.unsubscribe$))
      .subscribe({
        next: () => {
          console.log("Message sent to :" + receiverId);
          this.message = '';
        },
        error: (error) => {
          console.log("There was a problem with sending the message : " + error);
        }
      })
  }

  getContacts() {
    console.log("contacts retrieved: the userID : " + this.userData.id);
    this.chatService.getContacts(this.userData.id)
      .pipe(takeUntil(this.unsubscribe$))
      .subscribe((res) => {
        this.contacts = res;
      });
    if (this.userData.roleType === 'CLIENT') {
      this.getConversation(this.adminPersistenceId);
    }
  }

  getConversation(contactExternalId: string) {
    this.selectedContactId = contactExternalId;

    const messageInfo: MessageInfo = {
      userExternalId: this.userData.id,
      contactExternalId: contactExternalId
    };
    console.log("Get conversation method: userID - " + this.userData.id + " ; contactId - " + contactExternalId);
    setTimeout(() => {
      this.chatService.getConversation(messageInfo)
        .pipe(takeUntil(this.unsubscribe$))
        .subscribe(
          (res) => {
            this.messages = res;
          }
        );
    }, 200);
    this.seeMessages(this.selectedContactId);
  }

  private seeMessages(selectedContactId: string) {
    const seenMessagesBody: MessageInfo = {
      userExternalId: this.userData.id,
      contactExternalId: selectedContactId
    };
    this.chatService.seeMessages(seenMessagesBody)
      .subscribe({
        next: () => {
          console.log('Success!');
        },
        error: () => {
          console.log('There was a problem!');
        },
      });
  }
}
