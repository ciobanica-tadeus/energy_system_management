import {Component, OnDestroy, OnInit} from '@angular/core';

@Component({
  selector: 'app-welcome',
  templateUrl: './welcome.component.html',
  styleUrls: ['./welcome.component.css']
})
export class WelcomeComponent implements OnInit, OnDestroy{
  ngOnDestroy(): void {

  }
  ngOnInit(): void {
    console.log(window.localStorage);
  }


}
