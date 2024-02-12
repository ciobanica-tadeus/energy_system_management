import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { HeaderComponent } from './components/header/header.component';
import { HttpClientModule} from "@angular/common/http";
import { WelcomeComponent } from './components/welcome/welcome.component';
import { LoginFormComponent } from './components/login-form/login-form.component';
import { ContentComponent } from './components/content/content.component';
import {FormsModule} from "@angular/forms";
import {NgOptimizedImage} from "@angular/common";
import { AppRoutingModule } from './app-routing.module';
import { AdminComponent } from './admin/admin.component';
import { ClientComponent } from './client/client.component';
import { ErrorComponent } from './components/error/error.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AdminModalComponent} from "./components/admin-modal/admin-modal.component";
import { UsersTableComponent } from './components/users-table/users-table.component';
import {UpdateModalComponent} from "./components/update-modal/update-modal.component";
import { AddDeviceModalComponent } from './components/add-device-modal/add-device-modal.component';
import { DeviceComponent } from './device/device.component';
import { ChatComponent } from './components/chat/chat.component';
import {UnsubComponent} from "./components/unsub/unsub.component";

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    WelcomeComponent,
    LoginFormComponent,
    ContentComponent,
    AdminComponent,
    ClientComponent,
    ErrorComponent,
    AdminModalComponent,
    UsersTableComponent,
    UpdateModalComponent,
    AddDeviceModalComponent,
    DeviceComponent,
    ChatComponent,
    UnsubComponent,
  ],
    imports: [
        BrowserModule,
        HttpClientModule,
        FormsModule,
        NgOptimizedImage,
        AppRoutingModule,
        BrowserAnimationsModule,
    ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
