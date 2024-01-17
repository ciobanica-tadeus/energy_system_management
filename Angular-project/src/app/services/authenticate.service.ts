import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {BehaviorSubject, delay, Observable, of, Subscription, timeout} from "rxjs";
import {Router} from "@angular/router";

interface AuthenticateJSON {
  accessToken: string,
  uuid: string,
  role: string,
  expirationDate: Date
}

@Injectable({
  providedIn: 'root'
})
export class AuthenticateService {
  private URL = "http://localhost:8080";
  private expirationSubscription = new Subscription();

  constructor(private http: HttpClient,
              private router: Router) {
  }

  getAccessToken(): string | null {
    return window.localStorage.getItem('accessToken');
  }

  setAccessToken(accessToken: string) {
    if (accessToken !== null) {
      window.localStorage.setItem("accessToken", accessToken);
    } else {
      window.localStorage.removeItem("accessToken");
    }
  }

  getRole(): string | null {
    return window.localStorage.getItem('role');
  }

  setRole(role: string) {
    if (role !== null) {
      window.localStorage.setItem("role", role);
    } else {
      window.localStorage.removeItem("role");
    }
  }

  getUserId() {
    return window.localStorage.getItem("id");
  }

  setUserId(id: string) {
    if (id !== null) {
      window.localStorage.setItem("id", id);
    } else {
      window.localStorage.getItem("id");
    }
  }

  public authenticateAnUser(username: string, password: string): Observable<any> {
    let body = {"username": username, "password": password}
    return this.http.post<AuthenticateJSON>(
      this.URL + "/api/v1/auth/login",
      body
    );
  }

  clearLocalStorage() {
    this.expirationSubscription.unsubscribe();
    window.localStorage.clear();
  }

  expirationCounter(date: Date) {
    window.localStorage.clear()
    this.expirationSubscription = of(null)
      .pipe(delay(date))
      .subscribe((expired) => {
        console.log('Token Expired!!');
        this.clearLocalStorage();
        this.router.navigate(["/welcome"]);
      });
  }
}
