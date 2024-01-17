import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {AuthenticateService} from "./authenticate.service";

@Injectable({
  providedIn: 'root'
})
export class ClientService {
  private clientURL = "http://localhost:8081";
  private headers;
  constructor(private http:HttpClient,
              private authService: AuthenticateService) {
    this.headers = new HttpHeaders({
    'Content-Type': 'application/json',
    "Authorization": "Bearer " + this.authService.getAccessToken()
  });
  }

  getDevices(id : any) {
    return this.http.get(this.clientURL + "/api/v1/devices/getDevices/" + id,
      {headers: this.headers});
  }
}
