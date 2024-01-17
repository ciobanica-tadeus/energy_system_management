import { Component, OnDestroy } from '@angular/core';
import { Subject } from 'rxjs';

@Component({
  selector: 'app-unsub',
  templateUrl: './unsub.component.html',
  styleUrls: ['./unsub.component.scss'],
})
export class UnsubComponent implements OnDestroy {
  unsubscribe$ = new Subject<void>();

  ngOnDestroy() {
    this.unsubscribe$.next();
    this.unsubscribe$.complete();
  }
}
