import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class SharedService {
  private source = new Subject<string>();
  public message$ = this.source.asObservable();

  sendMessage(message: string) {
    this.source.next(message);
  }
}
