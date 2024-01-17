import {CanActivateFn, Router} from '@angular/router';
import {inject} from "@angular/core";
import {AuthenticateService} from "../services/authenticate.service";

export const adminGuard: CanActivateFn = (route, state) => {
  const router = inject(Router);
  const userRole = inject(AuthenticateService).getRole();
  const hasRole = (userRole === "ADMIN");
  return hasRole || router.navigate(['welcome']);
};
