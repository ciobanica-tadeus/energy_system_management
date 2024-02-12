import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {AuthenticateService} from "./authenticate.service";

export interface User {
  id: string,
  name: string,
  username: string,
  roleType: string
}
export interface Device {
  id: string,
  userId: string,
  maxHourlyEnergyConsumption: number,
  name: string,
  description: string,
  address: string
}

@Injectable({
  providedIn: 'root'
})
export class AdminService {
  private URL = "http://localhost:8080/api/v1/users";
  private deviceURL = "http://localhost:8081/api/v1/devices";

  private headers;

  constructor(private http: HttpClient, private authService: AuthenticateService) {
    this.headers = new HttpHeaders({
      'Content-Type': 'application/json',
      "Authorization": "Bearer " + this.authService.getAccessToken()
    });
  }

  getAllUsers() {
    return this.http.get(this.URL,
      {headers: this.headers});
  };

  getAllDevices() {
    return this.http.get(this.deviceURL,
      {headers: this.headers});
  }

  addNewUser(name: string, username: string, password: string, roleType: any) {
    let userRequest = {
      "name": name,
      "username": username,
      "password": password,
      "roleType": roleType
    };
    return this.http.post(this.URL + "/save", userRequest,
      {headers: this.headers});
  }

  deleteUser(id: string) {
    return this.http.delete(this.URL + "/delete/" + id,
      {headers: this.headers});
  }

  updateUser(id: string, name: string, username: string, password: string,
             roleType: string) {
    let userUpdate = {
      "id": id,
      "name": name,
      "username": username,
      "password": password,
      "roleType": roleType
    }
    return this.http.put(this.URL + "/update", userUpdate,
      {headers: this.headers});
  }

  addNewDevice(deviceRequest: {
    name: string;
    description: string;
    address: string;
    maxHourlyEnergyConsumption: number
    userId: string;
  }) {
    return this.http.post(this.deviceURL + "/save",
      deviceRequest,
      {headers: this.headers})
  }

  deleteDevice(id: string) {
    return this.http.delete(this.deviceURL + "/delete/" + id,
      {headers: this.headers});
  }

  updateDevice(updateDeviceRequest: {
    id: string;
    name: string;
    description: string;
    address: string;
    maxHourlyEnergyConsumption: number;
    userId: string
  }) {
    return this.http.put(this.deviceURL + "/update", updateDeviceRequest,
      {headers: this.headers});
  }

  getUserDetails(id:string){
    return this.http.get(this.URL + "/getUser/"+ id,
      {headers: this.headers, responseType: 'text'});
  }
}
