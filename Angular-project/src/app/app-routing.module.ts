import {NgModule} from '@angular/core';
import {RouterModule, Routes} from "@angular/router";
import {WelcomeComponent} from "./components/welcome/welcome.component";
import {AdminComponent} from "./admin/admin.component";
import {ClientComponent} from "./client/client.component";
import {ErrorComponent} from "./components/error/error.component";
import {adminGuard} from "./guards/admin.guard";
import {clientGuard} from "./guards/client.guard";

const routes: Routes = [
  {path: '', redirectTo: '/welcome', pathMatch:'full'},
  {path: 'welcome', component: WelcomeComponent},
  {path: 'admin/:id', component: AdminComponent, canActivate: [adminGuard]},
  {path: 'client/:id', component: ClientComponent, canActivate: [clientGuard]},
  {path: '**', component: ErrorComponent}
]

@NgModule({
  declarations: [],
  imports: [
    RouterModule.forRoot(routes)
  ],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
