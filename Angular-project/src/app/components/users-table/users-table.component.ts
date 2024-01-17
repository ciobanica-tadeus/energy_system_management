import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {AdminService, User} from "../../services/admin.service";

@Component({
  selector: 'app-users-table',
  templateUrl: './users-table.component.html',
  styleUrls: ['./users-table.component.css']
})
export class UsersTableComponent implements OnInit {
  @Output() onSubmitEditUser = new EventEmitter();
  users: User[] = [];
  selectedUser ?: User;
  public constructor(private adminService: AdminService) {

  }

  ngOnInit(): void {
    this.getAllUsers();
  }

  private getAllUsers() {
    this.adminService.getAllUsers().subscribe({
        next: (response: any) => {
          if (response.usersList && response) {
            this.users = response.usersList.map(
              (userData: any) => ({
                id: userData.id,
                name: userData.name,
                username: userData.username,
                roleType: userData.roleType
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

  onAddUser($event: any) {
    let name = $event.name;
    let username = $event.username;
    let password = $event.password;
    let roleType = $event.roleType;
    this.adminService.addNewUser(name, username, password, roleType)
      .subscribe({
        next: (response) => {
          this.getAllUsers();
        },
        error: error => {
          console.log(error);
        }
      });
  }

  onDeleteUser(id: string) {
    this.adminService.deleteUser(id).subscribe({
      next: response => {
        console.log("The user deleted with success!");
        this.getAllUsers();
      },
      error: error => {
        console.log(error);
      }
    });
  }

  onUpdateUser($event: any) {
    let id = $event.id;
    let name = $event.name;
    let username = $event.username;
    let password = $event.password;
    let roleType = $event.roleType;
    this.adminService.updateUser(id,name, username, password, roleType)
      .subscribe({
        next: (response) => {
          this.getAllUsers();
        },
        error: error => {
          console.log(error);
        }
      })
  }

  onClickUpdate(user: User) {
    this.selectedUser = user;
  }

}
